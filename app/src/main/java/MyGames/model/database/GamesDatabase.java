package MyGames.model.database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import MyGames.model.GameData;
import MyGames.model.GameRelationData;

public class GamesDatabase extends SQLiteOpenHelper implements IGamesDatabase{
    private static final String GAMES = "GAMES";
    private static final String GAME_ID = "ID";
    private static final String GAME_NAME = "GAMENAME";
    private static final String GAME_SUMMARY = "SUMMARY";
    private static final String GAME_TOTALRATING = "TOTALRATING";
    private static final String GAME_RATING = "RATING";
    private static final String GAME_PUBLISHERS = "PUBLISHERS";
    private static final String GAME_RELEASEDATE = "RELEASEDATE";
    private static final String GAME_COVER = "COVER";
    private static final String GAME_PEGI = "PEGI";
    private static final String GAME_STATE = "STATE";
    private static final String GAME_COMMENT = "COMMENT";
    private static final String INTEGER_KEY = "integer primary key not null";
    private static final String TEXT_NOT_NULL = "text not null";
    private static final String TEXT = "text";
    private static final String REAL = "real";
    private static final String INTEGER = "integer";
    private static final String INTEGER_NOT_NULL = "integer not null";
    private static final String PLATFORMS = "PLATFORMS";
    private static final String GENRES = "GENRES";
    private static final String GNAME = "GNAME";
    private static final String GAMEID = "GAMEID";
    private static final String PNAME = "PNAME";


    public GamesDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public List<Integer> getAllGames() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(GAMES,
                new String[] {GAME_ID},
                null,
                null,
                null,
                null,
                GAME_NAME);
        List<Integer> games = new ArrayList<>(cursor.getCount());;

        while (cursor.moveToNext()) {
            int i = cursor.getInt(0);
            games.add(i);
        }
        cursor.close();
        return games;
    }

    @Override
    public List<String> getAllPlatforms() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(PLATFORMS,
                new String[] {PNAME},
                null,
                null,
                null,
                null,
                PNAME);
        List<String> platforms = new ArrayList<>(cursor.getCount());;

        while (cursor.moveToNext()) {
            String i = cursor.getString(0);
            platforms.add(i);
        }
        cursor.close();
        return platforms;    
    }

    @Override
    public List<String> getAllGenres() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(GENRES,
                new String[] {GNAME},
                null,
                null,
                null,
                null,
                GNAME);
        List<String> genres = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            String i = cursor.getString(0);
            genres.add(i);
        }
        cursor.close();
        return genres;
    }

    @Override
    public List<Integer> getGamesWithPlatform(String platformName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(PLATFORMS,
                null,
                PNAME  + "=?",
                new String[] {platformName},
                null,
                null,
                null);
        List<Integer> ids  = new ArrayList<>(cursor.getCount());;
        while (cursor.moveToNext()) {
            ids.add( cursor.getInt(1));
        }
        cursor.close();

        return ids;
    }

    @Override
    public List<Integer> getGamesWithGenre(String genreName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(GENRES,
                null,
                GNAME  + "=?",
                new String[] {genreName},
                null,
                null,
                null);
        List<Integer> ids  = new ArrayList<>(cursor.getCount());;
        while (cursor.moveToNext()) {
            ids.add( cursor.getInt(1));
        }
        cursor.close();

        return ids;
    }

    @Override
    public GameData getGameData(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(GAMES,
                null,
                GAME_ID + "=?",
                new String[] {Integer.toString(id)},
                null,
                null,
                null);
        GameData gameData = null;
        if (cursor.moveToNext()) {
            gameData = new GameData(id);
            gameData.setName(cursor.getString(1));
            gameData.setPlatforms(getPlatforms(id));
            gameData.setGenres(getGenres(id));
        }
        cursor.close();
        return gameData;
    }

    private String getGenres(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(GENRES,
                null,
                GAMEID + "=?",
                new String[] {Integer.toString(id)},
                null,
                null,
                null);

        StringBuilder builder = new StringBuilder();
        if (cursor.getCount() == 0) {
            builder.append(GameData.NO_VALUE);
            return builder.toString();
        }
        while (cursor.moveToNext()) {
            builder.append(cursor.getString(0));
        }
        cursor.close();
        return builder.toString();
    }

    private String getPlatforms(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(PLATFORMS,
                null,
                GAMEID + "=?",
                new String[] {Integer.toString(id)},
                null,
                null,
                null);

        StringBuilder builder = new StringBuilder();
        if (cursor.getCount() == 0) {
            builder.append(GameData.NO_VALUE);
            return builder.toString();
        }
        while (cursor.moveToNext()) {
            builder.append(cursor.getString(0));
        }
        cursor.close();
        return builder.toString();
    }

    @Override
    public void insertGameData(GameData gameData) {

    }

    @Override
    public void insertPlatformData(GameRelationData platformData) {

    }

    @Override
    public void insertGenreData(GameRelationData genreData) {

    }

    @Override
    public void changeState(int idGame, GameData.MyGameState state) {

    }

    @Override
    public void changeComment(int idGame, String comment) {

    }

    @Override
    public void deleteGame(int idGame) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createGamesTable(db);
        createPlatformsTable(db);
        createGenresTable(db);

    }
    private void createGamesTable(SQLiteDatabase db) {
        String create = "create table " + GAMES + " ("
                + GAME_ID + " " + INTEGER_KEY + ","
                + GAME_NAME + " " + TEXT_NOT_NULL + ","
                + GAME_SUMMARY + " " + TEXT + ","
                + GAME_TOTALRATING + " " + REAL + ","
                + GAME_RATING + " " + REAL + ","
                + GAME_PUBLISHERS + " " + TEXT + ","
                + GAME_RELEASEDATE + " " + INTEGER + ","
                + GAME_COVER + " " + TEXT + ","
                + GAME_PEGI + " " + TEXT + ","
                + GAME_STATE + " " + INTEGER_NOT_NULL + ","
                + GAME_COMMENT + " " + TEXT
                + ");";
        db.execSQL(create);
    }
    private void createGenresTable(SQLiteDatabase db) {
        String create = "create table " + GENRES + " ("
                + GNAME + " " + TEXT_NOT_NULL + ","
                + GAMEID + " " + INTEGER_NOT_NULL + ","
                + "foreign key(" + GAMEID + ") references " + GAMES + "(" + GAME_ID + ")" + ","
                + "primary key(" + GNAME + "," + GAMEID + ")"
                + ");";
        db.execSQL(create);
    }

    private void createPlatformsTable(SQLiteDatabase db) {
        String create = "create table " + PLATFORMS + " ("
                + PNAME + " " + INTEGER_NOT_NULL + ","
                + GAMEID + " " + INTEGER_NOT_NULL + ","
                + "foreign key(" + GAMEID + ") references " + GAMES + "(" + GAME_ID + ")" + ","
                + "primary key(" + GNAME + "," + GAMEID + ")"
                + ");";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + GAMES);
        db.execSQL("drop table if exists " + PLATFORMS);
        db.execSQL("drop table if exists " + GENRES);
        onCreate(db);
    }
}
