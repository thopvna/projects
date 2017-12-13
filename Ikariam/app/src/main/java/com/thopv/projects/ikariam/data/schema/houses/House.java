package com.thopv.projects.ikariam.data.schema.houses;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.util.Log;
import com.thopv.projects.ikariam.data.schema.units.properties.Coordinate;

/**
 * Created by thopv on 10/11/2017.
 */
@Entity
public class House {
    @PrimaryKey
    private int party;
    @Embedded(prefix = "townHall")
    private TownHall townHall;
    @Embedded(prefix = "shipyard")
    private Shipyard shipyard;
    @Embedded(prefix = "port")
    private Port port;
    @Embedded(prefix = "coordinate")
    private Coordinate coordinate;
    public House(){

    }

    public static class Builder{
        private TownHall townHall;
        private Shipyard shipyard;
        private int party;
        private Port port;
        private Coordinate coordinate;

        public Builder(){
            party = 0;
        }
        public House build(){
            House house = new House();
            house.townHall = townHall;
            house.shipyard = shipyard;
            house.party = party;
            house.port = port;
            house.coordinate = coordinate;
            return house;
        }

        public Builder setTownHall(TownHall townHall) {
            this.townHall = townHall;
            return this;
        }

        public Builder setShipyard(Shipyard shipyard) {
            this.shipyard = shipyard;
            return this;
        }

        public Builder setParty(int party) {
            this.party = party;
            return this;
        }

        public Builder setPort(Port port) {
            this.port = port;
            return this;
        }

        public Builder setCoordinate(Coordinate coordinate) {
            this.coordinate = coordinate;
            return this;
        }

    }
    public int getMeDrawableId(){
        return HouseImageProvider.getMeImageDrawableId(townHall.getLevel());
    }

    public void setTownHall(TownHall townHall) {
        this.townHall = townHall;
    }

    public void setShipyard(Shipyard shipyard) {
        this.shipyard = shipyard;
    }

    public void setParty(int party) {
        this.party = party;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public TownHall getTownHall() {
        return townHall;
    }

    public Shipyard getShipyard() {
        return shipyard;
    }

    public int getParty() {
        return party;
    }
    public int getCounterParty(){
        return party == 0 ? 1 : 0;
    }
    public Port getPort() {
        return port;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
    public boolean isBlue(){
        return this.party == 0;
    }
}
