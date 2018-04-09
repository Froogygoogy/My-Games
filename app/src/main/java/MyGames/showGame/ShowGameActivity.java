package MyGames.showGame;

        import android.content.Intent;
        import android.graphics.Color;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.ImageView;
        import android.widget.RatingBar;
        import android.widget.TextView;

        import com.example.froogygoogy.mygames.R;

        import java.io.File;

        import MyGames.model.GameData;
        import MyGames.model.IMyGamesModel;
        import MyGames.model.MyGamesModel;

        import static MyGames.model.GameData.MyGameState.PLAYED;
        import static MyGames.model.GameData.MyGameState.PLAYING;
        import static MyGames.model.GameData.MyGameState.WANTING;

public class ShowGameActivity extends AppCompatActivity implements IShowGameView,AskCommentDialog.IConfirmedCommentListener {
    public static final String key = "KEY";
    private TextView gameName;
    private TextView gamePublishers;
    private TextView gameReleaseDate;
    private TextView gamePlatforms;
    private TextView gameGenres;
    private TextView gamePegi;
    private TextView gameSummary;
    private TextView gameComment;
    private TextView gameWaiting;
    private TextView gamePlaying;
    private TextView gamePlayed;
    private RatingBar gameTotalRating;
    private RatingBar gameRating;
    private ImageView gameCover;
    private ShowGamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game);
        gameName = findViewById(R.id.textViewNameGame);
        gamePublishers = findViewById(R.id.textViewPublishers);
        gameReleaseDate = findViewById(R.id.textViewRelease);
        gamePlatforms = findViewById(R.id.textViewPlatforms);
        gameGenres = findViewById(R.id.textViewGenres);
        gamePegi = findViewById(R.id.textViewPegi);
        gameSummary = findViewById(R.id.textViewSummary);
        gameComment = findViewById(R.id.textViewComent);
        gameWaiting = findViewById(R.id.textViewWanting);
        gamePlaying = findViewById(R.id.textViewPlaying);
        gamePlayed = findViewById(R.id.textViewPlayed);
        gameTotalRating = findViewById(R.id.ratingBarTotal);
        gameRating = findViewById(R.id.ratingBarRating);
        gameCover = findViewById(R.id.imageViewCover);
        IMyGamesModel model = MyGamesModel.getInstance();
        presenter = new ShowGamePresenter(this, model);
        Intent intent = getIntent();
        int idGame = intent.getIntExtra(key,-1);
        presenter.setGame(idGame);
        gameWaiting.setClickable(true);
        gamePlaying.setClickable(true);
        gamePlayed.setClickable(true);
    }
    public void onWantingSelected(View view) {
        presenter.onChangedGameState(WANTING);
    }
    public void onPlayingSelected(View view) {
        presenter.onChangedGameState(PLAYING);
    }
    public void onPlayedSelected(View view) {
        presenter.onChangedGameState(PLAYED);
    }

    @Override
    public void setGameName(String gameName) {
        this.gameName.setText(gameName);
    }

    @Override
    public void setGameSummary(String gameSummary) {
        this.gameSummary.setText(gameSummary);
    }

    @Override
    public void setGameComment(String gameComment) {
        this.gameComment.setText(gameComment);
        this.gameComment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onRequestToChangeComment();
            }
        }
        );

    }

    @Override
    public void setGamePublishers(String gamePublishers) {
        this.gamePublishers.setText(gamePublishers);
    }

    @Override
    public void setGamePlatforms(String gamePlatforms) {
        this.gamePlatforms.setText(gamePlatforms);
    }

    @Override
    public void setGameGenres(String gameGenres) {
        this.gameGenres.setText(gameGenres);
    }

    @Override
    public void setGamePegi(String gamePegi) {
        this.gamePegi.setText(gamePegi);
    }

    @Override
    public void setGameRating(double rating) {
        this.gameRating.setRating((float)rating);
    }

    @Override
    public void setGameTotalRating(double rating) {
        this.gameTotalRating.setRating((float)rating);
    }

    @Override
    public void setGameReleaseDate(String gameReleaseDate) {
        this.gameReleaseDate.setText(gameReleaseDate);
    }

    @Override
    public void setGameState(GameData.MyGameState gameState) {
        gameWaiting.setBackgroundColor(Color.TRANSPARENT);
        gameWaiting.setTextColor(Color.GRAY);
        gamePlaying.setBackgroundColor(Color.TRANSPARENT);
        gamePlaying.setTextColor(Color.GRAY);
        gamePlayed.setBackgroundColor(Color.TRANSPARENT);
        gamePlayed.setTextColor(Color.GRAY);
        switch (gameState) {
            case WANTING:
                gameWaiting.setBackgroundColor(Color.GREEN);
                gameWaiting.setTextColor(Color.BLACK);
                break;
            case PLAYING:
                gamePlaying.setBackgroundColor(Color.GREEN);
                gamePlaying.setTextColor(Color.BLACK);
                break;
            case PLAYED:
                gamePlayed.setBackgroundColor(Color.GREEN);
                gamePlayed.setTextColor(Color.BLACK);
                break;
        }
    }

    @Override
    public void setGameCover(File cover) {

    }

    @Override
    public void showError(String message) {
        Snackbar snackbar =
                Snackbar.make(findViewById(R.id.imageViewCover),
                        message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Ok", new OnClickListener() {
            @Override
            public void onClick(View view) {
// ...
            }
        });
        snackbar.show();
    }

    @Override
    public void askComment() {
        AskCommentDialog dialog = new AskCommentDialog();
        Bundle params = new Bundle();
        //put the required params in the bundle
        //params.putString("name",name);
        //params.putString("summary",summary);

        dialog.setArguments(params);
        dialog.show(getFragmentManager(), "AskCommentDialog");
    }

    @Override
    public void onConfirmedComment(String comment) {
        presenter.replaceComment(comment);
    }
}
