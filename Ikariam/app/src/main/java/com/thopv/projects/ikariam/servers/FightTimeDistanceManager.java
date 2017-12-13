package com.thopv.projects.ikariam.servers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.thopv.projects.ikariam.supports.utils.DateUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

/**
 * Created by thopv on 11/27/2017.
 */

public class FightTimeDistanceManager{
    public static FightTimeDistanceManager instance;
    private SharedPreferences sharedPreferences;
    private String FIGHT_TIME_DISTANCE_KEY = "fight_time_distance";
    private final long DEFAULT_DISTANCE = 15 * DateUtils.MINUTE;
    private FightTimeDistanceManager(Context context){
         sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static FightTimeDistanceManager getInstance(Context context) {
        if(instance == null){
            instance = new FightTimeDistanceManager(context);
        }
        return instance;
    }

    public long getFightTimeDistance(){
        return sharedPreferences.getLong(FIGHT_TIME_DISTANCE_KEY, DEFAULT_DISTANCE);
    }
    public void setFightTimeDistance(long distance){
        sharedPreferences.edit().putLong(FIGHT_TIME_DISTANCE_KEY, distance).apply();
        if(observers != null){
            for(Observer observer : observers) {
                observer.update(null, distance);
            }
        }
    }
    public void addObserver(Observer observer){
        if(observers == null){
            observers = new LinkedList<>();
        }
        observers.add(observer);
        observer.update(null, getFightTimeDistance());
    }
    private List<Observer> observers;
}
