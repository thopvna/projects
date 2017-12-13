package com.thopv.projects.ikariam.fight.presentation.presenters;

import com.thopv.projects.ikariam.UseCaseHandler;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.fight.presentation.contracts.UnitsSendContract;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.home.domain.entity.PartyUtils;
import com.thopv.projects.ikariam.home.domain.usecases.LoadHomeTroops;
import com.thopv.projects.ikariam.home.domain.usecases.SendUnits;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by thopv on 11/14/2017.
 */

public class UnitSendPresenter implements UnitsSendContract.Presenter{
    private UnitsSendContract.Controller controller;
    private SendUnits sendUnits;
    private House house;
    private LoadHomeTroops loadHomeTroops;
    public UnitSendPresenter(House house, UnitsSendContract.Controller controller, SendUnits sendUnits, LoadHomeTroops loadHomeTroops) {
        this.house = house;
        this.controller = controller;
        this.sendUnits = sendUnits;
        this.loadHomeTroops = loadHomeTroops;
        UseCaseHandler.execute(loadHomeTroops, new LoadHomeTroops.RequestValues(PartyUtils.getBlueParty()), response -> {
            if(response.isSuccess()){
                List<BaseTroop> baseTroops = new LinkedList<>();
                for(HomeTroop homeTroop : response.getPayload().getHomeTroops()){
                    baseTroops.add(new BaseTroop(homeTroop.getUnitName(), homeTroop.getAmount()));
                }
                controller.showUnits(baseTroops);
            }
        });
    }
    public void sendUnits(long goTime, Map<String, Integer> unitsAmount){
        UseCaseHandler.execute(sendUnits, new SendUnits.RequestValues(unitsAmount, goTime), response -> {
            if(response.isSuccess()){
                controller.showMessage("Thành công, Lính đã bắt đầu di chuyển.");
                controller.cancel();
            }
            else{
                controller.showMessage("Thao tác thất bại." + response.getMessage());
            }
        });
    }
    @Override
    public void sendUnits(long goTime, List<BaseTroop> baseTroops) {
        Map<String, Integer> unitsAmount = new HashMap<>();
        for(BaseTroop inform : baseTroops){
            unitsAmount.put(inform.getUnitName(), inform.getAmount());
        }
        sendUnits(goTime, unitsAmount);
    }
}
