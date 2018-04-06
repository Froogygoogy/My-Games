package MyGames.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import MyGames.model.database.IGamesDatabase;
import MyGames.model.database.MockGamesDatabase;
import MyGames.model.inetserver.IGamesServer;
import MyGames.model.inetserver.MockGamesServer;
import MyGames.model.inetserver.ResponseReceiver;

/**
 * Created by Froogygoogy on 20/03/2018.
 */

public class MyGamesModel implements  IMyGamesModel {
    private IGamesDatabase db;
    private IGamesServer gamesServer;
    private static MyGamesModel instance = null;


    public static MyGamesModel getInstance() {
        return instance;
    }
    public static MyGamesModel getInstance(Context context) {
        if (instance == null)
            instance = new MyGamesModel(new MockGamesDatabase(),
                    new MockGamesServer());
        return instance;
    }
    private MyGamesModel(IGamesDatabase db, IGamesServer gamesServer) {
        this.db = db;
        this.gamesServer = gamesServer;
    }

    @Override
    public void findGames(String searchedGameName, ResponseReceiver<List<AllGameData>> responseReceiver) {
        gamesServer.findGames(searchedGameName, responseReceiver);
    }

    @Override
    public void insertGame(AllGameData allGameData) {
        GameData gameData = allGameData.getGame();
        db.insertGameData(gameData);
        for (String name : allGameData.getPlatforms()) {
            GameRelationData gameRelationData = new GameRelationData(name,
                    gameData.getId());
            db.insertPlatformData(gameRelationData);
        }
        for (String name : allGameData.getGenres()) {
            GameRelationData gameRelationData = new GameRelationData(name,
                    gameData.getId());
            db.insertGenreData(gameRelationData);
        }
    }

    @Override
    public  List<Integer> getAllGames()
    {
        return  db.getAllGames();
    }

    @Override
    public String getGameName(int id) {
        GameData gameData = db.getGameData(id);
        return gameData.getName();
    }

    @Override
    public List<String> getAllPlatforms() {
        return db.getAllPlatforms();
    }

    @Override
    public List<String> getAllGenres() {
        return db.getAllGenres();
    }

    @Override
    public List<Integer> getGamesWithGenre(String s) {
        return db.getGamesWithGenre(s) ;
    }

    @Override
    public List<Integer> getGamesWithPlatform(String s) {
        return db.getGamesWithPlatform(s);
    }

}
