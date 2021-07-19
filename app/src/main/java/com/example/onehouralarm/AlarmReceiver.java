package com.example.onehouralarm;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.onehouralarm.bean.Alarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String s = format.format(new Date(Calendar.getInstance().getTimeInMillis()));
        Log.i("Alarm", "Received " + s);
        AlarmActivity.close();
        if (!isAlarm(context)) { return; }
        Intent intentOpen = new Intent(context, AlarmActivity.class);
        intentOpen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentOpen);
        displayNotification(context);
        AlarmActivity.startAlarm(context);
    }

    public boolean isAlarm(Context context) {
        SimpleDateFormat format = new SimpleDateFormat("HH");
        int x = Integer.parseInt(format.format(new Date(Calendar.getInstance().getTimeInMillis())));
        Alarm alarm = new Alarm(x, context.getSharedPreferences("alarms" + x, Context.MODE_PRIVATE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, MainActivity.getCalendarMillis(), pendingIntent);
        }
        return alarm.isActivated();
    }

    public void displayNotification(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Hourly Alarm";
            String description = "Plays Hourly Alarm as defined in Settings";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Hourly Alarm", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Intent intent = new Intent(context, AlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Hourly Alarm")
                .setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true)
                .setContentTitle("Hourly Alarm - " + format.format(new Date(Calendar.getInstance().getTimeInMillis())))
                .setContentText("Click to Open and Stop the Alarm").setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_MAX).setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(100, builder.build());

    }

}
