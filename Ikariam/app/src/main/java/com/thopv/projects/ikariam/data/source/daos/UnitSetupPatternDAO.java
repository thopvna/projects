package com.thopv.projects.ikariam.data.source.daos;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by thopv on 11/29/2017.
 */

public class UnitSetupPatternDAO {
    private SharedPreferences sharedPreferences;
    private static String key = "units_patterns";
    private UnitSetupPatternDAO(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    private static UnitSetupPatternDAO instance;

    public static UnitSetupPatternDAO getInstance(Context context) {
        if(instance == null){
            instance = new UnitSetupPatternDAO(context);
        }
        return instance;
    }
    @NonNull
    public Map<String,List<BaseTroop>> fetchAll(){
        
        TypeToken<Map<String,List<BaseTroop>>> token = new TypeToken<Map<String,List<BaseTroop>>>(){};
        Map<String,List<BaseTroop>> list = new Gson().fromJson(sharedPreferences.getString(key, ""), token.getType());
        return list != null ? list : new HashMap<>();
    }
    public void addPattern(String name, List<BaseTroop> troops){
        Map<String,List<BaseTroop>> list = fetchAll();
        list.put((list.size() + 1) + ": ".concat(name), troops);
        save(list);
    }
    public void save(Map<String,List<BaseTroop>> list){
        sharedPreferences.edit().putString(key, new Gson().toJson(list)).apply();
    }
}
