package com.thopv.projects.ikariam.data.schema.units.units;

import android.support.annotation.NonNull;

import com.thopv.projects.ikariam.data.schema.units.properties.Coordinate;
import com.thopv.projects.ikariam.data.schema.units.properties.Resources;
import com.thopv.projects.ikariam.data.schema.units.properties.UnitAttributes;

public class Unit {
    private String name;
    private int shipyardLevel;
    private UnitAttributes unitAttributes;
    private Coordinate coordinate;
    private ShipType shipType;
    private int drawable;
    private int smallDrawable;
    public Unit(){

    }
    public void setName(String name) {
        this.name = name;
    }
    public void setShipyardLevel(int shipyardLevel) {
        this.shipyardLevel = shipyardLevel;
    }

    public void setUnitAttributes(UnitAttributes unitAttributes) {
        this.unitAttributes = unitAttributes;
    }

    public int getDrawable() {
        return drawable;
    }

    public Unit(String name, int shipyardLevel, Resources resources, UnitAttributes unitAttributes, ShipType shipType, int drawable, int smallDrawable){
        this.name = name;
        this.shipyardLevel = shipyardLevel;
        this.unitAttributes = unitAttributes;
        this.shipType = shipType;
        this.drawable = drawable;
        this.smallDrawable = smallDrawable;
    }

    public int getSmallDrawable() {
        return smallDrawable;
    }

    public void setSmallDrawable(int smallDrawable) {
        this.smallDrawable = smallDrawable;
    }
    @NonNull
    public UnitAttributes getUnitAttributes(){
        return unitAttributes;
    }
    public int getShipyardLevel(){
        return shipyardLevel;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }
    @Override
    public boolean equals(Object obj) {
       if(obj instanceof Unit)
           return ((Unit) obj).getName().equalsIgnoreCase(getName());
        return super.equals(obj);
    }

}
