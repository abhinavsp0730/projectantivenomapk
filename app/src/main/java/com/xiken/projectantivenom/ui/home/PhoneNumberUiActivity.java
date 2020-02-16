package com.xiken.projectantivenom.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.xiken.projectantivenom.R;

public class PhoneNumberUiActivity extends AppCompatActivity {
    private static final String TAG = "PhoneNumberUiActivity";
    private EditText editTextName;
    private EditText editTextPhoneNumber;
    private Button submit;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_ui);
        initview();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                String name = editTextName.getText().toString();
                String phoneNumer = editTextPhoneNumber.getText().toString();
                PhoneNumber phoneNumberClass = new PhoneNumber(name, phoneNumer);
                FirebaseDatabase.getInstance().getReference().child("phoneNumber/"+ FirebaseAuth.getInstance().getUid()).push().setValue(phoneNumberClass).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: ");
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: ");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: ");
                    }
                });

            }
        });


    }

    private void initview() {
        editTextName = findViewById(R.id.name);
        editTextPhoneNumber = findViewById(R.id.phoneNumber);
        submit = findViewById(R.id.submit);
        databaseReference = FirebaseDatabase.getInstance().getReference("/users").child("phoneNumber");
    }
}
