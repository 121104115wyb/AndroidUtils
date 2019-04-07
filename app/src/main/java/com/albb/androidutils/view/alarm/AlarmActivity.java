package com.albb.androidutils.view.alarm;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.albb.androidutils.R;
import com.albb.androidutils.view.activity.BasePerMissionAcvivity;

public class AlarmActivity extends BasePerMissionAcvivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);
    }



}
