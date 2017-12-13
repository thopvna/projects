package com.thopv.projects.ikariam.data.source.repositories;

import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.source.daos.UnablePopulableTroopDAO;
import com.thopv.projects.ikariam.fight.domain.criteria.AttackUnablePopulableTroopCriteria;
import com.thopv.projects.ikariam.fight.domain.entity.populate.UnablePopulateTroop;
import com.thopv.projects.ikariam.home.domain.entity.PartyUtils;

import java.util.List;

/**
 * Created by thopv on 11/30/2017.
 */

public class UnablePopulableTroopRepository implements Repository<UnablePopulateTroop, UnablePopulateTroop> {
    private AppDatabase appDatabase;
    private UnablePopulableTroopDAO unablePopulableTroopDAO;

    public UnablePopulableTroopRepository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        unablePopulableTroopDAO = appDatabase.getUnablePopulableTroopDAO();
    }

    @Override
    public boolean insert(UnablePopulateTroop entity) {
        return unablePopulableTroopDAO.insert(entity) > 0;
    }

    @Override
    public boolean insertAll(List<UnablePopulateTroop> entities) {
        return unablePopulableTroopDAO.insertAll(entities).length == entities.size();
    }

    @Override
    public boolean delete(UnablePopulateTroop id) {
        return unablePopulableTroopDAO.delete(id) > 0;
    }

    @Override
    public boolean deleteAll(List<UnablePopulateTroop> ids) {
        return unablePopulableTroopDAO.deleteAll(ids) == ids.size();
    }

    @Override
    public boolean update(UnablePopulateTroop entity) {
        return unablePopulableTroopDAO.update(entity) > 0;
    }

    @Override
    public boolean updateAll(List<UnablePopulateTroop> entities) {
        return unablePopulableTroopDAO.updateAll(entities) == entities.size();
    }

    @Override
    public List<UnablePopulateTroop> loadAll() {
        return unablePopulableTroopDAO.fetchAll();
    }

    @Override
    public UnablePopulateTroop findById(UnablePopulateTroop id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <Q extends Criteria> List<UnablePopulateTroop> find(Q criteria) {
        if(criteria instanceof AttackUnablePopulableTroopCriteria){
            return unablePopulableTroopDAO.fetchAll(PartyUtils.getBlueParty());
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
