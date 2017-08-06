package ru.ivadimn.a0207alarm;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import ru.ivadimn.a0207alarm.model.Event;

/**
 * Created by vadim on 06.08.2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    private List<Event> events;
    private ItemClickListener listener;

    public EventAdapter(ItemClickListener listener) {
        this.listener = listener;
    }

    public interface ItemClickListener {
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }

    public void updateData(List<Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        holder.bind(position, events.get(position));
    }

    @Override
    public int getItemCount() {
        return events == null ? 0 : events.size();
    }

    public class EventHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView txtTitle;
        private TextView txtMoment;
        private int position;

        public EventHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.cv_id);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onClick(view, position);
                }
            });
            txtTitle = (TextView) view.findViewById(R.id.title_id);
            txtMoment = (TextView) view.findViewById(R.id.moment_id);

        }

        public void bind(int position, Event e) {
            this.position = position;
            txtTitle.setText(e.getTitle());
            txtMoment.setText(e.getMomentString());
        }
    }
}
