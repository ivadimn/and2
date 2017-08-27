package ru.ivadimn.health.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

import ru.ivadimn.health.Utils;

/**
 * Created by vadim on 27.08.2017.
 */

public class DateDlgFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener  {

    private static final String DATE = "DATE";

    public static DateDlgFragment createDialog(long date) {
        DateDlgFragment dlg = new DateDlgFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(DATE, date);
        dlg.setArguments(bundle);
        return dlg;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(args.getLong(DATE));
        return new DatePickerDialog(getContext(), this, c.get(Calendar.YEAR),
                c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        Toast.makeText(getContext(), Utils.stringDate(c.getTimeInMillis()), Toast.LENGTH_SHORT).show();
    }
}
