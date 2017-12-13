package com.thopv.projects.ikariam.data.source.repositories;

import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.schema.armies.AttackTroop;
import com.thopv.projects.ikariam.data.source.daos.AttackTroopDAO;
import com.thopv.projects.ikariam.home.domain.criterias.AttackTroopById;
import com.thopv.projects.ikariam.home.domain.criterias.AttackTroopsAvailableCriteria;

import java.util.Collections;
import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public class AttackTroopRepository implements Repository<AttackTroop,AttackTroop> {
    private AppDatabase appDatabase;
    private AttackTroopDAO attackTroopDAO;
    public AttackTroopRepository(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
        attackTroopDAO = appDatabase.getAttackTroopDAO();
    }

    @Override
    public boolean insert(AttackTroop entity) {
        return attackTroopDAO.insert(entity) > 0;
    }

    @Override
    public boolean insertAll(List<AttackTroop> entities) {
        return attackTroopDAO.insertAll(entities).length == entities.size();
    }

    @Override
    public boolean delete(AttackTroop id) {
        return attackTroopDAO.delete(id) > 0;
    }

    @Override
    public boolean deleteAll(List<AttackTroop> ids) {
        return attackTroopDAO.deleteAll(ids) == ids.size();
    }

    @Override
    public boolean update(AttackTroop entity) {
        return attackTroopDAO.updateAll(entity) > 0;
    }

    @Override
    public boolean updateAll(List<AttackTroop> entities) {
        return attackTroopDAO.updateAll(entities) == entities.size();
    }

    @Override
    public List<AttackTroop> loadAll() {
        return attackTroopDAO.getAll();
    }

    @Override
    public AttackTroop findById(AttackTroop id) {
        return attackTroopDAO.get(id.getUnitName());
    }

    @Override
    public <Q extends Criteria> List<AttackTroop> find(Q criteria) {
        if(criteria instanceof AttackTroopById){
            AttackTroop attackTroop = attackTroopDAO.get(((AttackTroopById) criteria).getUnitName());
            return Collections.singletonList(attackTroop);
        }
        else if(criteria instanceof AttackTroopsAvailableCriteria){
            return attackTroopDAO.getAllAvailable();
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
