package MyGames.model.inetserver;

import android.os.AsyncTask;

import MyGames.model.AllGameData;
import MyGames.model.GameData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static MyGames.model.GameData.NO_NUMBER;
import static MyGames.model.GameData.NO_COMMENT;
import static MyGames.model.GameData.MyGameState.WANTING;
import static MyGames.model.GameData.NO_VALUE;

/**
 * Created by jcamen on 21/02/18.
 * A mock Inet server that only contains a set of given games
 */

public class MockGamesServer implements IGamesServer {
    private static final List<AllGameData> allGames;

    static {
        GameData gameData;
        List<String> listOfPlatforms;
        List<String> listOfGenres;
        allGames = new ArrayList<>();
        gameData = createGame(26772, "NBA 2K18", "The highest rated annual sports title of this generation returns with NBA 2K18, featuring unparalleled authenticity and improvements on the court.",
                79.4, 85.0, "2K Games", 1505433600000L, "//images.igdb.com/igdb/image/upload/t_thumb/e9tzvkbwsvy9ung6sfgk.jpg",
                "The content of this game is suitable for all persons.");
        listOfPlatforms = createListOfNames("Nintendo Switch", "PlayStation 4", "Xbox One", "Xbox 360", "PlayStation 3", "PC (Microsoft Windows)");
        listOfGenres = createListOfNames("Simulator", "Sport");
        addFullGame(gameData, listOfPlatforms, listOfGenres);
        gameData = createGame(28540, "Assassin’s Creed: Origins", "For the last four years, the team behind Assassin’s Creed IV Black Flag has been crafting a new beginning for the Assassin’s Creed franchise. \n \nSet in Ancient Egypt, players will journey to the most mysterious place in history, during a crucial period that will shape the world and give rise to the Assassin’s Brotherhood. Plunged into a living, systemic and majestic open world, players are going to discover vibrant ecosystems, made of diverse and exotic landscapes that will provide them with infinite opportunities of pure exploration, adventures and challenges. \n \nPowered by a new fight philosophy, Assassin's Creed Originsembraces a brand new RPG direction where players level up, loot, and choose abilities to shape and customize their very own skilled Assassin as they grow in power and expertise while exploring the entire country of Ancient Egypt.",
                88.09983882282674, 90.2663443123202, "Ubisoft Entertainment", 1509062400000L,
                "//images.igdb.com/igdb/image/upload/t_thumb/l2dza03yjs6j5u6uuak2.jpg", NO_VALUE);
        listOfPlatforms = createListOfNames("PC (Microsoft Windows)", "PlayStation 4", "Xbox One");
        listOfGenres = createListOfNames("Role-playing (RPG)", "Adventure");
        addFullGame(gameData, listOfPlatforms, listOfGenres);
        gameData = createGame(26917, "Assassin's Creed: Bloodsail", "Assassin's Creed: Bloodsail is an upcoming 3D action RPG for mobile platforms developed by Ubisoft China and VGame Studios, for the Chinese market. The game is inspired by Assassin's Creed IV: Black Flag and is set in the Golden Age of Piracy.  \n \nAnnounced at the ChinaJoy 2016 expo, the game is scheduled for release in 2017 with open beta starting in March 2017 in China. \n \nThe game features the player controlling a pirate, as they explore the Caribbean, with notable cities like Havana and Nassau, encountering characters originating from Black Flag. In addition to naval battles the game will feature Player vs Enemy (PvE) and Player vs Player (PvP) modes.",
                (double) NO_NUMBER, (double) NO_NUMBER, "Ubisoft China", 1546214400000L, "//images.igdb.com/igdb/image/upload/t_thumb/kniv4awapbfwdaori6l1.jpg",
                NO_VALUE);
        listOfPlatforms = createListOfNames("Android", "iOS");
        listOfGenres = createListOfNames("Adventure");
        addFullGame(gameData, listOfPlatforms, listOfGenres);
        gameData = createGame(26758, "Super Mario Odyssey", "The game has Mario leaving the Mushroom Kingdom to reach an unknown open world-like setting, like Super Mario 64 and Super Mario Sunshine.",
                93.26598863978544, 89.4694772795709, "Nintendo, Nintendo of America, Nintendo of Europe", 1509062400000L,
                "//images.igdb.com/igdb/image/upload/t_thumb/u4m45xwqdq8cuu4b9elq.jpg", "The content of this game is suitable for persons aged 7 years and over only.\r\nIt contains: Non realistic looking violence towards fantasy characters - - Pictures or sounds likely to be scary to young children");
        listOfPlatforms = createListOfNames("Nintendo Switch");
        listOfGenres = createListOfNames("Platform", "Adventure");
        addFullGame(gameData, listOfPlatforms, listOfGenres);
        gameData = createGame(8594, "Mario Vs. Donkey Kong: Tipping Stars", "In this puzzle/platformer hybrid, players guide Mario Minis to the exit door using touch control. As with previous games in the series, players can create their own levels and share them with other players online. In this game, players can “tip” the creators of the user-generated puzzles with stars, which can be used to buy new parts in the robust level creator.",
                73.1875, 75.0, "Nintendo", 1425513600000L, "//images.igdb.com/igdb/image/upload/t_thumb/luquevaacsoogpaca5ib.jpg",
                "The content of this game is suitable for all persons.");
        listOfPlatforms = createListOfNames("Wii U", "Nintendo 3DS");
        listOfGenres = createListOfNames("Platform", "Puzzle");
        addFullGame(gameData, listOfPlatforms, listOfGenres);
    }

