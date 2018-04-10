package MyGames.showGame;


import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import MyGames.model.GameData;
import MyGames.model.IMyGamesModel;
import MyGames.model.inetserver.ResponseReceiver;

import static MyGames.model.GameData.NO_COMMENT;
import static MyGames.model.GameData.NO_NUMBER;
import static MyGames.model.GameData.NO_VALUE;

public class ShowGamePresenter {
    private static final double RATIO_RATINGS = 10.0;
    private  IShowGameView view;
    private IMyGamesModel model;
    private int currentGameId;
    private GameData.MyGameState currentGameState;
    private static SimpleDateFormat sdf;
    static {
        sdf = new SimpleDateFormat("yyyy, MMMM d");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    public ShowGamePresenter(IShowGameView newview, IMyGamesModel newmodel) {
        view = newview;
        model = newmodel;
    }

    public void setGame(int idGame) {
        currentGameId = idGame;
        GameData gameData = model.getGameData(currentGameId);
        if (gameData == null) {
            view.setGameName("THIS GAME DOES NOT EXIST IN LOCAL DB");
            view.setGameSummary(NO_VALUE);
            view.setGameComment(NO_COMMENT);
            view.setGamePublishers(NO_VALUE);
            view.setGamePlatforms(NO_VALUE);
            view.setGameGenres(NO_VALUE);
            view.setGamePegi(NO_VALUE);
            view.setGameRating(0.0);
            view.setGameTotalRating(0.0);
            view.setGameReleaseDate(NO_VALUE);
        } else {
            view.setGameName(gameData.getName());
            view.setGameSummary(gameData.getSummary());
            view.setGamePublishers(gameData.getPublishers());
            if(gameData.getComment()!= NO_COMMENT)
            {
                view.setGameComment(gameData.getComment());
            }
            else
            {
                view.setGameComment(NO_COMMENT);
            }
            view.setGamePlatforms(gameData.getPlatforms());
            view.setGameGenres(gameData.getGenres());
            if(gameData.getPegi()!= NO_VALUE)
            {
                view.setGamePegi(gameData.getPegi());
            }
            else
            {
                view.setGamePegi(NO_VALUE);
            }
            double gameRating = gameData.getRating();
            if(gameRating!= (double)NO_NUMBER)
            {
                view.setGameRating(gameRating/ RATIO_RATINGS);
            }
            else
            {
                view.setGameRating(0.0);

            }
            view.setGameTotalRating(gameData.getTotalRating());
            currentGameState = gameData.getState();
            view.setGameState(currentGameState);
            if (gameData.getReleaseDate() !=(double)NO_NUMBER){
                sdf.format(gameData.getReleaseDate()*1000);
                String date = sdf.toString();
                view.setGameReleaseDate(date);
            }
            else
            {
                view.setGameReleaseDate(NO_VALUE);
            }

            String cover = gameData.getCover();
            if (!cover.equals(NO_VALUE))

                model.requestCover(cover,currentGameId,new ResponseReceiver<File>()
                {

                    @Override
                    public void onResponseReceived(File response) {
                        view.setGameCover(response);
                    }

                    @Override
                    public void onErrorReceived(String message) {
                        view.showError(message);
                    }
                });
        }

    }
    public void onChangedGameState(GameData.MyGameState wanting) {
        if(wanting!=currentGameState)
        {
            currentGameState = wanting;
            model.changeState(currentGameId,currentGameState);
            view.setGameState(currentGameState);
        }
    }

    public void onRequestToChangeComment() {
        view.askComment();
    }

    public void replaceComment(String comment) {
        if(comment != null || comment != "")
        {
            model.changeComment(currentGameId,comment);
            view.setGameComment(comment);
        }
    }
}
