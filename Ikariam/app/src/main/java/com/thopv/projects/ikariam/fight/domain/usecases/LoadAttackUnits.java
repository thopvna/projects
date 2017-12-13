package com.thopv.projects.ikariam.fight.domain.usecases;

import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.armies.AttackTroop;
import com.thopv.projects.ikariam.data.source.repositories.Repository;
import com.thopv.projects.ikariam.fight.domain.criteria.AttackUnablePopulableTroopCriteria;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FieldTroop;
import com.thopv.projects.ikariam.fight.domain.entity.populate.UnablePopulateTroop;
import com.thopv.projects.ikariam.home.domain.criterias.AttackFieldTroopsAvailableCriteria;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/29/2017.
 */
public class LoadAttackUnits extends UseCase<LoadAttackUnits.RequestValues, LoadAttackUnits.ResponseValues> {
    private Repository<AttackTroop, AttackTroop> attackTroopRepository;
    private Repository<FieldTroop, FieldTroop> fieldTroopRepository;
    private Repository<UnablePopulateTroop, UnablePopulateTroop> unablePopulateTroopRepository;
    public LoadAttackUnits(Repository<AttackTroop, AttackTroop> attackTroopRepository, Repository<FieldTroop, FieldTroop> fieldTroopRepository, Repository<UnablePopulateTroop, UnablePopulateTroop> unablePopulateTroopRepository) {
        this.attackTroopRepository = attackTroopRepository;
        this.fieldTroopRepository = fieldTroopRepository;
        this.unablePopulateTroopRepository = unablePopulateTroopRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        List<AttackTroop> attackTroops = attackTroopRepository.loadAll();
        List<FieldTroop> attackFieldTroops = fieldTroopRepository.find(new AttackFieldTroopsAvailableCriteria());
        List<UnablePopulateTroop> unablePopulateTroops = unablePopulateTroopRepository.find(new AttackUnablePopulableTroopCriteria());
        List<BaseTroop> baseTroops = new LinkedList<>();
        //TODO: Vẫn hơi sai sai.
        if(attackTroops != null){
            for(AttackTroop attackTroop : attackTroops){
                BaseTroop baseTroop = new BaseTroop(attackTroop.getUnitName(), attackTroop.getAmount());
                additionalTroop(baseTroops, baseTroop);
            }
        }
        if(attackFieldTroops != null){
            for(FieldTroop fieldTroop : attackFieldTroops){
                BaseTroop baseTroop = new BaseTroop(fieldTroop.getUnitName(), fieldTroop.getAmount());
                additionalTroop(baseTroops, baseTroop);
            }
        }
        if(unablePopulateTroops != null) {
            for(UnablePopulateTroop unablePopulateTroop : unablePopulateTroops){
                BaseTroop baseTroop = new BaseTroop(unablePopulateTroop.getUnitName(), unablePopulateTroop.getAmount());
                additionalTroop(baseTroops, baseTroop);
            }
        }
        callback.onCompleted(ComplexResponse.get(new ResponseValues(baseTroops)));
    }

    private void additionalTroop(List<BaseTroop> baseTroops, BaseTroop baseTroop) {
        if(baseTroops.contains(baseTroop)){
            BaseTroop oldTroop = baseTroops.get(baseTroops.indexOf(baseTroop));
            oldTroop.merge(baseTroop);
        }
        else {
            baseTroops.add(baseTroop);
        }
    }

    public static class RequestValues extends UseCase.RequestValues {

    }

    public static class ResponseValues extends UseCase.ResponseValues {
        private List<BaseTroop> attackTroops;

        public List<BaseTroop> getAttackTroops() {
            return attackTroops;
        }

        public ResponseValues(List<BaseTroop> attackTroops) {
            this.attackTroops = attackTroops;
        }
    }
}
