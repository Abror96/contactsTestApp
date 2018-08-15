package com.example.abror.contactstestapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ContactAdapter extends FirestoreRecyclerAdapter<Contact, ContactHolder> {

    public ContactAdapter(@NonNull FirestoreRecyclerOptions<Contact> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ContactHolder holder, int position, @NonNull Contact model) {
        holder.tvFullName.setText(model.getFullname());
        holder.tvAddress.setText(model.getAddress());
        holder.tvEmail.setText(model.getEmail());
        holder.tvCellphone.setText(model.getCellphone());
        holder.tvPhone.setText(model.getPhone());
        holder.tvAddedDate.setText(model.getAdded_date());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(view.getContext(), "Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item,
                viewGroup, false);
        return new ContactHolder(v);
    }
}
