package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.data.schema.armies.MovingTroop;
import com.thopv.projects.ikariam.data.source.repositories.Repository;
import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.armies.AttackTroop;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.home.domain.criterias.AttackTroopById;
import com.thopv.projects.ikariam.home.domain.criterias.HomeTroopById;
import com.thopv.projects.ikariam.home.domain.criterias.MovingTroopsNonConfirmAvailable;
import com.thopv.projects.ikariam.home.domain.entity.PartyUtils;

import junit.framework.Assert;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public class ConfirmMovingTroops extends UseCase<ConfirmMovingTroops.RequestValues, ConfirmMovingTroops.ResponseValues> {
    private Repository<MovingTroop, MovingTroop> movingTroopRepository;
    private Repository<AttackTroop, AttackTroop> attackTroopRepository;
    private Repository<HomeTroop, HomeTroop> homeTroopRepository;

    public ConfirmMovingTroops(Repository<MovingTroop, MovingTroop> movingTroopRepository, Repository<AttackTroop, AttackTroop> attackTroopRepository, Repository<HomeTroop, HomeTroop> homeTroopRepository) {
        this.movingTroopRepository = movingTroopRepository;
        this.attackTroopRepository = attackTroopRepository;
        this.homeTroopRepository = homeTroopRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        MovingTroopsNonConfirmAvailable nonConfirmCriteria = new MovingTroopsNonConfirmAvailable();
        movingTroopRepository.beginTransaction();

        List<MovingTroop> nonConfirmMovingTroops = movingTroopRepository.find(nonConfirmCriteria);
        List<AttackTroop> newAttackTroops = new LinkedList<>();
        List<HomeTroop> newHomeTroops = new LinkedList<>();

        if(nonConfirmMovingTroops == null || nonConfirmMovingTroops.size() <=0){
            movingTroopRepository.endTransaction();
            callback.onCompleted(ComplexResponse.fail("Don't have any non confirm moving troops available."));
            return;
        }

        for(MovingTroop movingTroop : nonConfirmMovingTroops){
            if(!movingTroop.isReturn())
                convertMovingToAttackTroop(newAttackTroops, movingTroop);
            else
                convertMovingToHomeTroop(newHomeTroops, movingTroop);
        }

        boolean movingOk = movingTroopRepository.updateAll(nonConfirmMovingTroops);
        boolean attackOk = attackTroopRepository.insertAll(newAttackTroops);
        boolean homeOk = homeTroopRepository.insertAll(newHomeTroops);

        boolean success = movingOk && attackOk && homeOk;
        if(success) {
            movingTroopRepository.commitTransaction();
        }
        movingTroopRepository.endTransaction();
        callback.onCompleted(ComplexResponse.get(success));
    }

    private void convertMovingToAttackTroop(List<AttackTroop> newAttackTroops, MovingTroop movingTroop) {
        AttackTroopById attackTroopById = new AttackTroopById( movingTroop.getUnitName());
        List<AttackTroop> oldAttackTroops = attackTroopRepository.find(attackTroopById);
        movingTroop.setConfirm(true);
        if(oldAttackTroops == null || oldAttackTroops.size() == 0 || oldAttackTroops.get(0) == null) {
            AttackTroop attackTroop = new AttackTroop.Builder()
                    .setAmount(movingTroop.getAmount())
                    .setUnitName(movingTroop.getUnitName())
                    .build();
            newAttackTroops.add(attackTroop);
        }
        else{
            AttackTroop attackTroop = oldAttackTroops.get(0);
            attackTroop.merge(movingTroop.getAmount());
            newAttackTroops.add(attackTroop);
        }
    }
    private void convertMovingToHomeTroop(List<HomeTroop> homeTroops, MovingTroop movingTroop) {
        int blueParty = PartyUtils.getBlueParty();
        HomeTroopById homeTroopById = new HomeTroopById(blueParty, movingTroop.getUnitName());
        List<HomeTroop> oldHomeTroops = homeTroopRepository.find(homeTroopById);
        movingTroop.setConfirm(true);
        if(oldHomeTroops == null || oldHomeTroops.size() == 0) {
            HomeTroop homeTroop = new HomeTroop(blueParty, movingTroop.getUnitName(), movingTroop.getAmount());
            homeTroops.add(homeTroop);
        }
        else{
            Assert.assertTrue(oldHomeTroops.size() == 1);
            HomeTroop homeTroop = oldHomeTroops.get(0);
            homeTroop.merge(movingTroop.getAmount());
            homeTroops.add(homeTroop);
        }
    }

    public static class RequestValues extends UseCase.RequestValues{
    }
    public static class ResponseValues extends UseCase.ResponseValues{

    }
}