    private static void addFullGame(GameData gameData, List<String> platforms, List<String> genres) {
        AllGameData allGameData = new AllGameData();
        allGameData.setGame(gameData);
        allGameData.setPlatforms(platforms);
        allGameData.setGenres(genres);
        allGames.add(allGameData);
    }

    private static GameData createGame(int id, String name, String summary, double totalRating, double rating, String publishers,
                                       long releaseDate, String cover, String pegi) {
        GameData gameData = new GameData(id);
        gameData.setName(name);
        gameData.setSummary(summary);
        gameData.setTotalRating(totalRating);
        gameData.setRating(rating);
        gameData.setPublishers(publishers);
        gameData.setReleaseDate(releaseDate);
        gameData.setCover(cover);
        gameData.setPegi(pegi);
        gameData.setState(WANTING);
        gameData.setComment(NO_COMMENT);
        return gameData;
    }

    private static List<String> createListOfNames(String ... names) {
        List<String> theList = new ArrayList<>();
        for (String name : names)
            theList.add(name);
        return theList;
    }

    private static class DelayedFindGames extends AsyncTask<String, Void, List<AllGameData>> {
        final ResponseReceiver<List<AllGameData>> receiver;

        public DelayedFindGames(ResponseReceiver<List<AllGameData>> receiver) {
            this.receiver = receiver;
        }

        @Override
        protected List<AllGameData> doInBackground(String... strings) {
            String name = strings[0];
            List<AllGameData> result = new ArrayList<>();
            for (AllGameData allGameData : allGames)
                if (allGameData.getGame().getName().contains(name)) {
                    GameData gameData = allGameData.getGame().copy();

                    List<String> listOfPlatforms = new ArrayList<>();
                    listOfPlatforms.addAll(allGameData.getPlatforms());

                    List<String> listOfGenres = new ArrayList<>();
                    listOfGenres.addAll(allGameData.getGenres());

                    AllGameData data = new AllGameData();
                    data.setGame(gameData);
                    data.setPlatforms(listOfPlatforms);
                    data.setGenres(listOfGenres);
                    result.add(data);
                }
            return result;
        }

        @Override
        protected void onPostExecute(List<AllGameData> allGameData) {
            receiver.onResponseReceived(allGameData);
        }
    }

    @Override
    public void findGames(final String gameName, final ResponseReceiver<List<AllGameData>> receiver) {
        if (gameName.equals("error")) {
            receiver.onErrorReceived("Error while searching");
            return;
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DelayedFindGames delayedFindGames  = new DelayedFindGames(receiver);
                delayedFindGames.execute(gameName);
            }
        }, 1500);
    }

    @Override
    public void requestCover(String cover, String filename, final ResponseReceiver<File> receiver) {
        receiver.onErrorReceived("Mock does not download image covers");
    }
}
