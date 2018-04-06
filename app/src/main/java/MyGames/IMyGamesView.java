package MyGames;

import java.util.List;

/**
 * Created by Froogygoogy on 20/03/2018.
 */

public interface IMyGamesView {
    void switchToAddGame(String name);
    void displayNames(List<String> names);
    int getDisplay();
    void setDisplay(int mode);

    void switchToGame(Integer integer);
}
