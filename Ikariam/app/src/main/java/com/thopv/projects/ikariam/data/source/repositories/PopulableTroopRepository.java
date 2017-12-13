package com.thopv.projects.ikariam.data.source.repositories;

import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.schema.armies.AttackTroop;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.data.source.daos.AttackTroopDAO;
import com.thopv.projects.ikariam.data.source.daos.HomeTroopDAO;
import com.thopv.projects.ikariam.data.source.mappers.populable_troops.FromListAttackToListPopulable;
import com.thopv.projects.ikariam.data.source.mappers.populable_troops.FromPopulableToAttack;
import com.thopv.projects.ikariam.data.source.mappers.populable_troops.FromListHomeToListPopulable;
import com.thopv.projects.ikariam.data.source.mappers.populable_troops.FromPopulableToHome;
import com.thopv.projects.ikariam.fight.domain.criteria.AttackPopulableTroopCriteria;
import com.thopv.projects.ikariam.fight.domain.criteria.HomePopulableTroopCriteria;
import com.thopv.projects.ikariam.fight.domain.entity.populate.PopulableTroop;
import com.thopv.projects.ikariam.home.domain.entity.PartyUtils;

import java.util.List;

/**
 * Created by thopv on 11/21/2017.
 */

public class PopulableTroopRepository implements Repository<PopulableTroop,PopulableTroop> {
    private AppDatabase appDatabase;
    private HomeTroopDAO homeTroopDAO;
    private AttackTroopDAO attackTroopDAO;
    private FromPopulableToAttack fromPopulableToAttackMapper;
    private FromListAttackToListPopulable fromListAttackToListPopulableMapper;
    private FromPopulableToHome fromPopulableToHomeMapper;
    private FromListHomeToListPopulable fromListHomeToListPopulableMapper;
    public PopulableTroopRepository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
        homeTroopDAO = appDatabase.getTroopDAO();
        attackTroopDAO = appDatabase.getAttackTroopDAO();
        fromPopulableToAttackMapper = new FromPopulableToAttack();
        fromListAttackToListPopulableMapper = new FromListAttackToListPopulable();
        fromPopulableToHomeMapper = new FromPopulableToHome();
        fromListHomeToListPopulableMapper = new FromListHomeToListPopulable();
    }

    @Override
    public boolean insert(PopulableTroop entity) {
        if(!entity.isAttack()){
            HomeTroop homeTroop = fromPopulableToHomeMapper.map(entity);
            return homeTroopDAO.insert(homeTroop) > 0;
        }
        else{
            AttackTroop attackTroop = fromPopulableToAttackMapper.map(entity);
            return attackTroopDAO.insert(attackTroop) > 0;
        }
    }

    @Override
    public boolean insertAll(List<PopulableTroop> entities) {
        boolean success = true;
        
        for(PopulableTroop populableTroop : entities){
            boolean result = insert(populableTroop);
            if(!result) {
                success = false;
            }
        }
        
        return success;
    }

    @Override
    public boolean delete(PopulableTroop id) {
        id.setAmount(0);
        if(!id.isAttack()){
            HomeTroop homeTroop = fromPopulableToHomeMapper.map(id);
            return homeTroopDAO.update(homeTroop) > 0;
        }
        else{
            AttackTroop attackTroop = fromPopulableToAttackMapper.map(id);
            return attackTroopDAO.update(attackTroop) > 0;
        }
    }

    /**
     * Tui chỉ set amount = 0 chứ k delete hẳn
     * @param ids
     * @return
     */
    @Override
    public boolean deleteAll(List<PopulableTroop> ids) {
        boolean success = true;
        for(PopulableTroop populableTroop : ids){
            boolean result = delete(populableTroop);
            if(!result) {
                success = false;
            }
        }
        return success;
    }

    @Override
    public boolean update(PopulableTroop entity) {
        if(!entity.isAttack()){
            HomeTroop homeTroop = fromPopulableToHomeMapper.map(entity);
            return homeTroopDAO.update(homeTroop) > 0;
        }
        else{
            AttackTroop attackTroop = fromPopulableToAttackMapper.map(entity);
            return attackTroopDAO.update(attackTroop) > 0;
        }
    }

    @Override
    public boolean updateAll(List<PopulableTroop> entities) {
        boolean success = true;
        for(PopulableTroop populableTroop : entities){
            boolean result = update(populableTroop);
            if(!result) {
                success = false;
            }
        }
        return success;
    }

    @Override
    public List<PopulableTroop> loadAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public PopulableTroop findById(PopulableTroop id) {
        throw new UnsupportedOperationException();    }

    @Override
    public <Q extends Criteria> List<PopulableTroop> find(Q criteria) {
        if(criteria instanceof AttackPopulableTroopCriteria){
            List<AttackTroop> attackTroops = attackTroopDAO.getAllAvailable();
            return fromListAttackToListPopulableMapper.map(attackTroops);
        }
        else if(criteria instanceof HomePopulableTroopCriteria){
            List<HomeTroop> homeTroops = homeTroopDAO.getAllAvailable(PartyUtils.getRedParty());
            return fromListHomeToListPopulableMapper.map(homeTroops);
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
