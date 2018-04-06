package MyGames.model.inetserver;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by jcamen on 23/02/18.
 */

public class NetworkHelper {
    private static String TAG = NetworkHelper.class.getSimpleName();
    private static int CHUNK_SIZE = 4096;

    public static class UnexpectedCodeException extends Exception {
        private int code;

        public UnexpectedCodeException(int code) {
            super("Unexpected code: " + code);
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    static String downloadString(String theURL, String ... header) throws IOException, UnexpectedCodeException {
        final String[] strings = new String[1];
        downloadURL(theURL, new ResponseReceiver<InputStream>() {
            @Override
            public void onResponseReceived(InputStream inputStream) {
                Scanner s = new Scanner(inputStream).useDelimiter("\\A");
                strings[0] = s.hasNext() ? s.next() : "";
            }

            @Override
            public void onErrorReceived(String message) {
                // Do nothing
            }
        }, header);

        return strings[0];
    }

    static void downloadFile(String theURL, final File filetoStoreCover, String ... header)
            throws IOException, UnexpectedCodeException {
        downloadURL(theURL, new ResponseReceiver<InputStream>() {
            @Override
            public void onResponseReceived(InputStream inputStream) {
                storeFile(filetoStoreCover, inputStream);
            }

            @Override
            public void onErrorReceived(String message) {
                // Do nothing
            }
        }, header);
    }

    private static void storeFile(File filetoStoreCover, InputStream inputStream) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filetoStoreCover);
            byte data[] = new byte[CHUNK_SIZE];
            int count;
            while ((count = inputStream.read(data)) != -1)
                outputStream.write(data, 0, count);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadURL(String theURL, ResponseReceiver<InputStream> receiver, String... header)
            throws IOException, UnexpectedCodeException {
        InputStream inputStream = null;
        HttpsURLConnection connection = null;

        try {
            Log.i(TAG, "Accessing to " + theURL);
            URL url = new URL(theURL);
            connection = (HttpsURLConnection)url.openConnection();
            connection.setReadTimeout(10000); // 10 seconds = 10000 ms.
            connection.setConnectTimeout(15000); // 15 seconds = 15000 ms.
            connection.setRequestMethod("GET");
            if (header != null) {
                for (int i = 0 ; i < header.length ; i+= 2)
                    connection.setRequestProperty(header[i], header[i+1]);
            }
            connection.setDoInput(true);
            connection.connect();
            int responseCode = connection.getResponseCode();
            Log.i(TAG, "Response code from server: " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                receiver.onResponseReceived(inputStream);
            }
            else
                throw new UnexpectedCodeException(responseCode);
        } finally {
            if (inputStream != null)
                inputStream.close();
            if (connection != null)
                connection.disconnect();
        }
    }
}
