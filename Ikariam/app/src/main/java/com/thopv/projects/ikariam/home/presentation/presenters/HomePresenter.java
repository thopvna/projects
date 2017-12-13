package com.thopv.projects.ikariam.home.presentation.presenters;

import com.thopv.projects.ikariam.UseCaseHandler;
import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.home.domain.usecases.AddHouse;
import com.thopv.projects.ikariam.home.domain.usecases.GetHouse;
import com.thopv.projects.ikariam.home.domain.usecases.LoadHouses;
import com.thopv.projects.ikariam.home.domain.usecases.SetupHomeUnits;
import com.thopv.projects.ikariam.home.presentation.contracts.HomeContract;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.schema.houses.ModelHouse;
import com.thopv.projects.ikariam.data.schema.houses.HouseImageProvider;
import com.thopv.projects.ikariam.data.schema.houses.Port;
import com.thopv.projects.ikariam.data.schema.houses.Shipyard;
import com.thopv.projects.ikariam.data.schema.houses.TownHall;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by thopv on 11/13/2017.
 */

public class HomePresenter implements HomeContract.Presenter{
    private HomeContract.Controller controller;
    private LoadHouses loadHouses;
    private GetHouse getHouse;
    private AddHouse addHouse;
    private SetupHomeUnits setupHomeUnits;

    public HomePresenter(HomeContract.Controller controller, LoadHouses loadHouses, GetHouse getHouse, AddHouse addHouse, SetupHomeUnits setupHomeUnits) {
        this.controller = controller;
        this.loadHouses = loadHouses;
        this.getHouse = getHouse;
        this.addHouse = addHouse;
        this.setupHomeUnits = setupHomeUnits;
        loadHouses();
    }
    private void loadHouses(){
        UseCaseHandler.execute(loadHouses, new LoadHouses.RequestValues(), response -> {
            if(response.isSuccess())
                showHousesImage(response.getPayload().getHouses());
        });
    }
    private void showHousesImage(List<House> houses){
        if(houses == null) return;
        for(House house : houses){
            int drawableId = HouseImageProvider.getImageDrawableId(house.getParty(), house.getTownHall().getLevel());
            controller.showHouse(house.getParty(), drawableId);
        }
    }
    public void loadHouse(int party){
        UseCaseHandler.execute(getHouse, new GetHouse.RequestValues(party), response -> {
            if(response.isSuccess()){
                House house = response.getPayload().getHouse();
                boolean isBlue = house.isBlue();
                if(isBlue)
                    controller.showMeDialog(house);
                else
                    controller.showEnemyDialog(house);
            }
            else{
                controller.showError(response.getMessage());
                controller.showHouseBuilderDialog(party);
            }
        });
    }


    public void buildHouse(ModelHouse modelHouse) {
        House house = new House.Builder()
                .setCoordinate(modelHouse.getCoordinate())
                .setPort(new Port(modelHouse.getPortLevel()))
                .setShipyard(new Shipyard(modelHouse.getShipyardLevel()))
                .setTownHall(new TownHall(modelHouse.getTownHallLevel()))
                .setParty(modelHouse.getParty())
                .build();
        UseCaseHandler.execute(addHouse, new AddHouse.RequestValues(house), response -> {
            if(response.isSuccess()){
                loadHouses();
            }
            else{
                loadHouses();
                controller.showError("Operation failed. " + response.getMessage());
            }
        });
    }

    public void loadField(House house) {
        controller.showBattleField(house);
    }

    @Override
    public void setupUnits(House house, List<BaseTroop> troopsInform) {
        List<HomeTroop> homeTroops = new ArrayList<>();
        for(BaseTroop inform : troopsInform){
            homeTroops.add(new HomeTroop(house.getParty(), inform.getUnitName(), inform.getAmount()));
        }
        UseCaseHandler.execute(setupHomeUnits, new SetupHomeUnits.RequestValues(homeTroops), response -> {
            if(response.isSuccess()){
                controller.showError("Setup home units success.");
            }
            else{
                controller.showError("Operation failed. " + response.getMessage());
            }
        });
    }
}
