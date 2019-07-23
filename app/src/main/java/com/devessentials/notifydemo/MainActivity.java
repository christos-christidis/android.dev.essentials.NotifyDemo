package com.devessentials.notifydemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "com.devessentials.notifydemo.news";

    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    // SOS: API must be > 26 for these calls! Which means I must test that on a phone w Android >= 8.0
    private void createNotificationChannel() {
        String name = "NotifyDemo News";
        int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

        // SOS: these settings apply to all notifications sent through that channel
        channel.setDescription("Example News Channel");
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        mNotificationManager.createNotificationChannel(channel);
    }

    public void sendNotification(View view) {
        Intent resultIntent = new Intent(this, ResultActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // SOS: an action adds a button to the notification. Clicking this button will launch the app.
        // We'll also launch the app if user clicks anywhere on the not/ion (w steContentIntent below)
        Icon icon = Icon.createWithResource(this, android.R.drawable.ic_dialog_info);
        Notification.Action action = new Notification.Action.Builder(icon, "Open", pendingIntent).build();

        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("Example Notification")
                .setContentText("This is an example notification.")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setChannelId(CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setActions(action)
                .build();

        int notificationID = 101;
        mNotificationManager.notify(notificationID, notification);

        // SOS: also check out end of chapter for how we can bundle many notifications into one!
    }
}
