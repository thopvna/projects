package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.data.schema.armies.AttackTroop;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.source.repositories.Repository;
import com.thopv.projects.ikariam.fight.domain.criteria.AllFieldTroopsHasHitPoints;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FieldTroop;
import com.thopv.projects.ikariam.home.domain.criterias.HomeTroopByParty;

import junit.framework.Assert;

import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public class ClearUnits extends UseCase<ClearUnits.RequestValues, ClearUnits.ResponseValues>{
    private Repository<HomeTroop, HomeTroop> homeTroopRepository;
    private Repository<FieldTroop, FieldTroop> fieldTroopRepository;
    private Repository<AttackTroop, AttackTroop> attackTroopRepository;
    public ClearUnits(Repository<HomeTroop, HomeTroop> homeTroopRepository, Repository<FieldTroop, FieldTroop> fieldTroopRepository, Repository<AttackTroop, AttackTroop> attackTroopRepository) {
        this.homeTroopRepository = homeTroopRepository;
        this.fieldTroopRepository = fieldTroopRepository;
        this.attackTroopRepository = attackTroopRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
        House house = requestValues.getHouse();
        Assert.assertNotNull(house);
        boolean success;
        boolean homeTroopsOk;
        boolean attackTroopsOk ;
        boolean fieldTroopsOk = true;
        fieldTroopRepository.beginTransaction();

        List<HomeTroop> homeTroops = homeTroopRepository.find(new HomeTroopByParty(house.getParty()));
        homeTroopsOk = homeTroopRepository.deleteAll(homeTroops);

        List<AttackTroop> attackTroops = attackTroopRepository.loadAll();
        attackTroopsOk = attackTroopRepository.deleteAll(attackTroops);

        if(!house.isBlue()){
            List<FieldTroop> fieldTroops = fieldTroopRepository.find(new AllFieldTroopsHasHitPoints());
            fieldTroopsOk = fieldTroopRepository.deleteAll(fieldTroops);
        }

        success = homeTroopsOk && attackTroopsOk && fieldTroopsOk;

        if(success)
            fieldTroopRepository.commitTransaction();

        fieldTroopRepository.endTransaction();
        callback.onCompleted(ComplexResponse.get(success));
    }

    public static class RequestValues extends UseCase.RequestValues{
        private House house;

        public RequestValues(House house) {
            this.house = house;
        }

        public House getHouse() {
            return house;
        }
    }
    public static class ResponseValues extends UseCase.ResponseValues{
    }
}
