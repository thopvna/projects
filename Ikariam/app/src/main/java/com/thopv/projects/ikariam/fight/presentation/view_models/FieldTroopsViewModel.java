package com.thopv.projects.ikariam.fight.presentation.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.supports.async.Async;
import com.thopv.projects.ikariam.supports.async.Callback;
import com.thopv.projects.ikariam.home.presentation.models.ModelFieldTroop;
import com.thopv.projects.ikariam.data.source.daos.FieldTroopDAO;

import java.util.List;

/**
 * Created by thopv on 11/14/2017.
 */

public class FieldTroopsViewModel extends AndroidViewModel {
    private FieldTroopDAO fieldTroopDAO;
    public FieldTroopsViewModel(@NonNull Application application) {
        super(application);
        fieldTroopDAO = AppDatabase.getInstance(application).getFieldTroopsDAO();
    }

    public void getFightingTroops(Callback<LiveData<List<ModelFieldTroop>>> callback) {
        Async.execute(() -> fieldTroopDAO.getAllLive(), callback::onResult);
    }
}
