package MyGames.model.database;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import MyGames.model.GameData;
import MyGames.model.GameRelationData;

/**
 * Created by jcamen on 19/02/18. It is just a fake database, whose data is stored in static
 * variables. They only last  as long as the application runs.
 */

public class MockGamesDatabase implements IGamesDatabase {

    private static List<GameData> storedGames = new ArrayList<>();
    private static List<GameRelationData> storedPlatforms = new ArrayList<>();
    private static List<GameRelationData> storedGenres = new ArrayList<>();

    @Override
    public List<Integer> getAllGames() {
        List<Integer> ids = new ArrayList<>(storedGames.size());
        for (GameData gameData : storedGames)
            ids.add(gameData.getId());
        return ids;
    }

    @Override
    public List<String> getAllPlatforms() {
        Set<String> platformNames = new HashSet<>();
        List<String> listOfPlatformNames = new ArrayList<>();
        boolean added;

        for (GameRelationData platformData : storedPlatforms)
            added = platformNames.add(platformData.getName());
        Iterator<String> iterator = platformNames.iterator();
        while (iterator.hasNext())
            listOfPlatformNames.add(iterator.next());

        return listOfPlatformNames;
    }

    @Override
    public List<String> getAllGenres() {
        Set<String> genreNames = new HashSet<>();
        List<String> listOfGenreNames = new ArrayList<>();
        boolean added;

        for (GameRelationData genreData : storedGenres)
            added = genreNames.add(genreData.getName());
        Iterator<String> iterator = genreNames.iterator();
        while (iterator.hasNext())
            listOfGenreNames.add(iterator.next());

        return listOfGenreNames;
    }

    @Override
    public List<Integer> getGamesWithPlatform(String platformName) {
        List<Integer> ids = new ArrayList<>();
        for (GameRelationData platformData : storedPlatforms)
            if (platformName.equals(platformData.getName()))
                ids.add(platformData.getIdGame());
        return ids;
    }

    @Override
    public List<Integer> getGamesWithGenre(String genreName) {
        List<Integer> ids = new ArrayList<>();
        for (GameRelationData genreData : storedGenres)
            if (genreName.equals(genreData.getName()))
                ids.add(genreData.getIdGame());
        return ids;
    }

    @Override
    public GameData getGameData(int id) {
        GameData gameData = null;
        for (GameData game : storedGames)
            if (game.getId() == id) {
                gameData = new GameData(id);
                gameData.setName(game.getName());
                gameData.setSummary(game.getSummary());
                gameData.setTotalRating(game.getTotalRating());
                gameData.setRating(game.getRating());
                gameData.setPublishers(game.getPublishers());
                gameData.setGenres(getGenres(id));
                gameData.setReleaseDate(game.getReleaseDate());
                gameData.setPlatforms(getPlatforms(id));
                gameData.setCover(game.getCover());
                gameData.setPegi(game.getPegi());
                gameData.setState(game.getState());
                gameData.setComment(game.getComment());
            }
        return gameData;
    }

    @Override
    public void insertGameData(GameData gameData) {
        for (GameData game : storedGames)
            if (game.getId() == gameData.getId()) {
                game.setName(gameData.getName());
                game.setSummary(gameData.getSummary());
                game.setTotalRating(gameData.getTotalRating());
                game.setRating(gameData.getRating());
                game.setPublishers(gameData.getPublishers());
                game.setGenres(gameData.getGenres());
                game.setReleaseDate(gameData.getReleaseDate());
                game.setPlatforms(gameData.getPlatforms());
                game.setCover(gameData.getCover());
                game.setPegi(gameData.getPegi());
                game.setState(gameData.getState());
                game.setComment(gameData.getComment());

                removePlatforms(game.getId());
                removeGenres(game.getId());
                return;
            }
        storedGames.add(gameData);
    }

    private void removeGenres(int gameId) {
        Iterator<GameRelationData> iterator = storedGenres.iterator();
        GameRelationData genreData;

        while (iterator.hasNext()) {
            genreData = iterator.next();
            if (genreData.getIdGame() == gameId)
                iterator.remove();
        }
    }

    private void removePlatforms(int gameId) {
        Iterator<GameRelationData> iterator = storedPlatforms.iterator();
        GameRelationData platformData;

        while (iterator.hasNext()) {
            platformData = iterator.next();
            if (platformData.getIdGame() == gameId)
                iterator.remove();
        }
    }

    @Override
    public void insertPlatformData(GameRelationData platformData) {
        for (GameRelationData platform : storedPlatforms)
            if (platform.getName().equals(platformData.getName()) && platform.getIdGame() == platformData.getIdGame())
                return;
        storedPlatforms.add(platformData);
    }

    @Override
    public void insertGenreData(GameRelationData genreData) {
        for (GameRelationData genre : storedGenres)
            if (genre.getName().equals(genreData.getName()) && genre.getIdGame() == genreData.getIdGame())
                return;
        storedGenres.add(genreData);
    }

    private String getPlatforms(int id) {
        StringBuilder builder = new StringBuilder();
        int i = 1;

        for (GameRelationData platFormData : storedPlatforms) {
            if (platFormData.getIdGame() == id) {
                if (i == 1) builder.append(platFormData.getName());
                else builder.append(", ").append(platFormData.getName());
                i++;
            }
        }
        return builder.toString();
    }

    private String getGenres(int id) {
        StringBuilder builder = new StringBuilder();
        int i = 1;

        for (GameRelationData genreData : storedGenres) {
            if (genreData.getIdGame() == id) {
                if (i == 1) builder.append(genreData.getName());
                else builder.append(", ").append(genreData.getName());
                i++;
            }
        }
        return builder.toString();
    }

    @Override
    public void changeState(int idGame, GameData.MyGameState state) {
        for (GameData gameData : storedGames)
            if (gameData.getId() == idGame)
                gameData.setState(state);
    }

    @Override
    public void changeComment(int idGame, String comment) {
        for (GameData gameData : storedGames)
            if (gameData.getId() == idGame)
                gameData.setComment(comment);
    }

    @Override
    public void deleteGame(int idGame) {
        Iterator<GameData> iterator = storedGames.iterator();
        GameData gameData;

        while (iterator.hasNext()) {
            gameData = iterator.next();
            if (gameData.getId() == idGame) {
                iterator.remove();
                removePlatforms(idGame);
                removeGenres(idGame);
                break;
            }
        }
    }
}
