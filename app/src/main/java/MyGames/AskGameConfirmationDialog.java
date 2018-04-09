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
import android.widget.TextView;

import com.example.froogygoogy.mygames.R;

/**
 * Created by Froogygoogy on 31/03/2018.
 */

public class AskGameConfirmationDialog extends DialogFragment {
    public interface  IConfirmedListener {
        void onActionConfirmed();
    }
    TextView name;
    TextView summary;

    IConfirmedListener confirmedListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = context.getLayoutInflater().inflate(R.layout.ask_for_game_confirmation_layout, null);
        Bundle parameters = getArguments();
        name =  view.findViewById(R.id.textViewName);
        summary =  view.findViewById(R.id.textViewSummary);
        name.setText("Name: " + parameters.getString("name"));
        summary.setText("Summary: " + parameters.getString("summary"));
        builder.setView(view);
        builder.setPositiveButton("YES,PROCEED",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(confirmedListener != null)
                        {
                            confirmedListener.onActionConfirmed();
                        }
                    }
                }
        );
        builder.setNegativeButton("NOT AT ALL",null);
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        confirmedListener = (IConfirmedListener) context;
    }

}
