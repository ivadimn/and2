package ru.ivadimn.health.fragments;

import android.support.v4.app.Fragment;

import ru.ivadimn.health.R;

/**
 * Created by vadim on 27.08.2017.
 */

public class PagerFragment extends Fragment {

    protected String title;
    public String getTitle(){
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(title);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().setTitle(R.string.app_name);
    }
}
