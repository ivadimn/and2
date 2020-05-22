package ru.ivadimn.a0202storage.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by vadim on 23.07.2017.
 */

public class DatePickerFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String DATE = "DATE";

    private int day;
    private int month;
    private int year;
    private OnDateSetListener listener;

    public interface OnDateSetListener {
        public void onDateSelect(long date);
    }

    private final Calendar calendar = Calendar.getInstance();

    public static DatePickerFragment createFragment(long d) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putLong(DATE, d);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDateSetListener)
            listener = (OnDateSetListener) context;
        else
            throw new RuntimeException("This context is not OnDateSetListener interface");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            calendar.setTimeInMillis(args.getLong(DATE, 0));
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
        }
         return new DatePickerDialog(getContext(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        calendar.set(y, m, d);
        if (listener != null)
            listener.onDateSelect(calendar.getTimeInMillis());
    }
}
