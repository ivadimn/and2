package ru.ivadimn.health.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import ru.ivadimn.health.R;
import ru.ivadimn.health.Utils;
import ru.ivadimn.health.model.Press;

/**
 * Created by vadim on 26.08.2017.
 */

public class PressAdapter extends RecyclerView.Adapter<PressAdapter.PressHolder> {

    private List<Press> list;

    public void updateData(List<Press> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public PressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.press_item, parent, false);
        return new PressHolder(view);
    }

    @Override
    public void onBindViewHolder(PressHolder holder, int position) {
        holder.bind(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class PressHolder extends RecyclerView.ViewHolder {

        private TextView dateView;
        private TextView commentView;
        private CheckBox chDelete;
        private CardView cv;
        private int position;

        public PressHolder(View view) {
            super(view);
            cv = (CardView)view.findViewById(R.id.cv_id);
            dateView = (TextView) view.findViewById(R.id.date_id);
            commentView = (TextView) view.findViewById(R.id.comment_id);
            chDelete = (CheckBox) view.findViewById(R.id.delete_id);
        }

        public void bind(Press p, int position) {
            this.position = position;
            dateView.setText(Utils.stringDate(p.getDate()));
            commentView.setText(p.getComment());
        }
    }
}
