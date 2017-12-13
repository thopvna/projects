package com.thopv.projects.ikariam.config;

import android.app.NotificationManager;
import android.content.Context;

import dagger.Component;

/**
 * Created by thopv on 11/20/2017.
 */
@ApplicationScope
@Component(modules = {ContextModule.class})
public interface ApplicationComponent {
    Context getContext();
    NotificationManager getNotificationService();
}
