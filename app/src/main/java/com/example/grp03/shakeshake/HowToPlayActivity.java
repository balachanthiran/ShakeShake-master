package com.example.grp03.shakeshake;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Switch;
import android.widget.TextView;

public class HowToPlayActivity extends AppCompatActivity {

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);
        getSupportActionBar().hide();
    }

    public void btnPlayOnClick(View view) {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
