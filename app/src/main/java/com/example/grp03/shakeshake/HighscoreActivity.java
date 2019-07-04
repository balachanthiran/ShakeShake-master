package com.example.grp03.shakeshake;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;

public class HighscoreActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private ListView highscoreList;
    private ArrayList<String> array = new ArrayList<>();
    DatabaseReference database;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        database = FirebaseDatabase.getInstance().getReference();
        highscoreList = (ListView) findViewById(R.id.highscoreList);
        sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);


        final Query highscore = database.child("Player").orderByChild("score").limitToLast(10);

        highscore.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot data: dataSnapshot.getChildren()){

                        array.add(data.child("username").getValue() + ": " + data.child("score").getValue());
                    }

                    ArrayAdapter adapter = new ArrayAdapter(HighscoreActivity.this, android.R.layout.simple_list_item_1, array);
                    Collections.reverse(array);
                    highscoreList.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




    }
}
