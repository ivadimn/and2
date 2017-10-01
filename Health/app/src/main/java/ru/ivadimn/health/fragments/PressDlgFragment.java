package ru.ivadimn.health.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import ru.ivadimn.health.R;
import ru.ivadimn.health.Utils;
import ru.ivadimn.health.model.Press;
import ru.ivadimn.health.model.PressValue;

/**
 * Created by vadim on 28.08.2017.
 */

public class PressDlgFragment extends AppCompatDialogFragment {

    public static final String TAG = "PressDlgFragment";
    private static final String SYSTOLIC = "SYSTOLIC";
    private static final String DIASTOLIC = "DIASTOLIC";
    private static final String PULS = "PULS";
    private static final String INDEX = "INDEX";

    private EditText edSystol;
    private EditText edDiastol;
    private EditText edPuls;

    private DialogListener listener;

    public interface DialogListener {
        public void onOkClick(byte systol, byte diastol, byte puls);
    }

    public static PressDlgFragment createDialog(int pos, PressValue p, DialogListener listener)  {
        PressDlgFragment dlgFragment = new PressDlgFragment();
        Bundle args = new Bundle();
        args.putInt(INDEX, pos);
        if (p != null) {
            args.putByte(SYSTOLIC, p.getSystol());
            args.putByte(DIASTOLIC, p.getDiastol());
            args.putByte(PULS, p.getPuls());
        }
        dlgFragment.setArguments(args);
        return dlgFragment;
    }

    public void setDialogListener(DialogListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(R.string.blood_pressure);
        builder.setView(init(getArguments()));
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Byte.parseByte(edSystol.getText().toString());
                if (listener != null)
                    listener.onOkClick(Byte.parseByte(edSystol.getText().toString()),
                            Byte.parseByte(edDiastol.getText().toString()),
                            Byte.parseByte(edPuls.getText().toString()));
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        return builder.create();
    }

    private View init(Bundle args) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.press_value_layout, null);
        edSystol = (EditText) view.findViewById(R.id.press_systolic_id);
        edDiastol = (EditText) view.findViewById(R.id.press_diastolic_id);
        edPuls = (EditText) view.findViewById(R.id.puls_id);
        edSystol.setText(Utils.string(args.getByte(SYSTOLIC, (byte) 0)));
        edDiastol.setText(Utils.string(args.getByte(DIASTOLIC, (byte) 0 )));
        edPuls.setText(Utils.string(args.getByte(PULS, (byte) 0 )));
        return view;
    }
}
