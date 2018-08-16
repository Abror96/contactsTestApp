package com.example.abror.contactstestapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class ContactAdapter extends FirestoreRecyclerAdapter<Contact, ContactHolder> {

    ArrayList<Contact> arrayList = new ArrayList<>();

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
                DocumentSnapshot snapshot = getSnapshots().getSnapshot(position);
                Intent intent = new Intent(view.getContext() , UpdateDataActivity.class);
                intent.putExtra("doc_id", snapshot.getId());
                view.getContext().startActivity(intent);
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

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public void setFilter(ArrayList<Contact> newlist) {
        newlist = new ArrayList<>();

    }
}
