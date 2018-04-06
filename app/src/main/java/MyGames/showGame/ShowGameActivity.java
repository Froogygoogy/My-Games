package MyGames.showGame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.froogygoogy.mygames.R;

import static MyGames.model.GameData.MyGameState.PLAYED;
import static MyGames.model.GameData.MyGameState.PLAYING;
import static MyGames.model.GameData.MyGameState.WANTING;

public class ShowGameActivity extends AppCompatActivity implements IShowGameView {
    public static final String key = "KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game);
    }
    public void onWantingSelected(View view) {
        //presenter.onChangedGameState(WANTING);
    }
    public void onPlayingSelected(View view) {
        //presenter.onChangedGameState(PLAYING);
    }
    public void onPlayedSelected(View view) {
        //presenter.onChangedGameState(PLAYED);
    }

}
