package com.example.notificationmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NotificationCompat.Builder builder;
    private NotificationManager notiManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ConstraintLayout constraintLayout = findViewById(R.id.activity_main__constraint_layout__root);
        constraintLayout.setOnClickListener(this);

        this.builder = new NotificationCompat.Builder(this, "channelId");
        this.notiManager = (NotificationManager) this.getSystemService(Service.NOTIFICATION_SERVICE);

        builder.setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("My Notification")
                .setContentText("Hello Monica! This is a simple text for my notification")
                .setAutoCancel(true)
                .setContentIntent(getPendingIntentWithRequestCode(23));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel =
                    new NotificationChannel("NOTIFICATION_CHANNEL_ID",
                            "NOTIFICATION_CHANNEL_NAME",
                            importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            builder.setChannelId("NOTIFICATION_CHANNEL_ID");
            notiManager.createNotificationChannel(notificationChannel);
        }

    }

    private PendingIntent getPendingIntentWithRequestCode(int requestCode) {
        Intent notificationIntent = new Intent(this, SecondActivity.class);
        return PendingIntent.getActivity(this, requestCode, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main__constraint_layout__root:
                Log.i("MainActivity", "background clicked");
                this.notiManager.notify(1, this.builder.build());
        }
    }
}
