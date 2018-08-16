package com.example.abror.contactstestapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Update contact");

        final String document_id = getIntent().getStringExtra("doc_id");

        updatedData = new HashMap<>();

        etFullName = findViewById(R.id.fullname_editText_update);
        etAddress = findViewById(R.id.address_editText_update);
        etEmail = findViewById(R.id.email_edittext_update);
        etCellphone = findViewById(R.id.cellphone_edittext_update);
        etPhone = findViewById(R.id.phone_edittext_update);

        updateButton = findViewById(R.id.update_data_button);

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

                updatedData.put("fullname", etFullName.getText().toString());
                updatedData.put("address", etAddress.getText().toString());
                updatedData.put("email", etEmail.getText().toString());
                updatedData.put("cellphone", etCellphone.getText().toString());
                updatedData.put("phone", etPhone.getText().toString());

                contactsRef.document(document_id).update(updatedData);
            }
        });
    }
}
