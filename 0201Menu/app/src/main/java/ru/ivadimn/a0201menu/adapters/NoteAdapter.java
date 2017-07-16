package ru.ivadimn.a0201menu.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.ivadimn.a0201menu.R;
import ru.ivadimn.a0201menu.model.Note;

/**
 * Created by vadim on 16.07.17.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    public static final String TAG = "NOTE_ADAPTER";
    private List<Note> notes;
    private Context context;
    private OnRVClickListener listener;

    public interface OnRVClickListener {
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }

    public NoteAdapter(Context context, OnRVClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void updateData(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
        Log.d(TAG, "ViewHolder created");
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        holder.bind(position);
        Log.d(TAG, "ViewHolder binded");
    }

    @Override
    public int getItemCount() {
        return notes == null ? 0 : notes.size();
    }


    public class NoteHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private CardView cardView;
        private int position = 0;

        public NoteHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.title_id);
            cardView = (CardView) view.findViewById(R.id.cv_id);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onClick(view, position);
                }
            });
            //добавит листенер
        }

        public void bind(int position) {
            this.position = position;
            tvTitle.setText(notes.get(position).getTitle());
        }
    }
}
