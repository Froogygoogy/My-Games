package MyGames.showGame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
        View view = context.getLayoutInflater().inflate(R.layout.ask_for_comment, null);
        comment = view.findViewById(R.id.editTextComment);
        builder.setView(view);
        builder.setTitle("Write a comment");
        builder.setPositiveButton("ADD COMMENT",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(confirmedComment != null)
                        {
                            confirmedComment.onConfirmedComment(comment.getText().toString());
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
