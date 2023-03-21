package com.incorps.moviecatalogue3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {
    private final int ID_REPEATING = 100;
    Button okSetting;
    Switch switchDaily;
    Switch switchReminder;
    AlarmReceiver alarmReceiver;
    DailyReminderReceiver dailyReminderReceiver;
    private int jobId = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        okSetting = findViewById(R.id.ok_setting);
        switchDaily = findViewById(R.id.switch_daily_reminder);
        switchReminder = findViewById(R.id.switch_release_reminder);

        okSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchDaily.isChecked()) {
                    Log.d("Apakah Daily Terchecked","Iya");
                    alarmReceiver = new AlarmReceiver();
                    if (!alarmReceiver.isStatus()) {
                        alarmReceiver.setRepeatingAlarm(SettingActivity.this);
                    }
                } else {
                    alarmReceiver.setOffAlarm(SettingActivity.this);
                }

                if (switchReminder.isChecked()) {
                    Log.d("Apakah Terchecked","Iya");
                    dailyReminderReceiver = new DailyReminderReceiver();
                    if (!dailyReminderReceiver.isStatus()){
                        dailyReminderReceiver.setRepeatingAlarm(SettingActivity.this);
                    }
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        startJob();
//                    }
                } else {
                    dailyReminderReceiver.setOffAlarm(SettingActivity.this);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        cancelJob();
//                    }
                }
            }
        });
//
//        AlarmReceiver alarmReceiver = new AlarmReceiver();
    }

//    public void startJob(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (isJobRunning(this)) {
//                Toast.makeText(this, "Job Service is already scheduled", Toast.LENGTH_SHORT).show();
//                return;
//            }
//        }
//        ComponentName mServiceComponent = new ComponentName(this, GetReleaseMovieService.class);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            JobInfo.Builder builder = new JobInfo.Builder(jobId, mServiceComponent);
//            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
//            builder.setRequiresDeviceIdle(false);
//            builder.setRequiresCharging(false);
//            // 1000 ms = 1 detik
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                builder.setPeriodic(24 * 60 * 60 * 1000L);
//            } else {
//                builder.setPeriodic(24 * 60 * 60 * 1000L);
//            }
//            JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//            jobScheduler.schedule(builder.build());
//            Toast.makeText(this, "Job Service started", Toast.LENGTH_SHORT).show();
//        }
//    }
//    public void cancelJob(){
//        JobScheduler tm = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//            tm.cancel(jobId);
//            Toast.makeText(this, "Job Service canceled", Toast.LENGTH_SHORT).show();
//            finish();
//        }
//    }
//
//    public boolean isJobRunning(Context context) {
//        boolean isScheduled = false;
//        JobScheduler scheduler = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
//            if (scheduler != null) {
//                for (JobInfo jobInfo : scheduler.getAllPendingJobs()) {
//                    if (jobInfo.getId() == jobId) {
//                        isScheduled = true;
//                        break;
//                    }
//                }
//            }
//        }
//        return isScheduled;
//    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public void startJob(){
//        ComponentName mServiceComponent = new ComponentName(this, GetNewReleaseMovieService.class);
//        JobInfo.Builder builder = new JobInfo.Builder(jobId, mServiceComponent);
//        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
//        builder.setRequiresDeviceIdle(false);
//        builder.setRequiresCharging(false);
//        // 1000 ms = 1 detik
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            builder.setPeriodic(10000); //15 menit
//        } else {
//            builder.setPeriodic(5000); //3 menit
//        }
//        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        jobScheduler.schedule(builder.build());
//        Toast.makeText(this, "Job Service started", Toast.LENGTH_SHORT).show();
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public void cancelJob(){
//        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        tm.cancel(jobId);
//        Toast.makeText(this, "Job Service canceled", Toast.LENGTH_SHORT).show();
//        finish();
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public boolean isJobRunning(Context context) {
//        boolean isScheduled = false;
//        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        if (scheduler != null) {
//            for (JobInfo jobInfo : scheduler.getAllPendingJobs()) {
//                if (jobInfo.getId() == jobId) {
//                    isScheduled = true;
//                    break;
//                }
//            }
//        }
//        return isScheduled;
//    }
}
