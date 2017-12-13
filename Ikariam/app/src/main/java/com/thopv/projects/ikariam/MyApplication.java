package com.thopv.projects.ikariam;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;

import com.thopv.projects.ikariam.config.ApplicationComponent;
import com.thopv.projects.ikariam.servers.Server;


/**
 * Created by thopv on 10/11/2017.
 */

public class MyApplication extends Application {
    private ApplicationComponent component;

    public ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, Server.class);
        intent.setAction(Server.START);
        startService(intent);
    }

    @Override
    public void onTerminate() {

        Intent intent = new Intent(this, Server.class);
        intent.setAction(Server.STOP);
        startService(intent);
        super.onTerminate();
    }
}
