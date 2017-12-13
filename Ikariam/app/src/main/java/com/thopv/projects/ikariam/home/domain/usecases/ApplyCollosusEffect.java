package com.thopv.projects.ikariam.home.domain.usecases;

import com.thopv.projects.ikariam.ComplexResponse;
import com.thopv.projects.ikariam.UseCase;
import com.thopv.projects.ikariam.supports.utils.DateUtils;
import com.thopv.projects.ikariam.data.schema.armies.AttackTroop;
import com.thopv.projects.ikariam.data.schema.armies.CollosusedTroop;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLog;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLogType;
import com.thopv.projects.ikariam.data.source.daos.LogDAO;
import com.thopv.projects.ikariam.data.source.repositories.Repository;
import com.thopv.projects.ikariam.fight.domain.criteria.AttackFieldTroop;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FieldTroop;
import com.thopv.projects.ikariam.home.domain.criterias.AttackFieldTroopsAvailableCriteria;
import com.thopv.projects.ikariam.home.domain.criterias.AttackTroopsAvailableCriteria;
import com.thopv.projects.ikariam.home.domain.entity.Collosus;
import com.thopv.projects.ikariam.home.domain.entity.PartyUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/27/2017.
 */
public class ApplyCollosusEffect extends UseCase<ApplyCollosusEffect.RequestValues, ApplyCollosusEffect.ResponseValues> {
    private Repository<Integer, House> houseRepository;
    private Repository<Integer, Collosus> collosusRepository;
    private Repository<AttackTroop, AttackTroop> attackTroopRepository;
    private Repository<FieldTroop, FieldTroop> fieldTroopRepository;
    private Repository<CollosusedTroop, CollosusedTroop> collosusedTroopRepository;
    private LogDAO logDAO;
    public ApplyCollosusEffect(Repository<Integer, House> houseRepository, Repository<Integer, Collosus> collosusRepository, Repository<AttackTroop, AttackTroop> attackTroopRepository, Repository<FieldTroop, FieldTroop> fieldTroopRepository, Repository<CollosusedTroop, CollosusedTroop> collosusedTroopRepository, LogDAO logDAO) {
        this.houseRepository = houseRepository;
        this.collosusRepository = collosusRepository;
        this.attackTroopRepository = attackTroopRepository;
        this.fieldTroopRepository = fieldTroopRepository;
        this.collosusedTroopRepository = collosusedTroopRepository;
        this.logDAO = logDAO;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues, UseCaseCallback<ResponseValues> callback) {
         House house = houseRepository.findById(PartyUtils.getRedParty());
         if(house == null){
             callback.onCompleted(ComplexResponse.fail("Defense house is not existing."));
             return;
         }

         houseRepository.beginTransaction();

         List<AttackTroop> attackTroops  = attackTroopRepository.find(new AttackTroopsAvailableCriteria());
         List<FieldTroop> fieldTroops = fieldTroopRepository.find(new AttackFieldTroopsAvailableCriteria());

         if((attackTroops != null && attackTroops.size() > 0) || (fieldTroops != null && fieldTroops.size() > 0)) {
             Collosus collosus = Collosus.create(house);
             Collosus oldCollosus = collosusRepository.findById(PartyUtils.getRedParty());
             if (oldCollosus == null) {
                 boolean collosusOk = collosusRepository.insert(collosus);
                 boolean attackTroopsOk = applyAttackTroops(collosus);

                 boolean success = collosusOk && attackTroopsOk;
                 if(success)
                     houseRepository.commitTransaction();

                 houseRepository.endTransaction();
                 callback.onCompleted(ComplexResponse.get(success));
             } else {
                 if (oldCollosus.getNextTimeAvailable() < System.currentTimeMillis()) {
                     boolean collosusOk = collosusRepository.update(collosus);
                     boolean attackTroopsOk = applyAttackTroops(collosus);

                     boolean success = collosusOk && attackTroopsOk;
                     if(success)
                         houseRepository.commitTransaction();

                     houseRepository.endTransaction();
                     callback.onCompleted(ComplexResponse.get(collosusOk && attackTroopsOk));
                 } else {
                     houseRepository.endTransaction();
                     try {
                         callback.onCompleted(ComplexResponse.fail("Unable apply collosus effect. Next time available: " + DateUtils.getDistance(oldCollosus.getNextTimeAvailable())));
                     } catch (Exception ignored) {
                         callback.onCompleted(ComplexResponse.fail("Please try again."));
                     }
                 }
             }
         }
         else{
             houseRepository.endTransaction();
             callback.onCompleted(ComplexResponse.fail("Unable apply collosus effect. Don't have any attack troops."));
         }
    }
    private boolean applyAttackTroops(Collosus collosus){
        List<AttackTroop> attackTroops = attackTroopRepository.loadAll();
        List<FieldTroop> attackFieldTroops = fieldTroopRepository.find(new AttackFieldTroop());
        List<CollosusedTroop> collosusedTroops = new LinkedList<>();

        if(attackTroops != null){
            for(AttackTroop attackTroop : attackTroops){
                int origin = attackTroop.getAmount();
                attackTroop.setAmount(collosus.apply(attackTroop.getAmount()));
                addCollosusedTroop(collosusedTroops,
                        attackTroop.getUnitName(),
                        origin - attackTroop.getAmount(),
                        collosus.getStartTime(),
                        collosus.getEndTime());
            }
        }
        if(attackFieldTroops != null){
            for(FieldTroop attackFieldTroop : attackFieldTroops){
                int origin = attackFieldTroop.getAmount();
                attackFieldTroop.setAmount(collosus.apply(attackFieldTroop.getAmount()));
                addCollosusedTroop(collosusedTroops,
                        attackFieldTroop.getUnitName(),
                        origin - attackFieldTroop.getAmount(),
                        collosus.getStartTime(),
                        collosus.getEndTime());
			}
        }

        ArmyLog armyLog = new ArmyLog.Builder()
                .setStartTime(collosus.getStartTime())
                .setType(ArmyLogType.COLLOSUS)
                .setContent("Vừa bị dính Collosus effect.").build();

        boolean attackOk = attackTroopRepository.updateAll(attackTroops);
        boolean fieldTroopOk = fieldTroopRepository.updateAll(attackFieldTroops);
        boolean collosusTroopOk = collosusedTroopRepository.insertAll(collosusedTroops);

        boolean logOk = logDAO.insert(armyLog) > 0;

        return  attackOk && fieldTroopOk && collosusTroopOk && logOk;
    }

    private void addCollosusedTroop(List<CollosusedTroop> collosusedTroops, String unitName, int amount, long startTime, long endTime) {
        CollosusedTroop collosusedTroop = new CollosusedTroop(unitName,
                amount,
                startTime,
                endTime,
                false);
        if(!collosusedTroops.contains(collosusedTroop)){
            collosusedTroops.add(collosusedTroop);
        }
        else{
            CollosusedTroop oldCollosusedTroop = collosusedTroops.get(collosusedTroops.indexOf(collosusedTroop));
            oldCollosusedTroop.merge(collosusedTroop);
        }
    }

    public static class RequestValues extends UseCase.RequestValues {

    }

    public static class ResponseValues extends UseCase.ResponseValues {

    }
}
