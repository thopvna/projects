package com.thopv.projects.ikariam.data.source.repositories;

import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.data.source.daos.HomeTroopDAO;
import com.thopv.projects.ikariam.home.domain.criterias.HomeTroopByParty;
import com.thopv.projects.ikariam.home.domain.criterias.HomeTroopById;

import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public class HomeTroopRepository implements Repository<HomeTroop, HomeTroop> {
    private AppDatabase appDatabase;
    private HomeTroopDAO homeTroopDAO;
    public HomeTroopRepository(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
        homeTroopDAO = appDatabase.getTroopDAO();
    }

    @Override
    public boolean insert(HomeTroop entity) {
        return homeTroopDAO.insert(entity) > 0;
    }

    @Override
    public boolean insertAll(List<HomeTroop> entities) {
        return homeTroopDAO.insertAll(entities).length == entities.size();
    }

    @Override
    public boolean delete(HomeTroop id) {
        return homeTroopDAO.delete(id) > 0;
    }

    @Override
    public boolean deleteAll(List<HomeTroop> ids) {
        return homeTroopDAO.deleteAll(ids) == ids.size();
    }

    @Override
    public boolean update(HomeTroop entity) {
        return homeTroopDAO.update(entity) > 0;
    }

    @Override
    public boolean updateAll(List<HomeTroop> entities) {
        return homeTroopDAO.updateAll(entities) == entities.size();
    }

    @Override
    public List<HomeTroop> loadAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public HomeTroop findById(HomeTroop id) {
        return homeTroopDAO.get(id.getParty(), id.getUnitName());
    }

    @Override
    public <Q extends Criteria> List<HomeTroop> find(Q criteria) {
        if(criteria instanceof HomeTroopByParty){
            return homeTroopDAO.getAll(((HomeTroopByParty) criteria).getParty());
        }
        else if(criteria instanceof HomeTroopById){
            return homeTroopDAO.getAll(((HomeTroopById) criteria).getParty(), ((HomeTroopById) criteria).getUnitName());
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
