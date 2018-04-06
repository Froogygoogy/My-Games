package MyGames.model.inetserver;



import java.io.File;
import java.util.List;

import MyGames.model.AllGameData;

/**
 * Created by jcamen on 17/02/18.
 */

public interface IGamesServer {

    void findGames(String gameName, ResponseReceiver<List<AllGameData>> receiver);

    void requestCover(String cover, String filename, ResponseReceiver<File> receiver);
}
