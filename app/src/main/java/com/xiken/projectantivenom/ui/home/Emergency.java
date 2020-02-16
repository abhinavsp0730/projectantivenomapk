package com.xiken.projectantivenom.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xiken.projectantivenom.MapsActivity;
import com.xiken.projectantivenom.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Emergency extends AppCompatActivity {
    ImageView callAmbulance;
    Button mapButton;
    Button sendMessage;
    String longitude="83.9048564";
    String latitude ="21.4974157";
    String pincode="768018";
    String addresLine="Burla";
    private static final String TAG = "Emergency;";
    String abhinav = "9304838572";
    String gouri = "8249766641";
    String phone="";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 18 ){
            if (grantResults.length >0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (checkCallPermission()){
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:108"));
                        startActivity(intent);
                    }

                }
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        callAmbulance =findViewById(R.id.callAmbulance);
        mapButton = findViewById(R.id.go_to_the_map);
        sendMessage = findViewById(R.id.send_message);
        Intent intent = getIntent();
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");
        pincode = intent.getStringExtra("pincode");
        addresLine = intent.getStringExtra("addressLine");
        String uid = FirebaseAuth.getInstance().getUid();
        Log.d(TAG, "onCreate: "+ uid);
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("/phoneNumber");
//        firebaseDatabase.push().setValue(new PhoneNumber("8249766641","gour")).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Log.d(TAG, "onComplete: ");
//            }
//        }).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Log.d(TAG, "onSuccess: ");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "onFailure: "+e.getMessage());
//            }
//        });
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    PhoneNumber phoneNumber = snapshot.getValue(PhoneNumber.class);
                    phone = phoneNumber.getPhoneNumber();
                    Log.d(TAG, "onDataChange: "+phoneNumber.getPhoneNumber());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        final DateFormat dateFormat = new SimpleDateFormat("MMM dd,yyy HH:MM");
//        final Date date = new Date(System.currentTimeMillis());
//        dateFormat.format(date);
//        Log.d(TAG, "onClick:2 "+ dateFormat.format(date));
//        Log.d(TAG, "onCreate: "+latitude+ " ,"+longitude +","+ pincode +","+ addresLine);
        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage("8249766641", null, "help", null, null);
//        final Date date = new Date(System.currentTimeMillis());
//        final DateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm");
//        dateFormat.format(date);
        Log.d(TAG, "onCreate: phone"+phone);

        sendMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                SmsManager smsManager = SmsManager.getDefault();
                Log.d(TAG, "onClick: "+longitude);
                Log.d(TAG, "onClick: "+latitude);
                if (pincode == null){
                    smsManager.sendTextMessage("8249766641", null, "Help me Address is unknown", null, null);
                    smsManager.sendTextMessage(abhinav, null, "Help me Address is unknown", null, null);
                }
                smsManager.sendTextMessage("8249766641", null, "Help me", null, null);
                smsManager.sendTextMessage(abhinav, null, "Help me", null, null);
                smsManager.sendTextMessage("8249766641", "", "Help me  \nlatitude =" + latitude + "\n" + "longitude" + longitude + "\n" + "pincode = " + pincode + "\naddressLine = " + addresLine + "  " + "view in map " + "http://maps.google.com/maps?daddr=" + latitude + "," + longitude+" ", null, null);
                smsManager.sendTextMessage("6205430801", "", "Help me  \nlatitude =" + latitude + "\n" + "longitude" + longitude + "\n" + "pincode = " + pincode + "\naddressLine = " + addresLine + "  " + "view in map " + "http://maps.google.com/maps?daddr=" + latitude + "," + longitude+" ", null, null);

//                smsManager.sendTextMessage("8249766641", null, "help", null, null);

            }
        });
        callAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCallPermission()){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:108"));
                    startActivity(intent);
                }else {
                    checkCallPermission();
                }
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Emergency.this, MapsActivity.class);
                startActivity(intent);

            }
        });


    }
    public boolean checkCallPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(Emergency.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Emergency.this, new String[]{Manifest.permission.CALL_PHONE}, 18);
                return false;

            } else {
                return true;
            }

        }
        return true;

    }
}
