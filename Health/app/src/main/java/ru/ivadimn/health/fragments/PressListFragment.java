package ru.ivadimn.health.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.ivadimn.health.R;
import ru.ivadimn.health.adapters.PressAdapter;
import ru.ivadimn.health.database.DataManage;
import ru.ivadimn.health.database.DbEntity;
import ru.ivadimn.health.database.Values;
import ru.ivadimn.health.model.Press;

/**
 * Created by vadim on 26.08.2017.
 */

public class PressListFragment extends PagerFragment {

    public static final String TAG = "PressListFragment";

    private static PressListFragment instance;

    private DataManage data = new DataManage();
    private List<Press> listPress;
    private RecyclerView recycler;
    private PressAdapter adapter;
    private FloatingActionButton fab;

    public static PagerFragment getInstance(String title) {
        instance = new PressListFragment();
        instance.setTitle(title);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Values> vals = data.selectAll(Press.shema, null, null);
        listPress = new ArrayList<>();
        for (int i = 0; i < vals.size(); i++) {
            Press press = new Press();
            press.setValues(vals.get(i));
            listPress.add(press);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_press, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View v) {
        recycler = (RecyclerView) v.findViewById(R.id.list_press_id);
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false));
        adapter = new PressAdapter();
        recycler.setAdapter(adapter);
        adapter.updateData(listPress);
        fab = (FloatingActionButton) v.findViewById(R.id.fab_id);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog() {
        DateDlgFragment dlg = DateDlgFragment.createDialog(System.currentTimeMillis());
        dlg.show(getFragmentManager(), "DATE_PICKER");
    }
}

