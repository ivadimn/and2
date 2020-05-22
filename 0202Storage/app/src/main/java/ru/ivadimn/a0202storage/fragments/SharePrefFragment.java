package ru.ivadimn.a0202storage.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ru.ivadimn.a0202storage.R;
import ru.ivadimn.a0202storage.storage.SettingStore;

/**
 * Created by vadim on 19.07.17.
 */

public class SharePrefFragment extends Fragment {

    public static final String TAG = "SharePrefFragment";

    private Button btnSave;
    private Button btnFind;
    private Button btnClear;
    private EditText edKey;
    private EditText edValue;

    private SettingStore settingStore;

    public static Fragment createFragment() {
        return new SharePrefFragment();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingStore = new SettingStore(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shared_pref, container, false);
        btnSave = (Button) view.findViewById(R.id.btn_save_id);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSetting();
            }
        });
        btnFind = (Button) view.findViewById(R.id.btn_find_id);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSetting();
            }
        });
        btnClear = (Button) view.findViewById(R.id.btn_clear_id);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSetting();
            }
        });
        edKey = (EditText) view.findViewById(R.id.edit_key_id);
        edValue = (EditText) view.findViewById(R.id.edit_value_id);
        return view;
    }

    private void addSetting() {
        String key = edKey.getText().toString();
        String value = edValue.getText().toString();
        if (!key.isEmpty() && !value.isEmpty()) {
            settingStore.addOption(key, value);
            Toast.makeText(getActivity(), "Key - " + key + " and Value - " + value + " stored",
                    Toast.LENGTH_SHORT).show();
            edKey.setText("");
            edValue.setText("");
        }
    }

    private void checkSetting() {
        String key = edKey.getText().toString();
        String value = settingStore.checkOption(key);
        if (value != null)
            edValue.setText(value);
        else
            Toast.makeText(getActivity(), "Key - " + key + " not found", Toast.LENGTH_SHORT).show();

    }

    private void deleteSetting() {
        String key = edKey.getText().toString();
        settingStore.deleteOption(key);
        Toast.makeText(getActivity(), "Key - " + key + " deleted", Toast.LENGTH_SHORT).show();
        edKey.setText("");
        edValue.setText("");
    }
}
