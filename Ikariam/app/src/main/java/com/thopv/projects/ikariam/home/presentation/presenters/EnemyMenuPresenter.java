package com.thopv.projects.ikariam.home.presentation.presenters;

import com.thopv.projects.ikariam.UseCaseHandler;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.home.domain.entity.PartyUtils;
import com.thopv.projects.ikariam.home.domain.usecases.ApplyCollosusEffect;
import com.thopv.projects.ikariam.home.domain.usecases.ApplyLoEffect;
import com.thopv.projects.ikariam.home.domain.usecases.ApplyWhaleEffect;
import com.thopv.projects.ikariam.home.domain.usecases.ClearUnits;
import com.thopv.projects.ikariam.home.domain.usecases.GetTroopCount;
import com.thopv.projects.ikariam.home.domain.usecases.LoadEffectStatus;
import com.thopv.projects.ikariam.home.domain.usecases.LoadHomeTroops;
import com.thopv.projects.ikariam.home.domain.usecases.RemoveHouse;
import com.thopv.projects.ikariam.home.domain.usecases.SetupHomeUnits;
import com.thopv.projects.ikariam.home.presentation.contracts.EnemyMenuContract;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by thopv on 11/13/2017.
 */

public class EnemyMenuPresenter implements EnemyMenuContract.Presenter{
    private EnemyMenuContract.Controller controller;
    private House house;
    private GetTroopCount getTroopCount;
    private SetupHomeUnits setupHomeUnits;
    private RemoveHouse removeHouse;
    private ApplyLoEffect applyLoEffect;
    private ApplyWhaleEffect applyWhaleEffect;
    private LoadEffectStatus loadEffectStatus;
    private LoadHomeTroops loadHomeTroops;
    private ClearUnits clearUnits;
    private ApplyCollosusEffect applyCollosusEffect;
    @Inject
    public EnemyMenuPresenter(EnemyMenuContract.Controller controller, House house, GetTroopCount getTroopCount, SetupHomeUnits setupHomeUnits, RemoveHouse removeHouse, ApplyLoEffect applyLoEffect, ApplyWhaleEffect applyWhaleEffect, LoadEffectStatus loadEffectStatus, LoadHomeTroops loadHomeTroops, ClearUnits clearUnits, ApplyCollosusEffect applyCollosusEffect) {
        this.controller = controller;
        this.house = house;
        this.getTroopCount = getTroopCount;
        this.setupHomeUnits = setupHomeUnits;
        this.removeHouse = removeHouse;
        this.applyLoEffect = applyLoEffect;
        this.applyWhaleEffect = applyWhaleEffect;
        this.loadEffectStatus = loadEffectStatus;
        this.loadHomeTroops = loadHomeTroops;
        this.clearUnits = clearUnits;
        this.applyCollosusEffect = applyCollosusEffect;
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
    public void loadHomeTroops() {
        UseCaseHandler.execute(loadHomeTroops, new LoadHomeTroops.RequestValues(PartyUtils.getRedParty()), response -> {
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
    public void applyCollosusEffect() {
        UseCaseHandler.execute(applyCollosusEffect, new ApplyCollosusEffect.RequestValues(), response -> {
            if(response.isSuccess()){
                controller.showError("Apply collosus effect success. Your enemy troop đã tản đi bớt.");
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
}
