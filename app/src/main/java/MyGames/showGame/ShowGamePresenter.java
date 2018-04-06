package MyGames.showGame;


import java.text.SimpleDateFormat;
import java.util.TimeZone;

import MyGames.model.IMyGamesModel;

public class ShowGamePresenter {
    private  IShowGameView view;
    private IMyGamesModel model;
    private int currentGameId;
    private static SimpleDateFormat sdf;
    static {
        sdf = new SimpleDateFormat("yyyy, MMMM d");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    public ShowGamePresenter(IShowGameView newview, IMyGamesModel newmodel) {
        view = newview;
        model = newmodel;
    }

}
