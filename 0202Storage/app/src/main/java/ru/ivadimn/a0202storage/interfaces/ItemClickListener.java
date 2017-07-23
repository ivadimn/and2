package ru.ivadimn.a0202storage.interfaces;

import android.view.View;

/**
 * Created by vadim on 23.07.2017.
 */

public interface ItemClickListener {
    public void onClick(View view, int position);
    public void onLongClick(View view, int position);
}
