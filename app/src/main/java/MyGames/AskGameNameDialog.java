package MyGames;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.EditText;


import com.example.froogygoogy.mygames.R;

/**
 * Created by Froogygoogy on 20/03/2018.
 */

public class AskGameNameDialog extends DialogFragment {
    public interface IGameNameListener {
        void onNameInput(String name);
    }
    EditText gameNameEdit;
    IGameNameListener gameNameListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = context.getLayoutInflater().inflate(
                R.layout.lookfor_name_layout, null);
        gameNameEdit = view.findViewById(R.id.editText);
        builder.setView(view);
        builder.setPositiveButton("SEARCH",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(gameNameListener != null)
                        {
                            gameNameListener.onNameInput(gameNameEdit.getText().toString());
                        }
                    }
                }
        );
        builder.setNegativeButton("Cancel",null);

        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        gameNameListener = (IGameNameListener)context;
    }


}
