package ru.ivadimn.health.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.ivadimn.health.App;
import ru.ivadimn.health.R;
import ru.ivadimn.health.Utils;
import ru.ivadimn.health.database.DataManage;
import ru.ivadimn.health.database.Values;
import ru.ivadimn.health.fragments.DateDlgFragment;
import ru.ivadimn.health.fragments.PressDlgFragment;
import ru.ivadimn.health.model.Press;
import ru.ivadimn.health.model.PressValue;
import ru.ivadimn.health.model.PressValueContract;

public class PressActivity extends AppCompatActivity implements DateDlgFragment.OnDateSetListener{

    private static final String DATE = "DATE";
    private static final String COMMENT = "COMMENT";
    public static final String KEY = "KEY";
    public static final int NEW_RECORD = -1;

    private Button txtDate;
    private EditText edComment;
    private RecyclerView recycler;

    private DataManage data;
    private List<PressValue> results = new ArrayList<>();

    private long keyPress;
    private long datePress;

    public static Intent createIntent(Context context, Press press) {
        Intent intent = new Intent(context, PressActivity.class);
        if (press != null) {
            intent.putExtra(DATE, press.getDate());
            intent.putExtra(COMMENT, press.getComment());
            intent.putExtra(KEY, press.get_id());
        }
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_press);
        data = App.getInstance().getDataManage();
        initUI();
        initData(getIntent());

    }

    private void initUI() {
        txtDate = (Button) findViewById(R.id.press_date_id);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });
        edComment = (EditText) findViewById(R.id.edit_comment_id);
        recycler = (RecyclerView) findViewById(R.id.list_result_id);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData(Intent intent) {
        keyPress = intent.getLongExtra(KEY, NEW_RECORD);
        datePress = intent.getLongExtra(DATE, System.currentTimeMillis());
        txtDate.setText(Utils.stringDate(datePress));
        edComment.setText(intent.getStringExtra(COMMENT));
        if (keyPress == NEW_RECORD) return;
        getPressResults();
    }


    @Override
    public void onDateSelect(long date) {
        datePress = date;
        txtDate.setText(Utils.stringDate(date));
    }

    private void selectDate() {
        DateDlgFragment dlg = DateDlgFragment.createDialog(System.currentTimeMillis(), this);
        dlg.show(getSupportFragmentManager(), DateDlgFragment.TAG);
    }

    private void getPressResults() {
        List<Values> vals = data.selectAll(PressValue.shema, PressValueContract.COLUMN_PRESSURE_ID + " = ?",
                new String[] {Utils.string(keyPress)});
        for (int i = 0; i < vals.size(); i++) {
            PressValue pv = new PressValue();
            pv.setValues(vals.get(i));
            results.add(pv);
        }
    }

    private PressDlgFragment.DialogListener listener = new PressDlgFragment.DialogListener() {
        @Override
        public void onOkClick(byte systol, byte diastol, byte puls) {

        }
    };

    private void editValues(int pos) {
        PressDlgFragment dlg;
        if (pos == NEW_RECORD)
            dlg = PressDlgFragment.createDialog(pos, null, listener);
        else
            dlg = PressDlgFragment.createDialog(pos, results.get(pos), listener);
        dlg.show(getSupportFragmentManager(), PressDlgFragment.TAG);
    }
}
