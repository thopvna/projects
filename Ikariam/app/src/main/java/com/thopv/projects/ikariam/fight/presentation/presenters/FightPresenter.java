package com.thopv.projects.ikariam.fight.presentation.presenters;

import android.util.Log;

import com.thopv.projects.ikariam.UseCaseHandler;
import com.thopv.projects.ikariam.supports.utils.MommentUtils;
import com.thopv.projects.ikariam.fight.domain.usecases.LoadAttackUnits;
import com.thopv.projects.ikariam.fight.domain.usecases.ReturnUnits;
import com.thopv.projects.ikariam.fight.presentation.contracts.FightContract;
import com.thopv.projects.ikariam.fight.presentation.view_models.AttackTroopsViewModel;
import com.thopv.projects.ikariam.fight.presentation.view_models.FieldTroopsViewModel;
import com.thopv.projects.ikariam.fight.presentation.view_models.FightStatusViewModel;
import com.thopv.projects.ikariam.fight.presentation.view_models.HomeTroopsViewModel;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.fight.presentation.view_models.LoEffectViewModel;
import com.thopv.projects.ikariam.home.domain.entity.Lo;
import com.thopv.projects.ikariam.home.domain.entity.PartyUtils;

import java.util.Map;

/**
 * Created by thopv on 11/14/2017.
 */

public class FightPresenter implements FightContract.Presenter{
    private FightContract.Controller controller;
    private House house;
    private ReturnUnits returnUnits;
    private LoadAttackUnits loadAttackUnits;
    private Runnable returnUnitsRun;
    private boolean waitingForReturnUNits;

    public FightPresenter(FightContract.Controller controller,
                          House house,
                          FieldTroopsViewModel fieldTroopsViewModel,
                          AttackTroopsViewModel attackTroopsViewModel,
                          HomeTroopsViewModel homeTroopsViewModel,
                          LoEffectViewModel loEffectViewModel,
                          FightStatusViewModel fightStatusViewModel,
                          ReturnUnits returnUnits, LoadAttackUnits loadAttackUnits) {
        this.controller = controller;
        this.house = house;
        this.returnUnits = returnUnits;
        this.loadAttackUnits = loadAttackUnits;

        setupOptions();

        fieldTroopsViewModel.getFightingTroops(response -> {
            if(response.isSuccess()){
                response.getPayload().observe(controller, controller::showFightingTroops);
            }
            else{
                controller.showError(response.getMessage());
            }
        });
        attackTroopsViewModel.getAttackTroops(response -> {
            if(response.isSuccess()){
                response.getPayload().observe(controller, controller::showAttackReverseTroops);
            }
            else{
                controller.showError(response.getMessage());
            }
        });
        homeTroopsViewModel.getTroops(house.getParty(), response -> {
            if(response.isSuccess()){
                response.getPayload().observe(controller, controller::showHomeTroops);
            }
            else{
                controller.showError(response.getMessage());
            }
        });
        attackTroopsViewModel.getExtraAttackTroops(response -> {
            if(response.isSuccess()){
                response.getPayload().observe(controller, controller::showExtraAttackReverseTroops);
            }
            else{
                controller.showError(response.getMessage());
            }
        });
        homeTroopsViewModel.getExtraTroops(house.getParty(), response -> {
            if(response.isSuccess()){
                response.getPayload().observe(controller, controller::showExtraHomeTroops);
            }
            else{
                controller.showError(response.getMessage());
            }
        });
        loEffectViewModel.getWhales(response -> {
            if(response.isSuccess()){
                response.getPayload().observe(controller, los -> {
                    if(los != null){
                        for(Lo lo : los){
                            if(lo.getParty() == PartyUtils.getBlueParty() && lo.isAffect()){
                                controller.showAttackLoEffect();
                            }
                            else if(lo.getParty() == PartyUtils.getRedParty() && lo.isAffect()){
                                controller.showDefLoEffect();
                            }
                        }
                    }
                });
            }
            else{
                controller.showError(response.getMessage());
            }
        });
        fightStatusViewModel.getFightStatusObservable(response -> {
            if(response.isSuccess()) {
                response.getPayload().observe(controller, fightStatus -> {
                    if(fightStatus != null) {
                        controller.showFightStatus(fightStatus.isFighting(), fightStatus.getStartTime(), fightStatus.getTurn());
                        Log.e(getClass().getSimpleName(), "Fight Status Change.");
                    }
                });
            }
        });
    }

    public void sendUnits(){
        controller.showUnitsSend(house);
    }

    @Override
    public void returnUnits(Map<String, Integer> unitsAmount, long goTime, long before) {
        waitingForReturnUNits = true;
        returnUnitsRun = () -> {
            UseCaseHandler.execute(returnUnits, new ReturnUnits.RequestValues(unitsAmount, goTime), response -> {
                waitingForReturnUNits = false;
                if (response.isSuccess()) {
                    Log.e(getClass().getSimpleName(), "Return units success.");
                } else {
                    Log.e(getClass().getSimpleName(), "Return units failed." + response.getMessage());
                }
            });
        };
        MommentUtils.runBefore(before, returnUnitsRun);
        controller.showError("Thành công. Lính sẽ bắt đầu rút vào đợt tấn công sau.");
    }

    @Override
    public void returnUnits() {
        UseCaseHandler.execute(loadAttackUnits, new LoadAttackUnits.RequestValues(), response -> {
            if(response.isSuccess()){
                controller.showUnitsReturn(response.getPayload().getAttackTroops());
            }
            else{
                controller.showError("Operation failed. " + response.getMessage());
            }
        });
    }
    @Override
    public void changeFightTimeDistance(long newDistance){
        if(waitingForReturnUNits) {
            if (returnUnitsRun != null) {
                MommentUtils.cancel(returnUnitsRun);
            }
            MommentUtils.runBefore(newDistance, returnUnitsRun);
        }
    }

    private void setupOptions(){
        boolean isBlue = house.isBlue();
        if(isBlue){
            controller.hideAttackOptionsMenu();
            controller.hideAttackOptionsMenu();
            return;
        }
        controller.showAttackOptionsMenu();
        controller.hideDefOptionsMenu();
    }
}
