package ru.ivadimn.health.fragments;

import android.support.v4.app.Fragment;

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
}
