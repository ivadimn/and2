package ru.ivadimn.menudialog.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

/**
 * Created by vadim on 14.07.17.
 */

public class StandartDlg extends DialogFragment {
    private static final String TITLE = "TITLE";
    private static final String MESSAGE = "MESSAGE";



    public static StandartDlg createStandartDlg(String title, String message) {
        StandartDlg standartDlg = new StandartDlg();
        Bundle bundle = new Bundle();
        bundle.putString(title, TITLE);
        bundle.putString(message, MESSAGE);
        standartDlg.setArguments(bundle);
        return standartDlg;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new AlertDialog.Builder(getActivity()).create();
        Bundle bundle getArguments();
        dialog.setTitle(bundle.getString(TITLE, ""));


        return dialog;
    }
}
