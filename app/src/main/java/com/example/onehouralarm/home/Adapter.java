package com.example.onehouralarm.home;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onehouralarm.R;
import com.example.onehouralarm.bean.Alarm;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<Alarm> alarms;

    public Adapter(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Alarm alarm = alarms.get(position);
        holder.alarm = alarm;
        String description = "Change setting for alarm at " + alarm.getAlarmTitle();
        holder.titleView.setText(alarm.getAlarmTitle());
        holder.descriptionView.setText(description);
        holder.labeledSwitch.setOn(alarm.isActivated());
        if (alarm.isActivated()) {
            holder.linearLayout.setBackgroundColor(Color.BLUE);
        } else {
            holder.linearLayout.setBackgroundColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView titleView;
        TextView descriptionView;
        LabeledSwitch labeledSwitch;
        Alarm alarm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.alarm);
            titleView = itemView.findViewById(R.id.title);
            descriptionView = itemView.findViewById(R.id.description);
            labeledSwitch = itemView.findViewById(R.id.labeledSwitch);
            labeledSwitch.setOnToggledListener(new OnToggledListener() {
                @Override
                public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                    Log.e("Label", alarm.getAlarmTitle() + " - " + labeledSwitch.isOn());
                    alarm.setActivated(isOn);
                    notifyDataSetChanged();
                }
            });
        }
    }

}
