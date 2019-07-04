package com.example.grp03.shakeshake;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Intent intent;

    //Layout components
    TextView counter;
    TextView timer;
    Button btn1;
    ProgressBar progressBar;

    //score
    int count = 0;

    //ACCELEROMETER
    private Sensor mAccelerometer;
    private SensorManager mSensorManager;
    private SensorEventListener mSensorListener = this;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int shakeSensitivity = 600;

    //Database
    DatabaseReference database;
    DatabaseReference userData;
    SharedPreferences sharedPreferences;

    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);


        mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sound);
        mMediaPlayer.start();

        //Database
        sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        database = FirebaseDatabase.getInstance().getReference();

        // Hide action bar
        getSupportActionBar().hide();

        //Layout components
        progressBar = (ProgressBar) findViewById(R.id.circular_progress_bar);
        counter = (TextView) findViewById(R.id.count);
        timer = (TextView) findViewById(R.id.timer);
        btn1 = (Button) findViewById(R.id.BtnTryAgain);
        btn1.setVisibility(View.GONE);

        setTimer();
    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        float x = e.values[0];
        float y = e.values[1];
        float z = e.values[2];

        //Find current time
        long currentTime = System.currentTimeMillis();

        //Check if more than 100 milliseconds (0,1 sec) has passed since last invokment of this onSensorChanged method
        if ((currentTime - lastUpdate) > 100) {
            long diffTime = (currentTime - lastUpdate);
            lastUpdate = currentTime;

            float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

            if (speed > shakeSensitivity) {

                counter.setText("Shake Count : " + count);
                count = count + 1;
            }

            last_x = x;
            last_y = y;
            last_z = z;
        }

    }

    //Countdown timer
    public void setTimer() {
        //Progressbar animation
        ObjectAnimator anim = ObjectAnimator.ofInt(progressBar, "progress", 0, 60);
        //10 seconds
        anim.setDuration(10000);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();


        //10 seconds
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time left: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timer.setText("FINISHED, STOP!");
                mSensorManager.unregisterListener(mSensorListener, mAccelerometer);
                btn1.setVisibility(View.VISIBLE);

                userData = database.child("Player").child(sharedPreferences.getString("name", ""));

                userData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Get value from DB
                        Long previousScore = dataSnapshot.child("score").getValue(Long.class);

                        if (previousScore == null || count > previousScore) {
                            userData.child("score").setValue(count);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

        }.start();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    //When back pressed, open menu instead of previous activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        mMediaPlayer.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager.unregisterListener(this);
    }


    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.release();
        mSensorManager.unregisterListener(this);
    }

    public void BtnTryAgainonClick(View view) {
        //Reload Activity
        finish();
        startActivity(getIntent());
        //So we don't get an animation when reloading the acitivity
        overridePendingTransition(0, 0);
        getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


    }
}