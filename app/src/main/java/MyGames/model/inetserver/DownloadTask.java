package MyGames.model.inetserver;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;

/**
 * Created by jcamen on 27/02/18.
 */

public class DownloadTask extends AsyncTask<Void, Void, String> {

    public enum Type { FILE, STRING };
    private static final String NETWORK_NOT_CONNECTED = "Network not connected";
    private static final String NETWORK_ERROR = "Network error";
    private static final String BAD_CODE_FROM_SERVER = "Bad code from server: ";

    private Type type;
    private String urlToDownload;
    private File filetoStoreCover;
    private DownloadCallback<String> downloadCallback;
    private String[] headers;
    private String errorMsg;

    public DownloadTask(String urlToDownload, DownloadCallback<String> downloadCallback, String... headers) {
        this.type = Type.STRING;
        this.urlToDownload = urlToDownload;
        this.downloadCallback = downloadCallback;
        this.headers = headers;
    }

    public DownloadTask(String urlToDownload, File filetoStoreCover, DownloadCallback<String> downloadCallback, String... headers) {
        this.type = Type.FILE;
        this.filetoStoreCover = filetoStoreCover;
        this.urlToDownload = urlToDownload;
        this.downloadCallback = downloadCallback;
        this.headers = headers;
    }

    @Override
    protected void onPreExecute() {
        NetworkInfo networkInfo = downloadCallback.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            downloadCallback.onError(NETWORK_NOT_CONNECTED);
            cancel(true);
        }
    }

    @Override
    protected String doInBackground(Void ... nothing) {
        String response = null;
        try {
            switch (type) {
                case FILE:
                    NetworkHelper.downloadFile(urlToDownload, filetoStoreCover, headers);
                    response = "OK";
                    break;
                case STRING:
                    response = NetworkHelper.downloadString(urlToDownload, headers);
                    break;
            }
        } catch (IOException e) {
            errorMsg = NETWORK_ERROR;
        } catch (NetworkHelper.UnexpectedCodeException e) {
            errorMsg = BAD_CODE_FROM_SERVER + e.getCode();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        if (response == null)
            downloadCallback.onError(errorMsg);
        else
            downloadCallback.updateFromDownload(response);

    }
}
