package com.thopv.projects.ikariam.home.presentation.presenters;

import com.thopv.projects.ikariam.UseCaseHandler;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.home.domain.entity.PartyUtils;
import com.thopv.projects.ikariam.home.domain.usecases.ClearUnits;
import com.thopv.projects.ikariam.home.domain.usecases.SetupHomeUnits;
import com.thopv.projects.ikariam.home.domain.usecases.ApplyLoEffect;
import com.thopv.projects.ikariam.home.domain.usecases.ApplyWhaleEffect;
import com.thopv.projects.ikariam.home.domain.usecases.GetLogDetail;
import com.thopv.projects.ikariam.home.domain.usecases.GetTroopCount;
import com.thopv.projects.ikariam.home.domain.usecases.LoadEffectStatus;
import com.thopv.projects.ikariam.home.domain.usecases.LoadHomeTroops;
import com.thopv.projects.ikariam.home.domain.usecases.LoadLogs;
import com.thopv.projects.ikariam.home.domain.usecases.RemoveHouse;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;
import com.thopv.projects.ikariam.home.presentation.contracts.MeMenuContract;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.schema.logs.ArmyLog;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by thopv on 11/13/2017.
 */

public class MeMenuPresenter implements MeMenuContract.Presenter{
    private MeMenuContract.Controller controller;
    private House house;
    private GetTroopCount getTroopCount;
    private SetupHomeUnits setupHomeUnits;
    private LoadLogs loadLogs;
    private GetLogDetail getLogDetail;
    private RemoveHouse removeHouse;
    private LoadHomeTroops loadHomeTroops;
    private ApplyLoEffect applyLoEffect;
    private ApplyWhaleEffect applyWhaleEffect;
    private LoadEffectStatus loadEffectStatus;
    private ClearUnits clearUnits;
    @Inject
    public MeMenuPresenter(MeMenuContract.Controller controller, House house, GetTroopCount getTroopCount, SetupHomeUnits setupHomeUnits, LoadLogs loadLogs, GetLogDetail getLogDetail, RemoveHouse removeHouse, LoadHomeTroops loadHomeTroops, ApplyLoEffect applyLoEffect, ApplyWhaleEffect applyWhaleEffect, LoadEffectStatus loadEffectStatus, ClearUnits clearUnits) {
        this.controller = controller;
        this.house = house;
        this.getTroopCount = getTroopCount;
        this.setupHomeUnits = setupHomeUnits;
        this.loadLogs = loadLogs;
        this.getLogDetail = getLogDetail;
        this.removeHouse = removeHouse;
        this.loadHomeTroops = loadHomeTroops;
        this.applyLoEffect = applyLoEffect;
        this.applyWhaleEffect = applyWhaleEffect;
        this.loadEffectStatus = loadEffectStatus;
        this.clearUnits = clearUnits;
        reloadTroopsCount();
        loadEffectStatus();
    }

    private void reloadTroopsCount(){
        UseCaseHandler.execute(getTroopCount, new GetTroopCount.RequestValues(house.getParty()), response -> {
            if(response.isSuccess()){
                controller.showUnitsAmount(response.getPayload().getCount());
            }
            else{
                controller.showError(response.getMessage());
            }
        });
    }
    public void setupUnits(List<BaseTroop> troopsInform){
        List<HomeTroop> homeTroops = new ArrayList<>();
        for(BaseTroop inform : troopsInform){
            homeTroops.add(new HomeTroop(house.getParty(), inform.getUnitName(), inform.getAmount()));
        }
        UseCaseHandler.execute(setupHomeUnits, new SetupHomeUnits.RequestValues(homeTroops), response -> {
            if(response.isSuccess()){
                controller.showError("Setup home units success.");
                reloadTroopsCount();
            }
            else{
                controller.showError("Operation failed. " + response.getMessage());
            }
        });
    }
    public void removeHouse(){
        UseCaseHandler.execute(removeHouse, new RemoveHouse.RequestValues(house.getParty()), response -> {
            if(response.isSuccess()){
                controller.showError("Xóa thành công.");
                controller.cancel();
            }
            else{
                controller.showError(response.getMessage());
            }
        });
    }

    @Override
    public void applyWhaleEffect() {
        UseCaseHandler.execute(applyWhaleEffect, new ApplyWhaleEffect.RequestValues(), response -> {
            if(response.isSuccess()){
                controller.showError("Apply whale effect success.");
                loadEffectStatus();
            }
            else{
                controller.showError("Operation failed. " + response.getMessage());
            }
        });
    }

    @Override
    public void applyLoEffect() {
        UseCaseHandler.execute(applyLoEffect, new ApplyLoEffect.RequestValues(house.getParty()), response -> {
            if(response.isSuccess()){
                controller.showError("Apply lo effect success.");
                loadEffectStatus();
            }
            else{
                controller.showError("Operation failed. " + response.getMessage());
            }
        });
    }

    @Override
    public void loadEffectStatus(){
        UseCaseHandler.execute(loadEffectStatus, new LoadEffectStatus.RequestValues(house.getParty()), response -> {
            if(response.isSuccess()){
                controller.showEffectStatus(response.getPayload().getStatus());
            }
            else{
                controller.showError("Operation failed. " + response.getMessage());
            }
        });
    }

    @Override
    public void loadHomeTroops() {
        UseCaseHandler.execute(loadHomeTroops, new LoadHomeTroops.RequestValues(PartyUtils.getBlueParty()), response -> {
            if(response.isSuccess()){
                List<BaseTroop> baseTroops = new LinkedList<>();
                for(HomeTroop homeTroop : response.getPayload().getHomeTroops()){
                    baseTroops.add(new BaseTroop(homeTroop.getUnitName(), homeTroop.getAmount()));
                }
                controller.showUnitsSetup(baseTroops);
            }
            else{
                controller.showUnitsSetup(new LinkedList<>());
            }
        });
    }

    @Override
    public void clearUnits() {
        UseCaseHandler.execute(clearUnits, new ClearUnits.RequestValues(house), response -> {
            if(response.isSuccess()){
                controller.showError("Clear units success.");
                reloadTroopsCount();
            }
            else{
                controller.showError("Operation failed. " + response.getMessage());
            }
        });
    }

    public void loadTroops(){
        UseCaseHandler.execute(loadHomeTroops,
                new LoadHomeTroops.RequestValues(house.getParty()),
                response -> {
                    if(response.isSuccess()){
                        controller.showUnits(response.getPayload().getHomeTroops());
                    }
                    else{
                        controller.showError(response.getMessage());
                    }
        });
    }
    public void loadDetailLog(ArmyLog log){
        controller.showError("Start load detail log");
        UseCaseHandler.execute(getLogDetail, new GetLogDetail.RequestValues(log), response -> {
            if (response.isSuccess()) {
                controller.showLogDetail(response.getPayload().getArmyLogDetail());
            } else {
                controller.showError(response.getMessage());
            }
        });
    }
    public void loadBattleField(){
        controller.showBattleField(house);
    }



    public void loadLogs(){
        UseCaseHandler.execute(loadLogs, new LoadLogs.RequestValues(house.getParty()), response -> {
            if(response.isSuccess()){
                controller.showLogs(response.getPayload().getLogs());
            }
            else{
                controller.showError(response.getMessage());
            }
        });
    }
}
