package MyGames.model.inetserver;

import android.net.NetworkInfo;

/**
 * Created by jcamen on 22/02/18.
 */

public interface DownloadCallback<T> {
    void updateFromDownload(T result);
    NetworkInfo getActiveNetworkInfo();
    void onError(String msg);
}
