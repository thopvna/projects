package com.thopv.projects.ikariam.data.schema.units.units;

import android.support.annotation.NonNull;

import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.data.schema.units.properties.Resources;
import com.thopv.projects.ikariam.data.schema.units.properties.UnitAttributes;
import com.thopv.projects.ikariam.data.schema.units.properties.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thopv on 10/11/2017.
 */

public class UnitFactory {
    private static List<Unit> allUnits;
    private static List<String> allUnitsName;
    private static Map<String, Unit> allUnitsMap;
    public static List<Unit> getAllUnits(){
        if(allUnits == null) {
            allUnits = new ArrayList<>();
            allUnits.add(getFireShip());
            allUnits.add(getSteamRam());
            allUnits.add(getRamShip());    allUnits.add(getMortarShip());
            allUnits.add(getCatapultShip());    allUnits.add(getRocketShip());
            allUnits.add(getDivingBoat());    allUnits.add(getPaddleSpeedboat());
            allUnits.add(getBalloonCarrier());
        }
        return allUnits;
    }
    public static List<String> getAllUnitsName(){
        if(allUnitsName == null) {
            allUnitsName = new ArrayList<>();
            allUnitsName.add("FireShip");
            allUnitsName.add("SteamRam");
            allUnitsName.add("RamShip");    allUnitsName.add("MortarShip");
            allUnitsName.add("CatapultShip");    allUnitsName.add("RocketShip");
            allUnitsName.add("DivingBoat");    allUnitsName.add("PaddleSpeedboat");
            allUnitsName.add("BalloonCarrier");
        }
        return allUnitsName;
    }
    public static Map<String, Unit> getAllUnitsMap(){
        if(allUnitsMap == null){
            allUnitsMap = new HashMap<>();
            allUnitsMap.put("FireShip", getFireShip());
            allUnitsMap.put("SteamRam", getSteamRam());
            allUnitsMap.put("RamShip", getRamShip());
            allUnitsMap.put("MortarShip", getMortarShip());
            allUnitsMap.put("CatapultShip", getCatapultShip());
            allUnitsMap.put("RocketShip", getRocketShip());
            allUnitsMap.put("DivingBoat", getDivingBoat());
            allUnitsMap.put("PaddleSpeedboat", getPaddleSpeedboat());
            allUnitsMap.put("BalloonCarrier", getBalloonCarrier());
        }
        return allUnitsMap;
    }
    @NonNull
    public static Unit getUnit(String unitName){
        if(unitName.equalsIgnoreCase("FireShip"))
            return getFireShip  ();
        else if(unitName.equalsIgnoreCase("SteamRam")){
            return getSteamRam  ();
        }
        else if(unitName.equalsIgnoreCase("RamShip")){
            return getRamShip  ();
        }
        else if(unitName.equalsIgnoreCase("MortarShip")){
            return getMortarShip  ();
        }
        else if(unitName.equalsIgnoreCase("CatapultShip")){
            return getCatapultShip  ();
        }
        else if(unitName.equalsIgnoreCase("RocketShip")){
            return getRocketShip  ();
        }
        else if(unitName.equalsIgnoreCase("DivingBoat")){
            return getDivingBoat  ();
        }
        else if(unitName.equalsIgnoreCase("PaddleSpeedboat")){
            return getPaddleSpeedboat  ();
        }
        else if(unitName.equalsIgnoreCase("BalloonCarrier")){
            return getBalloonCarrier();
        }
        return getBalloonCarrier();
    }

    public static Unit getBallistaShip () {
        String name = "BallistaShip";Resources resources = new Resources(6, 180, 160, 50);Weapon weapon = new Weapon("Reinforced Hull", "First Class", 51, 70);
        Weapon weapon2 = new Weapon("Ballista", "Second Class", 28, 50, 7);
        UnitAttributes unitAttributes =
                new UnitAttributes(weapon, 14, 154, 40, 2, "Long Range Battleship, Machine");
        unitAttributes.addWeapon(weapon2);
        
        int shipyardLevel =  3;
        int priority =  0;
        ShipType shipType =  ShipType.LONG_RANGE_FIGHTER;
        int small_drawable = R.drawable.ballista_ship_small
;       int drawable =  com.thopv.projects.ikariam.R.drawable.ballista_ship;
        return new Unit(name, shipyardLevel, resources, unitAttributes, shipType, drawable, small_drawable);
    }

