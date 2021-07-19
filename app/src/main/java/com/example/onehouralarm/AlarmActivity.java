package com.example.onehouralarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmActivity extends AppCompatActivity {

    public static AlarmActivity context;
    public static Ringtone ringtone;

    TextView timeView;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this; requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_alarm);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        timeView = findViewById(R.id.time);
        adView = findViewById(R.id.adView);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(getString(R.string.admob_banner_id));
        adView.loadAd(new AdRequest.Builder().build());
        getSupportActionBar().hide();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        timeView.setText(format.format(new Date(Calendar.getInstance().getTimeInMillis())));
    }

    public static void startAlarm(Context context) {
        Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(context, alarm);
        ringtone.play();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                close();
            }
        }, 1000 * 60 * 5);
    }

    public static void close() {
        try {
            ringtone.stop();
        } catch (Exception e) {}
        try {
            context.finish();
        } catch (Exception e) {}
    }

    @Override
    protected void onDestroy() {
        try {
            ringtone.stop();
        } catch (Exception e) {}
        super.onDestroy();
    }
}
