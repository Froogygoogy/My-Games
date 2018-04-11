package MyGames;

import java.util.ArrayList;
import java.util.List;

import MyGames.model.MyGamesModel;

import static MyGames.MyGamesActivity.DISPLAY_GAMES;
import static MyGames.MyGamesActivity.DISPLAY_GENRES;
import static MyGames.MyGamesActivity.DISPLAY_PLATFORMS;

/**
 * Created by Froogygoogy on 20/03/2018.
 */


public class MyGamesPresenter {

    IMyGamesView view;
    MyGamesModel model;
    List<Integer> itemsIds;
    List<String> itemNames;
    private int gameToDelete;


    public MyGamesPresenter(IMyGamesView newView,MyGamesModel newModel) {
        view = newView;
        model = newModel;
    }

    public void onAddGameRequested(String name) {

        view.switchToAddGame(name);
    }

    public void onViewStarted() {
         /*itemsIds = model.getAllGames();
         itemNames = new ArrayList<String>();
        for (int i:itemsIds) {
            itemNames.add(model.getGameName(i));
        }
        view.displayNames(itemNames);*/
        int currentMode = view.getDisplay();
        onSetMode(currentMode);
    }

    public void onDisplayModeSelected(int mode) {
        int currentMode = view.getDisplay();

        if (mode != currentMode) {
            onSetMode(mode);
            view.setDisplay(mode);
        }
    }
    private void onSetMode(int mode)
    {
        switch (mode) {
            case DISPLAY_GAMES:
                itemsIds = model.getAllGames();
                itemNames = new ArrayList<String>();
                for (int i:itemsIds) {
                    itemNames.add(model.getGameName(i));
                }
                view.displayNames(itemNames);
                break;
            case DISPLAY_PLATFORMS:
                List<String> platforms = model.getAllPlatforms();
                view.displayNames(platforms);
                break;

            case DISPLAY_GENRES:
                List<String> genres = model.getAllGenres();
                view.displayNames(genres);
                break;
        }
    }

    public void onShowNameRequested(int position) {
        int currentMode = view.getDisplay();
        switch (currentMode) {
            case DISPLAY_GAMES:
                view.switchToGame(itemsIds.get(position));
                return;
            case DISPLAY_PLATFORMS:
                itemsIds = model.getGamesWithPlatform(itemNames.get(position));
                break;
            case DISPLAY_GENRES:
                itemsIds = model.getGamesWithGenre(itemNames.get(position));
                break;
        }
        view.setDisplay(DISPLAY_GAMES);
        onSetMode(currentMode);
    }

    public void onDeleteGameRequested(int position) {
        gameToDelete = itemsIds.get(position);

        view.askGameDeletionConfirmation(itemNames.get(position),model.getGameSummary(gameToDelete));
    }

    public void onGameDeletionConfirmed() {
        model.deleteGame(gameToDelete);
        view.setDisplay(DISPLAY_GAMES);
        onSetMode(DISPLAY_GAMES);
        view.displayNames(itemNames);
    }
}
