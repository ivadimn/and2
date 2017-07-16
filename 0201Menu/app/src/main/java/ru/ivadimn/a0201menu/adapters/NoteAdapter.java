package ru.ivadimn.a0201menu.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
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

    private List<Note> notes;
    private Context context;

    public NoteAdapter(Context context) {
        this.context = context;
    }

    public void updateData(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
            holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes == null ? 0 : notes.size();
    }


    public class NoteHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private CardView cardView;

        public NoteHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.title_id);
            cardView = (CardView) view.findViewById(R.id.cv_id);
            //добавит листенер
        }

        public void bind(Note note) {
            tvTitle.setText(note.getTitle());
        }
    }
}
