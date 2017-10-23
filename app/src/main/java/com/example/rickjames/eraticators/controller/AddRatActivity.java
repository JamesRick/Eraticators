package com.example.rickjames.eraticators.controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rickjames.eraticators.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.rickjames.eraticators.R.id.add;
import static com.example.rickjames.eraticators.R.id.inputtedEmail;
import static com.example.rickjames.eraticators.R.id.inputtedPassword;

public class AddRatActivity extends AppCompatActivity {

    private EditText address;
    private EditText borough;
    private EditText city;
    private EditText date;
    private EditText latitude;
    private EditText longitude;
    private EditText name;
    private EditText type;
    private EditText zip;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ratTable = database.getReference().child("RAT_TABLE");


    private Button addButton;
    private Button cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rat);
        addButton = (Button) findViewById(R.id.addRat);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        address = (EditText) findViewById(R.id.inputtedAddress);
        borough = (EditText) findViewById(R.id.inputtedBorough);
        city = (EditText) findViewById(R.id.inputtedCity);
        date = (EditText) findViewById(R.id.inputtedDate);
        latitude = (EditText) findViewById(R.id.inputtedLatitude);
        longitude = (EditText) findViewById(R.id.inputtedLongitude);
        name = (EditText) findViewById(R.id.inputtedName);
        type = (EditText) findViewById(R.id.inputtedType);
        zip = (EditText) findViewById(R.id.inputtedZip);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String addressText = address.getText().toString();
                String boroughText = borough.getText().toString();
                String cityText = city.getText().toString();
                String dateText = date.getText().toString();
                String latitudeText = latitude.getText().toString();
                String longitudeText = longitude.getText().toString();
                String nameText = name.getText().toString();
                String typeText = type.getText().toString();
                String zipText = zip.getText().toString();
                addNewRat(addressText,boroughText,cityText,dateText,latitudeText,longitudeText,nameText,typeText,zipText);
                Intent intent = new Intent(AddRatActivity.this, UserActivity.class);
                finish();
                startActivity(intent);

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRatActivity.this, UserActivity.class);
                finish();
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String TAG = null;
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };



    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    public void addNewRat(String address, String borough, String city, String date, String latitude, String longitude, String name, String type, String zip) {
        DatabaseReference curRat = ratTable.child(name);
        curRat.setValue(name);

        DatabaseReference addressRef = curRat.child("address");
        ratTable.child(name).child("address").setValue(address);


        ratTable.child(name).child("borough").setValue(borough);
        ratTable.child(name).child("city").setValue(city);
        ratTable.child(name).child("date").setValue(date);
        ratTable.child(name).child("latitude").setValue(latitude);
        ratTable.child(name).child("longitude").setValue(longitude);
        ratTable.child(name).child("name").setValue(name);
        ratTable.child(name).child("type").setValue(type);
        ratTable.child(name).child("zip").setValue(zip);
    }
}
