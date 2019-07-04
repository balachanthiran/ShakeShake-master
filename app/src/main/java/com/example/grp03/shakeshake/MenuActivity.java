package com.example.grp03.shakeshake;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    Intent intent;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Hide action bar
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);

        if(sharedPreferences.getString("name", null) == null) {
            Intent intent = new Intent(this, NameActivity.class);
            startActivity(intent);
        }

        getNotification();
    }

    public void getNotification() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 46);

        Intent intent = new Intent(getApplicationContext(), Notification_reciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void btnPlayOnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void btnHowToPlayOnClick(View view) {
        intent = new Intent(this, HowToPlayActivity.class);
        startActivity(intent);
    }

    public void btnHighscoreOnClick(View view) {
        intent = new Intent(this, HighscoreActivity.class);
        startActivity(intent);


    }
}
