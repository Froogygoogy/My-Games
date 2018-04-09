package MyGames.showGame;

import java.io.File;

import MyGames.model.GameData;

public interface IShowGameView {
    void setGameName(String gameName);
    void setGameSummary(String gameSummary);
    void setGameComment(String gameComment);
    void setGamePublishers(String gamePublishers);
    void setGamePlatforms(String gamePlatforms);
    void setGameGenres(String gameGenres);
    void setGamePegi(String gamePegi);
    void setGameRating(double rating);
    void setGameTotalRating(double rating);
    void setGameReleaseDate(String gameReleaseDate);
    void setGameState(GameData.MyGameState gameState);
    void setGameCover(File cover);

    void showError(String message);

    void askComment();
}
