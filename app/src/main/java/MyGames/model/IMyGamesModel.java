package MyGames.model;

import java.util.List;

import MyGames.model.inetserver.ResponseReceiver;

/**
 * Created by Froogygoogy on 20/03/2018.
 */

public interface IMyGamesModel {
    void findGames(String searchedGameName, ResponseReceiver<List<AllGameData>> responseReceiver);
    void insertGame(AllGameData gameData);
    List<Integer> getAllGames();
    String getGameName(int id);
    List<String> getAllPlatforms();
    List<String> getAllGenres();

    List<Integer> getGamesWithGenre(String s);

    List<Integer> getGamesWithPlatform(String s);
}
