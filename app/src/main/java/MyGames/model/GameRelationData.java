package MyGames.model;

/**
 * Created by jcamen on 17/02/18.
 */

public class GameRelationData {
    private String name;
    private int idGame;

    public GameRelationData(String name, int idGame) {
        this.name = name;
        this.idGame = idGame;
    }

    public String getName() {
        return name;
    }

    public int getIdGame() {
        return idGame;
    }

}
