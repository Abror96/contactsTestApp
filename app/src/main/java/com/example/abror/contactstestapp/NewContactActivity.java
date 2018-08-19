package com.example.abror.contactstestapp;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewContactActivity extends AppCompatActivity {

    EditText etFullName;
    EditText etAddress;
    EditText etEmail;
    EditText etCellphone;
    EditText etPhone;

    private View contextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        contextView = findViewById(R.id.new_contact_view);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add new contact");

        etFullName = findViewById(R.id.fullname_editText_add);
        etAddress = findViewById(R.id.address_editText_add);
        etEmail = findViewById(R.id.email_edittext_add);
        etCellphone = findViewById(R.id.cellphone_edittext_add);
        etPhone = findViewById(R.id.phone_edittext_add);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_contact_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_contact:
                // saving new contact
                saveContact();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void saveContact() {
        String fullnameStr = etFullName.getText().toString().trim();
        String addressStr = etAddress.getText().toString().trim();
        String emailStr = etEmail.getText().toString().trim();
        String cellphoneStr = etCellphone.getText().toString().trim();
        String phoneStr = etPhone.getText().toString().trim();

        if (fullnameStr.trim().isEmpty() || emailStr.trim().isEmpty() || cellphoneStr.trim().isEmpty()) {
            Snackbar.make(contextView, "Please add Fullname, email and cellphone", Snackbar.LENGTH_LONG).show();
            return;
        }

        CollectionReference contactsRef = FirebaseFirestore.getInstance()
                .collection("Contacts");

        //getting current date of creating contact
        Date date = new Date();
        Date newDate = new Date(date.getTime());
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        String stringdate = dt.format(newDate);

        contactsRef.add(new Contact(fullnameStr, addressStr, emailStr, cellphoneStr, phoneStr, stringdate));
        Snackbar.make(contextView, "Contact added", Snackbar.LENGTH_LONG).show();
        finish();
    }
}
