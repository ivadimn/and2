package ru.ivadimn.a0202storage.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.ivadimn.a0202storage.App;
import ru.ivadimn.a0202storage.activities.PersonActivity;
import ru.ivadimn.a0202storage.interfaces.IDataStore;
import ru.ivadimn.a0202storage.model.Person;
import ru.ivadimn.a0202storage.R;
import ru.ivadimn.a0202storage.adapters.PersonAdapter;

import static android.app.Activity.RESULT_OK;

/**
 * Created by vadim on 20.07.17.
 */

public class PersonListFragment extends Fragment {

    public static final String TAG = "LIST_PERSONS";
    public static final int ADD_PERSON = 1;
    public static final int EDIT_PERSON = 2;

    private RecyclerView list;
    private List<Person> persons;
    private PersonAdapter adapter;
    private FloatingActionButton fab;
    private MenuItem itemDelete;
    private IDataStore store;

    public static Fragment createFragment() {
        Fragment fragment = new PersonListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        store = App.getInstance().getStore(App.FILE_STORAGE);
        persons = store.getList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        list = (RecyclerView) view.findViewById(R.id.person_list_id);
        fab = (FloatingActionButton) view.findViewById(R.id.fab_id);
        fab.setOnClickListener(fabClick);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PersonAdapter(personClick);
        list.setAdapter(adapter);
        adapter.updateData(persons);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_person, menu);
        itemDelete = menu.findItem(R.id.menuitem_delete_id);
    }

    @Override
    public void onStop() {
        super.onStop();
        store.saveList();
    }

    //
    private View.OnClickListener fabClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = PersonActivity.createIntent(getContext(), -1);
            startActivityForResult(intent, ADD_PERSON);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
            adapter.notifyDataSetChanged();
    }

    private PersonAdapter.PersonClickListener personClick = new PersonAdapter.PersonClickListener() {
        @Override
        public void onClick(View view, int position) {
            //start edit activity
        }

        @Override
        public void onLongClick(View view, int position) {
            //view delete menu
        }
    };
}
