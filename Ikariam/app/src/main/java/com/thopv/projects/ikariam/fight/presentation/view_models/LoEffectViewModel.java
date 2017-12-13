package com.thopv.projects.ikariam.fight.presentation.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.thopv.projects.ikariam.supports.async.Async;
import com.thopv.projects.ikariam.supports.async.Callback;
import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.source.daos.LoDAO;
import com.thopv.projects.ikariam.home.domain.entity.Lo;

import java.util.List;

/**
 * Created by thopv on 11/27/2017.
 */

public class LoEffectViewModel extends AndroidViewModel {
    private LoDAO loDAO;
    public LoEffectViewModel(@NonNull Application application) {
        super(application);
        loDAO = AppDatabase.getInstance(application).getLoDAO();
    }

    public void getWhales(Callback<LiveData<List<Lo>>> callback){
        Async.execute(() -> loDAO.fetchAllLive(), callback::onResult);
    }
}
