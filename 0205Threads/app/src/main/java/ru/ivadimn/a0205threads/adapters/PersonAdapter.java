package ru.ivadimn.a0205threads.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.ivadimn.a0205threads.R;
import ru.ivadimn.a0205threads.model.Person;

/**
 * Created by vadim on 30.07.2017.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder> {

    private List<Person> persons;
    private Context context;

    public PersonAdapter(Context context) {
        this.context = context;
    }

    public void updateData(List<Person> data) {
        persons = data;
        notifyDataSetChanged();
    }

    @Override
    public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.person_item, parent, false);
        return new PersonHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonHolder holder, int position) {
        holder.bind(persons.get(position));
    }

    @Override
    public int getItemCount() {
        return persons == null ? 0 : persons.size();
    }


    public class PersonHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private ImageView imgPhoto;

        public PersonHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.name_id);
            imgPhoto = (ImageView) itemView.findViewById(R.id.photo_id);
        }

        public void bind(Person p) {
            Bitmap bmp;
            txtName.setText(p.getName());
            byte[] pt = p.getPhoto();
            if (pt == null) {
                imgPhoto.setImageResource(R.drawable.person_small);
            }
            else {
                bmp = BitmapFactory.decodeByteArray(pt, 0, pt.length);
                imgPhoto.setImageBitmap(bmp);
            }
        }
    }
}
