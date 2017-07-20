package ru.ivadimn.a0202storage.fragments;

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
import ru.ivadimn.a0202storage.model.Person;
import ru.ivadimn.a0202storage.R;
import ru.ivadimn.a0202storage.adapters.PersonAdapter;

/**
 * Created by vadim on 20.07.17.
 */

public class PersonListFragment extends Fragment {

    public static final String TAG = "LIST_PERSONS";

    private RecyclerView list;
    private List<Person> persons;
    private PersonAdapter adapter;
    private FloatingActionButton fab;
    private MenuItem itemDelete;

    public static Fragment createFragment() {
        Fragment fragment = new PersonListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        persons = App.getInstance().loadPersons();
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
        inflater.inflate(R.menu.menu_person, menu);
        itemDelete = menu.findItem(R.id.menuitem_delete_id);
    }

    @Override
    public void onStop() {
        super.onStop();
        App.getInstance().savePersons(persons);
    }

    //
    private View.OnClickListener fabClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //start edit activity
        }
    };

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
