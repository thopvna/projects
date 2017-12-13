package com.thopv.projects.ikariam.data.source.repositories;

import com.thopv.projects.ikariam.data.schema.armies.CollosusedTroop;
import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.source.daos.CollosusedTroopDAO;
import com.thopv.projects.ikariam.home.domain.criterias.CollosusedTroopsNonConfirmAvailable;
import com.thopv.projects.ikariam.home.domain.criterias.CollosusedTroopsStartAt;

import java.util.List;

/**
 * Created by thopv on 11/27/2017.
 */

public class CollosusedTroopRepository implements Repository<CollosusedTroop, CollosusedTroop>{
    private AppDatabase appDatabase;
    private CollosusedTroopDAO collosusedTroopDAO;

    public CollosusedTroopRepository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        collosusedTroopDAO = appDatabase.getCollosusedTroopDAO();
    }

    @Override
    public boolean insert(CollosusedTroop entity) {
        return collosusedTroopDAO.insert(entity) > 0;
    }

    @Override
    public boolean insertAll(List<CollosusedTroop> entities) {
        return collosusedTroopDAO.insertAll(entities).length == entities.size();
    }

    @Override
    public boolean delete(CollosusedTroop id) {
        return collosusedTroopDAO.delete(id) > 0;
    }

    @Override
    public boolean deleteAll(List<CollosusedTroop> ids) {
        return collosusedTroopDAO.deleteAll(ids) == ids.size();
    }

    @Override
    public boolean update(CollosusedTroop entity) {
        return collosusedTroopDAO.update(entity) > 0;
    }

    @Override
    public boolean updateAll(List<CollosusedTroop> entities) {
        return collosusedTroopDAO.updateAll(entities) == entities.size();
    }

    @Override
    public List<CollosusedTroop> loadAll() {
        return collosusedTroopDAO.fetchAll();
    }

    @Override
    public CollosusedTroop findById(CollosusedTroop id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Q extends Criteria> List<CollosusedTroop> find(Q criteria) {
        if(criteria instanceof CollosusedTroopsNonConfirmAvailable){
            return collosusedTroopDAO.fetch(System.currentTimeMillis(), false);
        }
        else if(criteria instanceof CollosusedTroopsStartAt){
            return collosusedTroopDAO.fetchStartAt(((CollosusedTroopsStartAt) criteria).getStartTime());
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void runInTransaction(Runnable runnable) {
        appDatabase.runInTransaction(runnable);
    }

    @Override
    public void beginTransaction() {
        appDatabase.beginTransaction();
    }

    @Override
    public void endTransaction() {
        appDatabase.endTransaction();
    }

    @Override
    public void commitTransaction() {
        appDatabase.setTransactionSuccessful();
    }
}
