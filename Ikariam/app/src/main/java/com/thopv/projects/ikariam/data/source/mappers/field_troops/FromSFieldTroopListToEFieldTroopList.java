package com.thopv.projects.ikariam.data.source.mappers.field_troops;

import com.thopv.projects.ikariam.data.schema.armies.SFieldTroop;
import com.thopv.projects.ikariam.data.schema.units.properties.Weapon;
import com.thopv.projects.ikariam.data.schema.units.units.Unit;
import com.thopv.projects.ikariam.data.schema.units.units.UnitFactory;
import com.thopv.projects.ikariam.data.source.mappers.Mapper;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FieldTroop;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/21/2017.
 */

public class FromSFieldTroopListToEFieldTroopList implements Mapper<List<SFieldTroop>, List<FieldTroop>> {
    public List<FieldTroop> map(List<SFieldTroop> source) {
        List<FieldTroop> fieldTroops = new LinkedList<>();
        for(SFieldTroop sFieldTroop : source){
            FieldTroop fieldTroop = new FieldTroop.Builder()
                    .setViewId(sFieldTroop.getViewId())
                    .setParty(sFieldTroop.getParty())
                    .setUnitName(sFieldTroop.getUnitName())
                    .setAmount(sFieldTroop.getAmount())
                    .setMaxAmount(sFieldTroop.getMaxAmount())
                    .setCurrentTotalHitpoints(sFieldTroop.getCurrentHitPoints())
                    .setWeapons(getWeapons(sFieldTroop.getUnitName(), sFieldTroop.getCurrentMunitions()))
                    .build();
            if(fieldTroops.contains(fieldTroop)){
                FieldTroop oldFieldTroop = fieldTroops.get(fieldTroops.indexOf(fieldTroop));
                oldFieldTroop.merge(fieldTroop);
            }
            else{
                fieldTroops.add(fieldTroop);
            }
        }
        return fieldTroops;
    }
    private List<Weapon> getWeapons(String unitName, int currentMunitions){
        Unit unit = UnitFactory.getUnit(unitName);
        List<Weapon> weapons = unit.getUnitAttributes().getWeapons();

        weapons.get(0).setMunition(currentMunitions);

        return weapons;
    }
}
