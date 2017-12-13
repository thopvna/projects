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
import com.thopv.projects.ikariam.data.source.daos.HomeTroopDAO;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;

import java.util.List;

/**
 * Created by thopv on 11/14/2017.
 */

public class HomeTroopsViewModel extends AndroidViewModel{
    private HomeTroopDAO homeTroopDAO;
    private UnablePopulableTroopDAO unablePopulableTroopDAO;
    public HomeTroopsViewModel(@NonNull Application application) {
        super(application);
        homeTroopDAO = AppDatabase.getInstance(application).getTroopDAO();
        unablePopulableTroopDAO = AppDatabase.getInstance(application).getUnablePopulableTroopDAO();
    }
    public void getTroops(int party, Callback<LiveData<List<BaseTroop>>> callback){
        Async.execute(() -> homeTroopDAO.getAllLive(party), callback::onResult);
    }
    public void getExtraTroops(int party, Callback<LiveData<List<BaseTroop>>> callback){
        Async.execute(() -> unablePopulableTroopDAO.fetchAllLive(party), callback::onResult);
    }
}
