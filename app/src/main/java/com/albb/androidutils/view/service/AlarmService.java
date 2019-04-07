package com.albb.androidutils.view.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AlarmService extends Service {


    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("AlarmService", "run: -----"+"startService");
                save(System.currentTimeMillis()+"");
                stopSelf();
            }
        }).start();

        startAlarm();
        return super.onStartCommand(intent, flags, startId);


    }



    void startAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long targetTime = SystemClock.elapsedRealtime()+10*1000;
        Intent intent = new Intent(this,AlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this,0,intent,0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,targetTime,pendingIntent);
        //android 4.4开始系统的定时任务变得不再准确，因为系统为了保护电池，会进入Doze模式，如果想要准确，需要按照下面来写
        if (Build.VERSION.SDK_INT>=19){
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,targetTime,pendingIntent);
        }
    }

    public void save(String s) {
        String data = s;
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            //设置文件名称，以及存储方式
            out = openFileOutput("alarmdata", Context.MODE_PRIVATE);
            //创建一个OutputStreamWriter对象，传入BufferedWriter的构造器中
            writer = new BufferedWriter(new OutputStreamWriter(out));
            //向文件中写入数据
            writer.write(data);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("AlarmService", "onDestroy: -----"+"onDestroy");
    }
}
