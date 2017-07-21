package ru.ivadimn.a0202storage.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.ivadimn.a0202storage.model.Person;
import ru.ivadimn.a0202storage.R;

/**
 * Created by vadim on 20.07.17.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private List<Person> persons;
    private SparseIntArray selectedList = new SparseIntArray();
    private boolean selectedMode = false;


    public interface PersonClickListener {
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }

    private PersonClickListener listener;

    public PersonAdapter(PersonClickListener listener) {
        this.listener = listener;
    }

    public void updateData(List<Person> persons) {
        this.persons = persons;
        notifyDataSetChanged();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, final int position) {
        holder.bind(persons.get(position), position);


    }

    @Override
    public int getItemCount() {
        return persons == null ? 0 : persons.size() ;
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo;
        private TextView name;
        private TextView phone;
        private CardView cardView;
        private CheckBox delete;

        private int position;

        public PersonViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_id);
            photo = (ImageView) itemView.findViewById(R.id.photo_id);
            name = (TextView) itemView.findViewById(R.id.name_id);
            phone = (TextView) itemView.findViewById(R.id.phone_id);
            delete = (CheckBox) itemView.findViewById(R.id.delete_id);
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

        public void bind(Person p, int position) {
            this.position = position;
            Bitmap bmp;;
            byte[] pt = p.getPhoto();
            if (pt == null) {
                photo.setImageResource(R.drawable.person_small);
            }
            else {
                bmp = BitmapFactory.decodeByteArray(pt, 0, pt.length);
                photo.setImageBitmap(bmp);
            }
            name.setText(p.getName());
            phone.setText(p.getPhone());
           // delete.setChecked(p.isDelete());
            delete.setVisibility(selectedMode ? View.VISIBLE : View.INVISIBLE);
            if(selectedMode) {
                if (selectedList.indexOfKey(position) > -1)
                    delete.setChecked(true);
                else
                    delete.setChecked(false);
            }
        }

        public CheckBox getDelete() {
            return delete;
        }

        public CardView getCardView() {
            return cardView;
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

    public void setSelectedList(SparseIntArray selectedList) {
        this.selectedList = selectedList;
    }

    public boolean isSelectedMode() {
        return selectedMode;
    }

    public void setSelectedMode(boolean selectedMode) {
        this.selectedMode = selectedMode;
    }


}
