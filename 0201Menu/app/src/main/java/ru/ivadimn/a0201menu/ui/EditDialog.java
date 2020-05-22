package ru.ivadimn.a0201menu.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.EditText;

import ru.ivadimn.a0201menu.R;
import ru.ivadimn.a0201menu.model.Note;

/**
 * Created by vadim on 17.07.17.
 */

public class EditDialog extends AppCompatDialogFragment {
    public static final String INDEX = "INDEX";
    public static final String NOTE = "NOTE";

    private DialogListener listener;
    private int position = -1;

    private EditText edTitle;
    private EditText edText;

    public interface DialogListener {
        public void onOkClick(int position, Note note);
    }

    public static EditDialog createDailog(int pos, Note note, DialogListener listener) {
        EditDialog dlg = new EditDialog();
        dlg.setListener(listener);
        if (note != null) {
            Bundle args = new Bundle();
            args.putInt(INDEX, pos);
            args.putParcelable(NOTE, (Parcelable) note);
            dlg.setArguments(args);
        }
        return dlg;
   }

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.edit_name);
        Bundle args = getArguments();
        builder.setView(init(args));
        builder.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (listener != null) {
                    listener.onOkClick(position,
                            new Note(edTitle.getText().toString(), edText.getText().toString()));
                }
            }
        });
        builder.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        return builder.create();
    }

    private  View init(Bundle args) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.edit_dlg, null);
        edTitle = (EditText) view.findViewById(R.id.edit_title_id);
        edText = (EditText) view.findViewById(R.id.edit_text_id);
        if (args != null) {
            Note note = args.getParcelable(NOTE);
            position = args.getInt(INDEX, -1);
            edTitle.setText(note.getTitle());
            edText.setText(note.getContent());
        }
        return view;

    }
}
