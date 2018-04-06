package MyGames.addGame;

import java.util.List;

/**
 * Created by Froogygoogy on 20/03/2018.
 */

public interface IAddGameView {
    void showTitle(String gameName);
    void showSearchInProgress();
    void hideSearchInProgress();
    void showError(String message);
    void displayNames(List<String> names);
    void askGameInsertionConfirmation(String name,String summary);

    void switchToMyGames();
}
