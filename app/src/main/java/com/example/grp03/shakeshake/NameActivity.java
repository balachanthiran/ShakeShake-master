package com.example.grp03.shakeshake;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.jar.Attributes;

public class NameActivity extends AppCompatActivity {

    DatabaseReference database;
    EditText mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        mName = (EditText) findViewById(R.id.nameField);
        database = FirebaseDatabase.getInstance().getReference();
    }

    public void addNameToDatabase(View view) {

        database.child("Player").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = mName.getText().toString();

                database.child("Player").child(name).child("username").setValue(name);

                SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("name", name);
                editor.commit();
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


    }


}



