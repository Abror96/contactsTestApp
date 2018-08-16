package com.example.abror.contactstestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void saveContact() {
        String fullnameStr = etFullName.getText().toString();
        String addressStr = etAddress.getText().toString();
        String emailStr = etEmail.getText().toString();
        String cellphoneStr = etCellphone.getText().toString();
        String phoneStr = etPhone.getText().toString();

        if (fullnameStr.trim().isEmpty() || emailStr.trim().isEmpty() || cellphoneStr.trim().isEmpty()) {
            Toast.makeText(this, "Please add Fullname, email and cellphone", Toast.LENGTH_LONG).show();
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
        Toast.makeText(this, "Contact added", Toast.LENGTH_LONG).show();
        finish();
    }
}
