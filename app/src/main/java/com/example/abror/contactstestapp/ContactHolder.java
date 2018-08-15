package com.example.abror.contactstestapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tvFullName;
    TextView tvAddress;
    TextView tvEmail;
    TextView tvCellphone;
    TextView tvPhone;
    TextView tvAddedDate;

    private ItemClickListener itemClick;


    public ContactHolder(@NonNull View itemView) {
        super(itemView);

        tvFullName = itemView.findViewById(R.id.fullname_textview);
        tvAddress = itemView.findViewById(R.id.address_textview);
        tvEmail = itemView.findViewById(R.id.email_textview);
        tvCellphone = itemView.findViewById(R.id.cellphone_textview);
        tvPhone = itemView.findViewById(R.id.phone_textview);
        tvAddedDate = itemView.findViewById(R.id.added_date_textview);

        itemView.setOnClickListener(this);


    }

    public void setItemClickListener(ItemClickListener itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public void onClick(View v) {
        itemClick.onClick(v, getAdapterPosition());
    }
}
