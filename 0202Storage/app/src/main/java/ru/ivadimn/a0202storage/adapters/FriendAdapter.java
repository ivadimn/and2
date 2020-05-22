package ru.ivadimn.a0202storage.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import ru.ivadimn.a0202storage.R;
import ru.ivadimn.a0202storage.interfaces.ItemClickListener;
import ru.ivadimn.a0202storage.model.Friend;
import ru.ivadimn.a0202storage.model.Person;

/**
 * Created by vadim on 23.07.2017.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendHolder> {

    private List<Friend> friends;
    private boolean selectedMode = false;
    private SparseIntArray selectedList = new SparseIntArray();
    private ItemClickListener listener;

    public FriendAdapter(ItemClickListener listener) {
        this.listener = listener;
    }
    public void updateData(List<Friend> friends) {
        this.friends = friends;
        notifyDataSetChanged();
    }

    @Override
    public FriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new FriendHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return friends == null ? 0 : friends.size();
    }


    public class FriendHolder extends RecyclerView.ViewHolder {

        private TextView friend;
        private CheckBox delete;
        private CardView cardView;
        private int position;

        public FriendHolder(View view) {
            super(view);
            friend = (TextView) view.findViewById(R.id.friend_id);
            delete = (CheckBox) view.findViewById(R.id.deletefriend_id);
            cardView = (CardView) view.findViewById(R.id.cvfriend_id);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedMode)
                        setSelected();
                    else if (listener != null)
                        listener.onClick(view, position);
                }
            });
            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (!selectedMode) {
                        selectedMode = true;
                        setSelected();
                        if (listener != null)
                            listener.onLongClick(view, position);
                    }
                    return true;
                }
            });
        }

        public void bind(int position) {
            this.position = position;
            friend.setText(friends.get(position).getName());
            delete.setVisibility(selectedMode ? View.VISIBLE : View.INVISIBLE);
            if(selectedMode) {
                if (selectedList.indexOfKey(position) > -1)
                    delete.setChecked(true);
                else
                    delete.setChecked(false);
            }
        }

        private void setSelected() {
            if (selectedList.indexOfKey(position) > -1)
                selectedList.delete(position);
            else
                selectedList.put(position, position);
            notifyItemChanged(position);
        }
    }

    public SparseIntArray getSelectedList() {
        return selectedList;
    }

    public boolean isSelectedMode() {
        return selectedMode;
    }

    public void setSelectedMode(boolean selectedMode) {
        this.selectedMode = selectedMode;
    }
}