    public static Unit getBalloonCarrier() {
         String name = "BalloonCarrier";
        
        Resources resources = new Resources(8, 700, 700 , 66);
        
            Weapon weapon = new Weapon("Balloon Attack", "First Class", 100, 50, 5);
        UnitAttributes unitAttributes = new
                UnitAttributes(weapon,0, 140, 20, 5, "Carrier Ship, Machine");
        int shipyardLevel =  7;
        int priority =  0;
        ShipType shipType =  ShipType.CARRIER_SHIP;
        
       int small_drawable = R.drawable.balloon_carrier_small
;       int drawable =  com.thopv.projects.ikariam.R.drawable.balloon_carrier;
        return new Unit(name, shipyardLevel, resources, unitAttributes, shipType, drawable, small_drawable);
    }

    public static Unit getCargoShip() {
         String name = "CargoShip";
        Resources resources = new Resources(0, 0, 0, 0 , 0);
        
            Weapon weapon = new Weapon("Unarmored Hull", "First Class", 15, 80);
        UnitAttributes unitAttributes =
                new  UnitAttributes(weapon,0, 30, 60, 1, "Civilian Ship, Machine");
        int shipyardLevel =  9;
        int priority =  0;
        ShipType shipType =  ShipType.SUPPORT_SHIP;
        
       int small_drawable = R.drawable.catapult_ship_small
;       int drawable =  R.drawable.cargo_ship;
        return new Unit(name, shipyardLevel, resources, unitAttributes, shipType, drawable, small_drawable);
    }

    public static Unit getCatapultShip() {
         String name = "CatapultShip";
        Resources resources = new Resources(5, 180, 140 , 50);
        
            Weapon weapon = new Weapon("Oil Jug", "First Class", 45, 30, 6);
            Weapon weapon2 = new Weapon("Hull", "Second Class", 31, 70);
            UnitAttributes unitAttributes =
                    new UnitAttributes(weapon,10, 148, 40, 3, "Long Range Battleship, Machine");
            unitAttributes.addWeapon(weapon2);
           
        int shipyardLevel =  3;
        int priority =  1;
        ShipType shipType =  ShipType.LONG_RANGE_FIGHTER;
        
       int small_drawable = R.drawable.catapult_ship_small
;       int drawable =  R.drawable.catapult_ship;
        return new Unit(name, shipyardLevel, resources, unitAttributes, shipType, drawable, small_drawable);
    }

    public static Unit getDivingBoat() {
         String name = "DivingBoat";
        Resources resources = new Resources(3, 160, 750, 100 , 60);
        
            Weapon weapon = new Weapon("Floating Bomb", "First Class", 123, 80, 4);
            Weapon weapon2 = new Weapon("Reinforce Hull", "Second Class", 90, 80);
            UnitAttributes unitAttributes =
                    new UnitAttributes(weapon,6, 110, 40, 3, "First Strike, Machine");
            unitAttributes.addWeapon(weapon2);
           
        int shipyardLevel =  19;
        int priority =  1;
        ShipType shipType =  ShipType.FIRST_STRIKE_SHIP;
        
       int small_drawable = R.drawable.diving_boat_small
;       int drawable =  R.drawable.diving_boat;
        return new Unit(name, shipyardLevel, resources, unitAttributes, shipType, drawable, small_drawable);
    }

    public static Unit getFireShip() {
         String name = "FireShip";
      Resources resources = new Resources(4, 80, 230 , 30);
        
            Weapon weapon = new Weapon("Flame Thrower", "First Class", 82, 50);
        UnitAttributes unitAttributes = new
                UnitAttributes(weapon,8, 219, 40, 2, "Heavy Battleship, Machine");
        int shipyardLevel =  4;
        int priority =  0;
        ShipType shipType =  ShipType.HEAVY_BATTLESHIP;
        
       int small_drawable = R.drawable.fire_ship_small
;       int drawable =  R.drawable.fire_ship;
        return new Unit(name, shipyardLevel, resources, unitAttributes, shipType, drawable, small_drawable);
    }

