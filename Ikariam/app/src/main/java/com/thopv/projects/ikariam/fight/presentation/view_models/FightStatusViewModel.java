package com.thopv.projects.ikariam.fight.presentation.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.thopv.projects.ikariam.supports.async.Async;
import com.thopv.projects.ikariam.supports.async.Callback;
import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.source.daos.FightStatusDAO;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FightStatus;

/**
 * Created by thopv on 11/29/2017.
 */

public class FightStatusViewModel extends AndroidViewModel {
    private FightStatusDAO fightStatusDAO;
    public FightStatusViewModel(@NonNull Application application) {
        super(application);
        fightStatusDAO = AppDatabase.getInstance(application).getFightStatusDAO();
    }
    public void getFightStatusObservable(Callback<LiveData<FightStatus>> callback){
        Async.execute(() -> fightStatusDAO.fetchLive(), callback::onResult);
    }
}
