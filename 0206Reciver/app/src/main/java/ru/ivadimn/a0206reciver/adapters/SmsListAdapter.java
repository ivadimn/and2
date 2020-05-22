package ru.ivadimn.a0206reciver.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.ivadimn.a0206reciver.R;
import ru.ivadimn.a0206reciver.model.TextMessage;

/**
 * Created by vadim on 03.08.2017.
 */

public class SmsListAdapter extends RecyclerView.Adapter<SmsListAdapter.SmsHolder> {

    private static final String TAG = "......SMS_LIST_ADAPTER";
    private List<TextMessage> messages;

    public void updateData(List<TextMessage> messages) {
        this.messages = messages;
        notifyDataSetChanged();
        Log.d(TAG, "Dataset changed");
    }


    @Override
    public SmsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new SmsHolder(view);
    }

    @Override
    public void onBindViewHolder(SmsHolder holder, int position) {
        holder.bind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages == null ? 0 : messages.size();
    }


    public class SmsHolder extends RecyclerView.ViewHolder {
        private TextView txtAddress;
        private TextView txtBody;

        public SmsHolder(View itemView) {
            super(itemView);
            txtAddress = (TextView) itemView.findViewById(R.id.address_id);
            txtBody = (TextView) itemView.findViewById(R.id.body_id);
        }

        public void bind(TextMessage msg) {
            txtAddress.setText(msg.getAddress());
            txtBody.setText(msg.getBody());
        }
    }
}
