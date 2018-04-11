package MyGames;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.froogygoogy.mygames.R;

import java.util.List;

import MyGames.addGame.AddGameActivity;
import MyGames.model.MyGamesModel;
import MyGames.showGame.ShowGameActivity;

public class MyGamesActivity extends AppCompatActivity implements IMyGamesView, AskGameNameDialog.IGameNameListener, AskGameConfirmationDialog.IConfirmedListener{

    private MyGamesPresenter presenter;
    private TextView noNames;
    private ListView namesList;
    public static final int DISPLAY_GAMES = 1;
    public static final int DISPLAY_PLATFORMS = 2;
    public static final int DISPLAY_GENRES = 3;
    private int displayMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_games);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(savedInstanceState != null)
        {
            displayMode = savedInstanceState.getInt("Display");
        }
        else
        {
            displayMode = DISPLAY_GAMES;
        }


        noNames = findViewById(R.id.textViewVoid);
        namesList = findViewById(R.id.listView);
        presenter = new MyGamesPresenter(this, MyGamesModel.getInstance(getApplicationContext()));
        namesList.setEmptyView(noNames);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AskGameNameDialog askGameNameDialog = new AskGameNameDialog();
                askGameNameDialog.show(getFragmentManager(), "AskGameName");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_games, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_games) {
            presenter.onDisplayModeSelected(DISPLAY_GAMES);
            return true;
        } else if (id == R.id.action_platforms) {
            presenter.onDisplayModeSelected(DISPLAY_PLATFORMS);
            return true;
        } else if (id == R.id.action_genres) {
            presenter.onDisplayModeSelected(DISPLAY_GENRES);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNameInput(String name) {

        presenter.onAddGameRequested(name);
    }

    @Override
    public void switchToAddGame(String name) {
        // Create the Intent
        Intent intent = new Intent(this, AddGameActivity.class);
// Add the parameters to AddGameActivity
        intent.putExtra(AddGameActivity.GAME_NAME, name);
// Start the activity:
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        presenter.onViewStarted();
    }

    @Override
    public void displayNames(List<String> names)
    {
        ListAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        namesList.setAdapter(adapter);
        namesList.setLongClickable(false);
        namesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                presenter.onShowNameRequested(position);
            }
        });

        if(names.size()>=1)
        {
            noNames.setText("No games found");
        }
        else
        {
            noNames.setText("");
        }
        if (displayMode == DISPLAY_GAMES)
        {
            namesList.setLongClickable(true);
            namesList.setOnItemLongClickListener(
                    new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view,
                                                       int position, long id) {
                            presenter.onDeleteGameRequested(position);
                            return true;
                        }
                    });
        }
    }

    @Override
    public int getDisplay() {
        return displayMode;
    }

    @Override
    public void setDisplay(int mode) {
        displayMode = mode;
    }

    @Override
    public void switchToGame(Integer integer) {
        // Create the Intent
        Intent intent = new Intent(this, ShowGameActivity.class);
// Add the parameters to AddGameActivity
        intent.putExtra(ShowGameActivity.key, integer);
// Start the activity:
        startActivity(intent);

    }

    @Override
    public void askGameDeletionConfirmation(String gameName, String gameSummary) {
        AskGameConfirmationDialog dialog = new AskGameConfirmationDialog();
        Bundle params = new Bundle();
        params.putString("title","Please, confirm deletion");
        params.putString("name",gameName);
        params.putString("summary",gameSummary);
        params.putString("question","Do you really want to delete this game?");
        dialog.setArguments(params);
        dialog.show(getFragmentManager(), "AskGameDeletionConfirmation");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("Display",displayMode);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActionConfirmed() {
        presenter.onGameDeletionConfirmed();

    }
}
