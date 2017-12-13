package com.thopv.projects.ikariam.fight.presentation.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.source.daos.UnablePopulableTroopDAO;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;
import com.thopv.projects.ikariam.supports.async.Async;
import com.thopv.projects.ikariam.supports.async.Callback;
import com.thopv.projects.ikariam.home.presentation.models.ModelAttackTroop;
import com.thopv.projects.ikariam.data.source.daos.AttackTroopDAO;

import java.util.List;

/**
 * Created by thopv on 11/14/2017.
 */

public class AttackTroopsViewModel extends AndroidViewModel {
    private AttackTroopDAO attackTroopDAO;
    private UnablePopulableTroopDAO unablePopulableTroopDAO;
    public AttackTroopsViewModel(@NonNull Application application) {
        super(application);
        attackTroopDAO = AppDatabase.getInstance(application).getAttackTroopDAO();
        unablePopulableTroopDAO = AppDatabase.getInstance(application).getUnablePopulableTroopDAO();
    }
    public void getAttackTroops(Callback<LiveData<List<BaseTroop>>> callback){
        Async.execute(() -> attackTroopDAO.getAllLive(), callback::onResult);
    }
    public void getExtraAttackTroops(Callback<LiveData<List<BaseTroop>>> callback){
        Async.execute(() -> unablePopulableTroopDAO.fetchAllLive(0), callback::onResult);
    }
}
