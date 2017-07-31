package ru.ivadimn.a0205threads;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by vadim on 30.07.2017.
 */

public class ProgressDlg extends AppCompatDialogFragment {

    private static final String MESSAGE = "MESSAGE";

    private TextView txtMessage;

    public static ProgressDlg createDialog(String message) {
        ProgressDlg dlg = new ProgressDlg();
        if (message != null) {
            Bundle args = new Bundle();
            args.putString(MESSAGE, message);
            dlg.setArguments(args);
        }
        return dlg;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        Bundle args = getArguments();
        builder.setView(init(args));
        builder.setCancelable(false);
        return builder.create();
    }

    private View init(Bundle args) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_process_dlg, null);
        txtMessage = (TextView) view.findViewById(R.id.progress_text_id);

        if (args != null) {
            txtMessage.setText(args.getString(MESSAGE));
        }
        return view;
    }
}
