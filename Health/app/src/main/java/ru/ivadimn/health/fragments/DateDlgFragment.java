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

    public static final String TAG = "DateDlgFragment";
    private static final String DATE = "DATE";

    private OnDateSetListener listener;

    public interface OnDateSetListener {
        public void onDateSelect(long date);
    }

    public static DateDlgFragment createDialog(long date, OnDateSetListener listener) {
        DateDlgFragment dlg = new DateDlgFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(DATE, date);
        dlg.setArguments(bundle);
        dlg.setDateSelectListener(listener);
        return dlg;
    }

    public void setDateSelectListener(OnDateSetListener listener) {
        this.listener = listener;
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
        if (listener != null)
            listener.onDateSelect(c.getTimeInMillis());
    }
}
