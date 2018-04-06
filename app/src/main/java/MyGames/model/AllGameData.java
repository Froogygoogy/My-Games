package MyGames.model;

import java.util.List;

/**
 * Created by jcamen on 23/02/18.
 */

public class AllGameData {
    private GameData game;
    private List<String> platforms;
    private List<String> genres;

    public AllGameData() {
        game = null;
        platforms = null;
        genres = null;
    }

    public GameData getGame() {
        return game;
    }

    public void setGame(GameData game) {
        this.game = game;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
