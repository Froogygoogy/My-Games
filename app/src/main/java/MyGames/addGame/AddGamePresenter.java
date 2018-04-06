package MyGames.addGame;

import java.util.ArrayList;
import java.util.List;

import MyGames.model.AllGameData;
import MyGames.model.GameData;
import MyGames.model.IMyGamesModel;
import MyGames.model.MyGamesModel;
import MyGames.model.inetserver.ResponseReceiver;

/**
 * Created by Froogygoogy on 20/03/2018.
 */

public class AddGamePresenter {
    private IAddGameView view;
    private IMyGamesModel model;
    private List<String> names;
    private int requestedIndex;
    private List<AllGameData> games;
    public AddGamePresenter(IAddGameView newview, IMyGamesModel newmodel) {
        view = newview;
        model = newmodel;
    }

    public void setSearchedGameName(String searchedGameName)
    {
        view.showTitle(searchedGameName);
        view.showSearchInProgress();
        model.findGames(searchedGameName,
                new ResponseReceiver<List<AllGameData>>() {
                    @Override
                    public void onResponseReceived(List<AllGameData> response) {
                        view.hideSearchInProgress();
                        gamesFound(response);
                    }
                    @Override
                    public void onErrorReceived(String message) {
                        view.hideSearchInProgress();
                        view.showError(message);
                    }
                });
    }

    private void gamesFound(List<AllGameData> response) {
        names = new ArrayList<String>();
        games = new ArrayList<AllGameData>();
        for (AllGameData game:response) {
            names.add(game.getGame().getName().toString());
            games.add(game);
        }

        view.displayNames(names);
    }

    public void onAddGameRequested(int position) {
        requestedIndex = position;
        GameData gameData = games.get(position).getGame();
        view.askGameInsertionConfirmation(gameData.getName(),
                gameData.getSummary());
    }

    public void onAddGameConfirmed() {
        model.insertGame(games.get(requestedIndex));
        view.switchToMyGames();
    }
}
