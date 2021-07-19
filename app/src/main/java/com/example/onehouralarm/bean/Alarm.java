package com.example.onehouralarm.bean;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class Alarm {

    private int time;
    private SharedPreferences preferences;

    public Alarm(int time, SharedPreferences preferences) {
        this.time = time;
        this.preferences = preferences;
    }

    public int getTime() {
        return time;
    }

    public String getAlarmTone() {
        return preferences.getString("tone", null);
    }

    public void setAlarmTone(String alarmTone) {
        preferences.edit().putString("tone", alarmTone).commit();
    }

    public boolean isActivated() {
        return preferences.getBoolean("activated",false);
    }

    public void setActivated(boolean activated) {
        preferences.edit().putBoolean("activated", activated).commit();
    }

    public static List<Alarm> getAllAlarms(Context context) {
        String temp = "alarms";
        List<Alarm> alarms = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            alarms.add(new Alarm(i, context.getSharedPreferences("alarms" + i, Context.MODE_PRIVATE)));
        }
        return alarms;
    }

    public String getAlarmTitle() {
        String title = null;
        if (getTime() == -1) {
            title = "All Alarm";
        } else if (getTime() < 13) {
            title = (getTime() == 0 ? 12 : getTime()) + " AM";
        } else {
            int time = getTime() - 12;
            title = (time == 0 ? 12 : time) + " PM";
        }
        return title;
    }
}
