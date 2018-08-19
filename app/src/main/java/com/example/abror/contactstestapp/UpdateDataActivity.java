package com.example.abror.contactstestapp;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateDataActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etAddress;
    private EditText etEmail;
    private EditText etCellphone;
    private EditText etPhone;
    private Button updateButton;

    private Map<String, Object> updatedData;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference contactsRef = db.collection("Contacts");

    private View contextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Update contact");

        contextView = findViewById(R.id.update_contact_view);

        // getting extra string from contactAdapter class
        final String document_id = getIntent().getStringExtra("doc_id");

        updatedData = new HashMap<>();
        initViews();

        contactsRef.document(document_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                etFullName.setText(task.getResult().getString("fullname"));
                etAddress.setText(task.getResult().getString("address"));
                etEmail.setText(task.getResult().getString("email"));
                etCellphone.setText(task.getResult().getString("cellphone"));
                etPhone.setText(task.getResult().getString("phone"));
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etFullName.getText().toString().trim().isEmpty() || etAddress.getText().toString().isEmpty()) {
                    Snackbar.make(contextView, "Please add fullname and address", Snackbar.LENGTH_LONG).show();
                    return;
                }
                updatedData.put("fullname", etFullName.getText().toString().trim());
                updatedData.put("address", etAddress.getText().toString().trim());
                updatedData.put("email", etEmail.getText().toString().trim());
                updatedData.put("cellphone", etCellphone.getText().toString().trim());
                updatedData.put("phone", etPhone.getText().toString().trim());

                contactsRef.document(document_id).update(updatedData);
                Snackbar.make(contextView, "Contact was updated", Snackbar.LENGTH_LONG).show();

                finish();
            }
        });
    }

    private void initViews() {
        etFullName = findViewById(R.id.fullname_editText_update);
        etAddress = findViewById(R.id.address_editText_update);
        etEmail = findViewById(R.id.email_edittext_update);
        etCellphone = findViewById(R.id.cellphone_edittext_update);
        etPhone = findViewById(R.id.phone_edittext_update);
        updateButton = findViewById(R.id.update_data_button);
    }
}
