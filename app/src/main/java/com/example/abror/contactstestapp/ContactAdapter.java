package com.example.abror.contactstestapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ContactAdapter extends FirestoreRecyclerAdapter<Contact, ContactAdapter.ContactHolder> {


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
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item,
                viewGroup, false);
        return new ContactHolder(v);
    }

    class ContactHolder extends RecyclerView.ViewHolder {
        TextView tvFullName;
        TextView tvAddress;
        TextView tvEmail;
        TextView tvCellphone;
        TextView tvPhone;
        TextView tvAddedDate;


        public ContactHolder(@NonNull View itemView) {
            super(itemView);

            tvFullName = itemView.findViewById(R.id.fullname_textview);
            tvAddress = itemView.findViewById(R.id.address_textview);
            tvEmail = itemView.findViewById(R.id.email_textview);
            tvCellphone = itemView.findViewById(R.id.cellphone_textview);
            tvPhone = itemView.findViewById(R.id.phone_textview);
            tvAddedDate = itemView.findViewById(R.id.added_date_textview);
        }
    }
}
