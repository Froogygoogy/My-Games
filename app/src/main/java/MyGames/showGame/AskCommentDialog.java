package MyGames.showGame;

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

public class AskCommentDialog extends DialogFragment{
    public interface IConfirmedCommentListener {
        void onConfirmedComment(String comment);
    }
    private IConfirmedCommentListener confirmedComment;
    private EditText comment;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final Layout lookfor_name_layout;
        View view = context.getLayoutInflater().inflate(
                R.layout.activity_ask_comment_dialog, null);
        comment = view.findViewById(R.id.editTextComment);
        builder.setPositiveButton("ADD COMMENT",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(confirmedComment != null)
                        {

                        }
                    }
                }
        );
        builder.setNegativeButton("FORGET IT",null);
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        confirmedComment = (IConfirmedCommentListener)context;
    }

}
