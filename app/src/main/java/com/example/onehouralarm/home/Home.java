package com.example.onehouralarm.home;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onehouralarm.MainActivity;
import com.example.onehouralarm.bean.Alarm;

import java.util.List;

public class Home {

    MainActivity context;
    List<Alarm> alarms;
    Adapter adapter;

    public Home(MainActivity context) {
        this.context = context;
        alarms = Alarm.getAllAlarms(context);
        adapter = new Adapter(alarms);
        context.recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        context.recyclerView.setAdapter(adapter);
    }



}
