package MyGames.addGame;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.froogygoogy.mygames.R;

import java.util.List;

import MyGames.AskGameConfirmationDialog;
import MyGames.MyGamesActivity;
import MyGames.model.MyGamesModel;

public class AddGameActivity extends AppCompatActivity implements IAddGameView, AskGameConfirmationDialog.IConfirmedListener    {
    public static final String GAME_NAME = "gameName";
    private ListView games_list;
    private TextView no_found_text;
    private ProgressBar progress_bar;
    private AddGamePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        games_list = findViewById(R.id.games_list);
        no_found_text = findViewById(R.id.not_found_text);
        progress_bar = findViewById(R.id.progress_bar);
        games_list.setEmptyView(no_found_text);
        presenter = new AddGamePresenter(this, MyGamesModel.getInstance());

        Intent intent = getIntent();
        String gameName = intent.getStringExtra(GAME_NAME);
        presenter.setSearchedGameName(gameName);
    }

    @Override
    public void showTitle(String gameName) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(gameName);
    }

    @Override
    public void showSearchInProgress() {
        games_list.setVisibility(View.GONE);
        no_found_text.setVisibility(View.GONE);
        progress_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSearchInProgress() {
        games_list.setVisibility(View.VISIBLE);
        no_found_text.setVisibility(View.GONE);
        progress_bar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

        Snackbar snackbar =
                Snackbar.make(findViewById(R.id.not_found_text),
                        message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// ...
            }
        });
        snackbar.show();
    }

    @Override
    public void displayNames(List<String> names) {
        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        games_list.setAdapter(adapter);
        games_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                presenter.onAddGameRequested(position);
            }
        });

    }

    @Override
    public void askGameInsertionConfirmation(String name, String summary) {
        AskGameConfirmationDialog dialog = new AskGameConfirmationDialog();
        Bundle params = new Bundle();
        //put the required params in the bundle
        params.putString("name",name);
        params.putString("summary",summary);

        dialog.setArguments(params);
        dialog.show(getFragmentManager(), "AskGameInsertionConfirmation");
    }

    @Override
    public void switchToMyGames() {
        // Create the Intent
        Intent intent = new Intent(this, MyGamesActivity.class);

        // Start the activity:
        startActivity(intent);

    }

    @Override
    public void onActionConfirmed() {
        presenter.onAddGameConfirmed();
    }

}