    public static Unit getMortarShip() {
         String name = "MortarShip";Resources resources = new Resources(5, 220, 900 , 50);
            Weapon weapon = new Weapon("Projectile", "First Class", 69, 10, 5);
            Weapon weapon2 = new Weapon("Hull", "Second Class", 37, 50);
            UnitAttributes unitAttributes =
                    new UnitAttributes(weapon,6, 112, 30, 4, "Long Range Battleship, Machine");
            unitAttributes.addWeapon(weapon2);
        
        int shipyardLevel =  17;
        
        int priority =  0;
        ShipType shipType =  ShipType.LONG_RANGE_FIGHTER;
        
       int small_drawable = R.drawable.mortar_ship_small
;       int drawable =  R.drawable.mortar_ship;
        return new Unit(name, shipyardLevel, resources, unitAttributes, shipType, drawable, small_drawable);
    }

    public static Unit getPaddleSpeedboat() {
         String name = "PaddleSpeedboat";
       Resources resources = new Resources(1, 40, 280 , 30);
        
            Weapon weapon = new Weapon("Ballista", "First Class", 12, 90, 5);
        UnitAttributes unitAttributes = new
                UnitAttributes(weapon,0, 20, 60, 1, "Carrier Ship Evasion, Machine");
        int shipyardLevel =  13;
        int priority =  0;
        ShipType shipType =  ShipType.CARRIER_SHIP_EVASION_SHIP;
        
       int small_drawable = R.drawable.paddle_speedboat_small
;       int drawable =  R.drawable.paddle_speedboat;
        return new Unit(name, shipyardLevel, resources, unitAttributes, shipType, drawable, small_drawable);
    }

    public static Unit getRamShip() {
         String name = "RamShip";
        
        Resources resources = new Resources(3, 250, 0 , 40);
        
            Weapon weapon = new Weapon("Ram Bow", "First Class", 75, 80);
        UnitAttributes unitAttributes = new
                UnitAttributes(weapon,9, 154, 40, 3, "Light Battleship, Machine");
        int shipyardLevel =  1;
        int priority =  2;
        ShipType shipType =  ShipType.LIGHT_BATTLESHIP;
        
       int small_drawable = R.drawable.ram_ship_small
;       int drawable =  R.drawable.ram_ship;
        return new Unit(name, shipyardLevel, resources, unitAttributes, shipType, drawable, small_drawable);
    }

    public static Unit getRocketShip() {
         String name = "RocketShip";
        
        Resources resources = new Resources(2, 200, 1200 , 60);
        
            Weapon weapon = new Weapon("Rocket", "First Class", 380, 20, 2);
            UnitAttributes unitAttributes = new
                    UnitAttributes(weapon,6, 65, 30, 4, "First Strike, Machine");

        int shipyardLevel =  11;
        int priority =  0;
        ShipType shipType =  ShipType.FIRST_STRIKE_SHIP;
        
       int small_drawable = R.drawable.rocket_ship_small
;       int drawable =  R.drawable.rocket_ship;
        return new Unit(name, shipyardLevel, resources, unitAttributes, shipType, drawable, small_drawable);
    }

    public static Unit getSteamRam () {
         String name = "SteamRam";
        
        Resources resources = new Resources(2,400 , 800, 40);
        UnitAttributes unitAttributes = new    UnitAttributes(
                new Weapon("Massive Ram", "First Class", 166, 90),
                16, 576, 40, 5, "Heavy Battleship, Machine");
        int shipyardLevel =  15;
        int priority =  1;
        ShipType shipType =  ShipType.HEAVY_BATTLESHIP;
        
       int small_drawable = R.drawable.steam_ram_small
;       int drawable =  R.drawable.steam_ram;
        return new Unit(name, shipyardLevel, resources, unitAttributes, shipType, drawable, small_drawable); 
    }

    public static Unit getTender() {
         String name = "Tender";
      
        
        Resources resources = new Resources(7, 300, 250, 250 , 40);
        
            Weapon weapon = new Weapon("Caulking Hammer", "First Class", 15, 50);
         UnitAttributes unitAttributes = new
                 UnitAttributes(weapon,0, 140, 30, 6, "Support, Machine");
        int shipyardLevel =  9;
        int priority =  0;
        ShipType shipType =  ShipType.SUPPORT_SHIP;
        
       int small_drawable = R.drawable.steam_ram_small
;       int drawable =  R.drawable.steam_ram;
        
       return new Unit(name, shipyardLevel, resources, unitAttributes, shipType, drawable, small_drawable);
    }
}
