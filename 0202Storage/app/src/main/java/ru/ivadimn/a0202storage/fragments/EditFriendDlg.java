package ru.ivadimn.a0202storage.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.EditText;

import ru.ivadimn.a0202storage.R;

/**
 * Created by vadim on 23.07.2017.
 */

public class EditFriendDlg extends AppCompatDialogFragment  {
    public static final String INDEX = "INDEX";
    public static final String NAME = "NAME";

    private DialogListener listener;
    private int position = -1;

    private EditText edName;

    public interface DialogListener {
        public void onOkClick(int position, String name);
    }

    public static EditFriendDlg createDialog(int pos, String name, DialogListener listener) {
        EditFriendDlg dlg = new EditFriendDlg();
        dlg.setListener(listener);
        if (name != null) {
            Bundle args = new Bundle();
            args.putInt(INDEX, pos);
            args.putString(NAME, name);
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
        builder.setTitle(R.string.friend_name);
        Bundle args = getArguments();
        builder.setView(init(args));
        builder.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (listener != null) {
                    listener.onOkClick(position, edName.getText().toString());
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

    private View init(Bundle args) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dlg_friend, null);
        edName = (EditText) view.findViewById(R.id.edit_friend_id);

        if (args != null) {
            position = args.getInt(INDEX, -1);
            edName.setText(args.getString(NAME));
        }
        return view;
    }
}
