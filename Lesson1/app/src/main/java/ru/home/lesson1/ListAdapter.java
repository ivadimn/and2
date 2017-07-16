package ru.home.lesson1;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.Holder> {

    private LayoutInflater mLayoutInflater;

    private ListActionListener mListener;

    private SparseIntArray mSelectedList = new SparseIntArray();

    private boolean mModeEnabled = false;

    public ListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setListener(final ListActionListener listener) {
        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new Holder(mLayoutInflater.inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.bind(position + " " + "position", position);
        if (mModeEnabled && mSelectedList.indexOfKey(position) >= 0) {
            holder.setSelected(true);
        } else {
            holder.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView mText;
        private int mPosition = 0;

        public Holder(final View itemView) {
            super(itemView);
            mText = (TextView) itemView.findViewById(R.id.text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (mModeEnabled) {
                        changeSelected();
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setItems(R.array.actions, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, final int pos) {
                            dialogInterface.dismiss();
                            if (mListener != null) {
                                switch (pos) {
                                    case 0:
                                        mListener.addItem();
                                        break;
                                }
                            }
                        }
                    })
                            .create()
                            .show();*/

                    mListener.showActionMode(callback);
                    changeSelected();
                    return true;
                }
            });

            itemView.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    PopupMenu menu = new PopupMenu(view.getContext(), view);
                    menu.inflate(R.menu.list_menu);
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(final MenuItem item) {
                            if (mListener == null) {
                                return false;
                            }
                            switch (item.getItemId()) {
                                case R.id.add:
                                    mListener.addItem();
                                    return true;
                                default: {
                                    return false;
                                }
                            }
                        }
                    });
                    menu.show();
                }
            });
        }

        private void changeSelected(){
            if (mSelectedList.indexOfKey(mPosition) >= 0) {
                mSelectedList.delete(mPosition);
            } else {
                mSelectedList.put(mPosition, mPosition);
            }
            notifyItemChanged(mPosition);
        }

        void bind(String text, int pos) {
            mText.setText(text);
            mPosition = pos;
        }

        void setSelected(boolean selected) {
            if (selected) {
                mText.setBackgroundColor(ContextCompat
                        .getColor(mText.getContext(), android.R.color.holo_green_dark));
            } else {
                mText.setBackgroundColor(ContextCompat
                        .getColor(mText.getContext(), android.R.color.transparent));
            }
        }
    }

    ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(final ActionMode mode, final Menu menu) {
            mode.getMenuInflater().inflate(R.menu.list_multi_mode, menu);
            mModeEnabled = true;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(final ActionMode mode, final Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(final ActionMode mode, final MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:
                    mListener.deleteItem();
                    return true;
                default: {
                    return false;
                }
            }
        }

        @Override
        public void onDestroyActionMode(final ActionMode mode) {
            mModeEnabled = false;
            mSelectedList.clear();
            notifyDataSetChanged();
        }
    };

    public interface ListActionListener {
        void deleteItem();

        void addItem();

        void showActionMode(ActionMode.Callback callback);
    }
}
