package com.thopv.projects.ikariam.data.schema.houses;

import com.thopv.projects.ikariam.data.schema.units.units.Unit;
import com.thopv.projects.ikariam.data.schema.units.properties.Coordinate;

import java.util.Map;

/**
 * Created by thopv on 10/11/2017.
 */

public class ModelHouse {
    private int party;
    private int shipyardLevel;
    private int townHallLevel;
    private Map<Unit, Integer> unitsAmount;
    private int portLevel;
    private Coordinate coordinate;
    public ModelHouse(){

    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public ModelHouse(int shipyardLevel, int portLevel, int townHallLevel, Coordinate coordinate, int party) {
        this.shipyardLevel = shipyardLevel;
        this.townHallLevel = townHallLevel;
        this.portLevel = portLevel;
        this.coordinate = coordinate;
        this.party = party;
    }


    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getPortLevel() {
        return portLevel;
    }

    public int getShipyardLevel() {
        return shipyardLevel;
    }

    public void setShipyardLevel(int shipyardLevel) {
        this.shipyardLevel = shipyardLevel;
    }

    public int getTownHallLevel() {
        return townHallLevel;
    }

    public void setTownHallLevel(int townHallLevel) {
        this.townHallLevel = townHallLevel;
    }

    public int getParty() {
        return party;
    }

    public void setParty(int party) {
        this.party = party;
    }

    public Map<Unit, Integer> getUnitsAmount() {
        return unitsAmount;
    }

    public void setUnitsAmount(Map<Unit, Integer> unitsAmount) {
        this.unitsAmount = unitsAmount;
    }
}
