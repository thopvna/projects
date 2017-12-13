package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.armies.AttackTroop;
import com.thopv.projects.ikariam.data.schema.armies.CollosusedTroop;
import com.thopv.projects.ikariam.data.source.repositories.Repository;
import com.thopv.projects.ikariam.home.domain.criterias.CollosusedTroopsNonConfirmAvailable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/27/2017.
 */
public class ConfirmCollosusTroops extends UseCase<ConfirmCollosusTroops.RequestValues, ConfirmCollosusTroops.ResponseValues> {
    public ConfirmCollosusTroops(Repository<CollosusedTroop, CollosusedTroop> collosusedTroopRepository, Repository<AttackTroop, AttackTroop> attackTroopRepository) {
        this.collosusedTroopRepository = collosusedTroopRepository;
        this.attackTroopRepository = attackTroopRepository;
    }

    private Repository<CollosusedTroop, CollosusedTroop> collosusedTroopRepository;
    private Repository<AttackTroop, AttackTroop> attackTroopRepository;
    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        collosusedTroopRepository.beginTransaction();

        List<CollosusedTroop> collosusedTroops = collosusedTroopRepository.find(new CollosusedTroopsNonConfirmAvailable());
        List<AttackTroop> attackTroops = new LinkedList<>();


        if(collosusedTroops == null || collosusedTroops.size() <= 0){
            collosusedTroopRepository.endTransaction();
            callback.onCompleted(ComplexResponse.fail("Don't have any non confirm available collosused troops."));
            return;
        }

        for(CollosusedTroop collosusedTroop : collosusedTroops) {
            collosusedTroop.setConfirm(true);
            AttackTroop attackTroop = new AttackTroop.Builder()
                    .setUnitName(collosusedTroop.getUnitName())
                    .setAmount(collosusedTroop.getAmount())
                    .build();
            attackTroops.add(attackTroop);
        }


        List<AttackTroop> oldAttackTroops = attackTroopRepository.loadAll();
        for(AttackTroop attackTroop : attackTroops){
            if(!oldAttackTroops.contains(attackTroop)){
                oldAttackTroops.add(attackTroop);
            }
            else{
                AttackTroop oldAttackTroop = oldAttackTroops.get(oldAttackTroops.indexOf(attackTroop));
                oldAttackTroop.merge(attackTroop);
            }
        }
        boolean collosusOk = collosusedTroopRepository.updateAll(collosusedTroops);
        boolean attackOk = attackTroopRepository.insertAll(oldAttackTroops);
        boolean success = collosusOk && attackOk;

        if(success)
            collosusedTroopRepository.commitTransaction();

        collosusedTroopRepository.endTransaction();

        callback.onCompleted(ComplexResponse.get(success));
    }

    public static class RequestValues extends UseCase.RequestValues {

    }

    public static class ResponseValues extends UseCase.ResponseValues {

    }
}
