package com.thopv.projects.ikariam.data.source.repositories;

import com.thopv.projects.ikariam.data.schema.armies.MovingTroop;
import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.source.daos.MovingTroopDAO;
import com.thopv.projects.ikariam.home.domain.criterias.MovingTroopsNonConfirmAvailable;
import com.thopv.projects.ikariam.home.domain.criterias.MovingTroopsStartAt;

import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public class MovingTroopRepository implements Repository<MovingTroop,MovingTroop> {
    private AppDatabase appDatabase;
    private MovingTroopDAO movingTroopDAO;
    public MovingTroopRepository(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
        this.movingTroopDAO = appDatabase.getMovingDAO();
    }
    @Override
    public boolean insert(MovingTroop entity) {
        return movingTroopDAO.insert(entity) > 0;
    }

    @Override
    public boolean insertAll(List<MovingTroop> entities) {
        return movingTroopDAO.insertAll(entities).length == entities.size();
    }

    @Override
    public boolean delete(MovingTroop id) {
        return movingTroopDAO.delete(id) > 0;
    }

    @Override
    public boolean deleteAll(List<MovingTroop> ids) {
        return movingTroopDAO.deleteAll(ids) == ids.size();
    }

    @Override
    public boolean update(MovingTroop entity) {
        return movingTroopDAO.update(entity) > 0;
    }

    @Override
    public boolean updateAll(List<MovingTroop> entities) {
        return movingTroopDAO.updateAll(entities) == entities.size();
    }

    @Override
    public List<MovingTroop> loadAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MovingTroop findById(MovingTroop id) {
        return movingTroopDAO.get(id.getStartTime(), id.getUnitName());
    }

    @Override
    public <Q extends Criteria> List<MovingTroop> find(Q criteria) {
        if(criteria instanceof MovingTroopsStartAt){
            return movingTroopDAO.getAllMovingTroopsStartAt(((MovingTroopsStartAt) criteria).getStartTime());
        }
        else if(criteria instanceof MovingTroopsNonConfirmAvailable){
            return movingTroopDAO.getAllMovingTroops(System.currentTimeMillis(), false);
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
