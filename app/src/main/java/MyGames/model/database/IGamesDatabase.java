package MyGames.model.database;


import java.util.List;

import MyGames.model.GameData;
import MyGames.model.GameRelationData;

/**
 * Created by jcamen on 17/02/18.
 */

public interface IGamesDatabase {
    List<Integer> getAllGames();
    List<String> getAllPlatforms();
    List<String> getAllGenres();
    List<Integer> getGamesWithPlatform(String platformName);
    List<Integer> getGamesWithGenre(String genreName);
    GameData getGameData(int id);

    void insertGameData(GameData gameData);
    void insertPlatformData(GameRelationData platformData);
    void insertGenreData(GameRelationData genreData);

    void changeState(int idGame, GameData.MyGameState state);
    void changeComment(int idGame, String comment);

    void deleteGame(int idGame);
}
