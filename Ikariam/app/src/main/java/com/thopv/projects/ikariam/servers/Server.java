package com.thopv.projects.ikariam.servers;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.config.UseCaseComponent;
import com.thopv.projects.ikariam.UseCaseHandler;
import com.thopv.projects.ikariam.fight.domain.usecases.Fight;
import com.thopv.projects.ikariam.fight.domain.usecases.PopulateUnits;
import com.thopv.projects.ikariam.home.domain.usecases.ConfirmCollosusTroops;
import com.thopv.projects.ikariam.home.domain.usecases.ConfirmMovingTroops;

import junit.framework.Assert;

import java.util.Observable;
import java.util.Observer;

public class Server extends Service implements Observer{
    private Fight fight;
    private UseCaseComponent component;
    private ConfirmMovingTroops confirmMovingTroops;
    private ConfirmCollosusTroops confirmCollosusTroops;
    private PopulateUnits populateUnits;

    public Server() {

    }
    public final static String START = "SERVER_START";
    public final static String STOP = "SERVER_STOP";
    public final int NOTIFYCATION_ID = 21295;
    public final long confirmDistance = 2000L;
    private static Handler jobHandler = new Handler();
    private Runnable fightRun;
    private Runnable confirmRun;

    @Override
    public void onCreate() {
        super.onCreate();
        //init
        component = UseCaseComponent.getInstance(this);
        populateUnits = component.getPopulateUnits();
        confirmMovingTroops = component.getConfirmMovingTroops();
        confirmCollosusTroops = component.getConfirmCollosusTroops();
        fight = component.getFight();
        //confirm movngtroops and collosus
        setupForConfirm();
        //Fight...
        setupForFight();
    }

    private void setupForConfirm() {
        confirmRun = () -> {
            UseCaseHandler.execute(confirmMovingTroops, new ConfirmMovingTroops.RequestValues(), response -> {
                Log.e(getClass().getSimpleName(), "Movingtroops confirm- status: " + response.isSuccess());
            });

            UseCaseHandler.execute(confirmCollosusTroops, new ConfirmCollosusTroops.RequestValues(), response -> {
                Log.e(getClass().getSimpleName(), "Collosused troops confirm- status: " + response.isSuccess());
            });
            jobHandler.postDelayed(confirmRun, confirmDistance);
        };
    }

    private void setupForFight() {
        FightTimeDistanceManager.getInstance(this).addObserver(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(getClass().getName(), "On Start Job.");
        Assert.assertNotNull(intent);
        Assert.assertNotNull(intent.getAction());
        if(intent.getAction().equalsIgnoreCase(START)) {
            jobHandler.post(confirmRun);
            jobHandler.post(fightRun);
            postNotification();
        }
        else if(intent.getAction().equalsIgnoreCase(STOP)){
            stopForeground(true);
            stopSelf();
        }
        return START_NOT_STICKY;
    }
    private void postNotification(){
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Ikariam")
                .setContentText("Server đang chạy...")
                .setSmallIcon(R.drawable.ic_ikariam_notification_icon)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .build();
        startForeground(NOTIFYCATION_ID, notification);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Long){
            long newFightTimeDistance  = (Long) arg;
            if(fightRun != null)
                jobHandler.removeCallbacks(fightRun);
            fightRun = () -> {
                UseCaseHandler.execute(fight, new Fight.RequestValues(), fightResponse -> {
                    Log.e(getClass().getSimpleName(), "Fighted- status: " + fightResponse.isSuccess());
                    jobHandler.postDelayed(() -> {
                       // Toast.makeText(this, "Refresh lại field", Toast.LENGTH_SHORT).show();
                        UseCaseHandler.execute(populateUnits, new PopulateUnits.RequestValues(), populateResponse -> {
                            Log.e(getClass().getSimpleName(), "Populated- status: " + populateResponse.isSuccess() + ", Mesage: " + populateResponse.getMessage());
                        });
                    }, 2000);
                });
                jobHandler.postDelayed(fightRun, newFightTimeDistance);
            };
            jobHandler.postDelayed(fightRun, 2000L);
        }
    }
}
