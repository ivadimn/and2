package ru.ivadimn.a0202storage.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.ivadimn.a0202storage.App;
import ru.ivadimn.a0202storage.activities.MainActivity;
import ru.ivadimn.a0202storage.activities.PersonActivity;
import ru.ivadimn.a0202storage.interfaces.IDataStore;
import ru.ivadimn.a0202storage.model.Person;
import ru.ivadimn.a0202storage.R;
import ru.ivadimn.a0202storage.adapters.PersonAdapter;
import ru.ivadimn.a0202storage.storage.StorageFactory;

import static android.app.Activity.RESULT_OK;

/**
 * Created by vadim on 20.07.17.
 */

public class PersonListFragment extends Fragment implements MainActivity.OnBackPressedListener{

    public static final String TAG = "LIST_PERSONS";
    public static final String TYPE_STORAGE = "TYPE_STOPRAGE";
    public static final int ADD_PERSON = 1;
    public static final int EDIT_PERSON = 2;

    private RecyclerView list;
    private List<Person> persons;
    private PersonAdapter adapter;
    private FloatingActionButton fab;
    private MenuItem itemDelete;
    private IDataStore store;

    public static Fragment createFragment(int typeStorage) {
        Fragment fragment = new PersonListFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_STORAGE, typeStorage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        StorageFactory.initStorage(getContext(), args.getInt(TYPE_STORAGE, StorageFactory.DATABASE_STORAGE));
        store = StorageFactory.getStorage();
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
        itemDelete.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuitem_delete_id:
                deletePersons(adapter.getSelectedList());
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            Intent intent = PersonActivity.createIntent(getContext(), -1, -1);
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
            Intent intent = PersonActivity.createIntent(getContext(), position, persons.get(position).get_id());
            startActivityForResult(intent, EDIT_PERSON);
        }

        @Override
        public void onLongClick(View view, int position) {
            startSelectedMode();
        }
    };

    private void deletePersons(SparseIntArray sparse) {
        List<Person> tmp = new ArrayList<>(persons);
        for (int i = 0; i < sparse.size(); i++) {
            store.delete(tmp.get(sparse.valueAt(i)));
        }
        sparse.clear();
    }

    private void startSelectedMode() {
        itemDelete.setVisible(true);
        fab.setVisibility(View.INVISIBLE);
        adapter.notifyDataSetChanged();

    }

    private void finishSelectedMode() {
       adapter.setSelectedMode(false);
       adapter.getSelectedList().clear();
       itemDelete.setVisible(false);
       fab.setVisibility(View.VISIBLE);
       adapter.notifyDataSetChanged();
    }

    @Override
    public boolean isSelectedMode() {
        return adapter.isSelectedMode();
    }

    @Override
    public void backToNormalMode() {
        finishSelectedMode();
    }
}
