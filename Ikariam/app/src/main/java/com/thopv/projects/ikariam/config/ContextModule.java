package com.thopv.projects.ikariam.config;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by thopv on 11/20/2017.
 */
@Module
public class ContextModule {
    private Context context;
    public ContextModule(Context context){
        this.context = context;
    }
    @Provides
    @ApplicationScope
    Context context(){
        return context;
    }
    @Provides
    @ApplicationScope
    NotificationManager notificationManager(){
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
