package com.example.abror.contactstestapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactHolder> {

    private MainActivity mainActivity;
    private ArrayList<Contact> contactArrayList;

    public ContactAdapter(MainActivity mainActivity, ArrayList<Contact> contactArrayList) {
        this.mainActivity = mainActivity;
        this.contactArrayList = contactArrayList;
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.contact_item,
                viewGroup, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        holder.tvFullName.setText(contactArrayList.get(position).getFullname());
        holder.tvAddress.setText(contactArrayList.get(position).getAddress());
        holder.tvEmail.setText(contactArrayList.get(position).getEmail());
        holder.tvCellphone.setText(contactArrayList.get(position).getCellphone());
        holder.tvPhone.setText(contactArrayList.get(position).getPhone());
        holder.tvAddedDate.setText(contactArrayList.get(position).getAdded_date());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(view.getContext() , UpdateDataActivity.class);
                intent.putExtra("doc_id", contactArrayList.get(position).getContactId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactArrayList.size();
    }


    public void setFilter(ArrayList<Contact> newlist) {
        contactArrayList = new ArrayList<>();
        contactArrayList.addAll(newlist);
        notifyDataSetChanged();
    }
}
