package com.thopv.projects.ikariam.fight.domain.entity.populate;


import android.util.Log;

import com.thopv.projects.ikariam.fight.domain.entity.fight.FieldTroop;
import com.thopv.projects.ikariam.data.schema.units.units.Unit;
import com.thopv.projects.ikariam.data.schema.units.units.UnitFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * Created by thopv on 11/1/2017.
 */

/**
 * Nhiệm vụ là populate units vào các view và gọi update
 */

/**
 * Khi populate. phe loadField luôn nằm ở bên trên và phe thủ luôn nằm ở dưới
 * Nhưng khi hiển thị ra thì còn tùy.
 */
public abstract class BaseFieldPopulater{
    private Map<Integer, FieldTroop> mFieldTroops;

    private interface InnerPopulatedCallback {
        void onPopulated(Map<Integer, FieldTroop> newInFieldTroops, Map<String, PopulableTroop> newHomePopulableTroops, Map<String, PopulableTroop> newAttackPopulableTroops);
    }
    public interface PopulatedCallback {
        void onPopulated(List<FieldTroop> newInFieldTroops, List<PopulableTroop> newHomePopulableTroops, List<PopulableTroop> newAttackPopulableTroops);
    }
    public interface PopulateFailCallback{
        void onFailed();
    }
    private interface InnerPopulateFailCallback {
        void onFailed();
    }
    public void populate(List<FieldTroop> fieldTroops, List<PopulableTroop> homePopulableTroops, List<PopulableTroop> attackPopulableTroops, PopulatedCallback callback, PopulateFailCallback failCallback){
        Map<Integer, FieldTroop> mapFieldTroops = new HashMap<>();
        Map<String, PopulableTroop> homePopulableTroopsMap = new HashMap<>();
        Map<String, PopulableTroop> attackPopulableTroopsMap = new HashMap<>();

        if(fieldTroops == null) fieldTroops = new LinkedList<>();
        if(homePopulableTroops == null) homePopulableTroops = new LinkedList<>();
        if(attackPopulableTroops == null) attackPopulableTroops = new LinkedList<>();

        for(FieldTroop fieldTroop : fieldTroops){
            mapFieldTroops.put(fieldTroop.getViewId(), fieldTroop);
        }
        for(PopulableTroop populableTroop : homePopulableTroops){
            homePopulableTroopsMap.put(populableTroop.getUnitName(), populableTroop);
        }
        for(PopulableTroop populableTroop : attackPopulableTroops){
            attackPopulableTroopsMap.put(populableTroop.getUnitName(), populableTroop);
        }

        populate(mapFieldTroops, homePopulableTroopsMap, attackPopulableTroopsMap, (newInFieldTroopsMap, newHomePopulableTroopsMap, newAttackPopulableTroopsMap) -> {
            List<FieldTroop> newFieldTroops = new LinkedList<>();
            List<PopulableTroop> newHomePopulableTroops = new LinkedList<>();
            List<PopulableTroop> newAttackPopulableTroops = new LinkedList<>();
            for(int viewId : newInFieldTroopsMap.keySet()){
                newFieldTroops.add(newInFieldTroopsMap.get(viewId));
            }
            for(String unitName : newHomePopulableTroopsMap.keySet()){
                newHomePopulableTroops.add(newHomePopulableTroopsMap.get(unitName));
            }
            for(String unitName : newAttackPopulableTroopsMap.keySet()){
                newAttackPopulableTroops.add(newAttackPopulableTroopsMap.get(unitName));
            }

            callback.onPopulated(newFieldTroops, newHomePopulableTroops, newAttackPopulableTroops);
        }, failCallback::onFailed);

    }
    public void populate(Map<Integer, FieldTroop> inFieldTroops, Map<String, PopulableTroop> homePopulableTroops, Map<String, PopulableTroop> attackPopulableTroops, InnerPopulatedCallback callback, InnerPopulateFailCallback failCallback){
        if(inFieldTroops == null)
            inFieldTroops = new HashMap<>();
        if(homePopulableTroops == null)
            homePopulableTroops = new HashMap<>();
        if(attackPopulableTroops == null)
            attackPopulableTroops = new HashMap<>();

        int attackAmounts = populate(inFieldTroops, attackPopulableTroops, Position.TREN);
        int defAmounts = populate(inFieldTroops, homePopulableTroops, Position.DUOI);
        
        if(attackAmounts + defAmounts > 0) {
            callback.onPopulated(mFieldTroops, homePopulableTroops, attackPopulableTroops);
        }
        else{
            failCallback.onFailed();
        }
    }
    public enum Position{
        TREN,
        DUOI
    }
    //Complex function
    private int populate(Map<Integer, FieldTroop> inFieldTroops,
                         Map<String, PopulableTroop> populableTroops,
                         Position position) {
        mFieldTroops = inFieldTroops;
        int totalAdditionAmount = 0;
        if (position == Position.TREN) {
            if (populableTroops.containsKey("FireShip")) {
                Unit unit = UnitFactory.getUnit("FireShip");
                int daThem =  addTopBlueShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }

            if (populableTroops.containsKey("SteamRam")) {
                Unit unit = UnitFactory.getUnit("SteamRam");
                int daThem =  addTopBlueShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }

            if (populableTroops.containsKey("RamShip")) {
                Unit unit = UnitFactory.getUnit("RamShip");
                int amount = populableTroops.get(unit.getName()).getAmount();
                int daThem =  addTopBlueShip(populableTroops.get(unit.getName()));
                if (daThem < amount) {
                    daThem +=  addTopBlueSupportShip(populableTroops.get(unit.getName()));
                    if(daThem > 0){
                        totalAdditionAmount += daThem;
                    }
                }
            }
            if (populableTroops.containsKey("MortarShip")) {
                Unit unit = UnitFactory.getUnit("MortarShip");
                int daThem =  addMiddleBlueShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }

            if (populableTroops.containsKey("CatapultShip")) {
                Unit unit = UnitFactory.getUnit("CatapultShip");
                int daThem =  addMiddleBlueShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }

            if (populableTroops.containsKey("RocketShip")) {
                Unit unit = UnitFactory.getUnit("RocketShip");
                int daThem =  addBottomBlueShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }

            if (populableTroops.containsKey("DivingBoat")) {
                Unit unit = UnitFactory.getUnit("DivingBoat");
                int daThem =  addBottomBlueShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }


            if (populableTroops.containsKey("BalloonCarrier")) {
                Unit unit = UnitFactory.getUnit("BalloonCarrier");
                int daThem =  addBottomSupportLeftBlueShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }


            if (populableTroops.containsKey("PaddleSpeedboat")) {
                Unit unit = UnitFactory.getUnit("PaddleSpeedboat");
                int daThem =  addBottomSupportRightBlueShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }
        } else {
            if (populableTroops.containsKey("FireShip")) {
                Unit unit = UnitFactory.getUnit("FireShip");
                int daThem =  addTopRedShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }


            if (populableTroops.containsKey("SteamRam")) {
                Unit unit = UnitFactory.getUnit("SteamRam");
                int daThem =  addTopRedShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }


            if (populableTroops.containsKey("RamShip")) {
                Unit unit = UnitFactory.getUnit("RamShip");
                int amount = populableTroops.get(unit.getName()).getAmount();
                int daThem =  addTopRedShip(populableTroops.get(unit.getName()));
                if (daThem < amount) {
                    daThem +=  addTopRedSupportShip(populableTroops.get(unit.getName()));
                    if(daThem > 0){
                        totalAdditionAmount += daThem;
                    }
                }
            }


            if (populableTroops.containsKey("MortarShip")) {
                Unit unit = UnitFactory.getUnit("MortarShip");
                int daThem =  addMiddleRedShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }


            if (populableTroops.containsKey("CatapultShip")) {
                Unit unit = UnitFactory.getUnit("CatapultShip");
                int daThem =  addMiddleRedShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }

            if (populableTroops.containsKey("RocketShip")) {
                Unit unit = UnitFactory.getUnit("RocketShip");
                int daThem =  addBottomRedShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }

            if (populableTroops.containsKey("DivingBoat")) {
                Unit unit = UnitFactory.getUnit("DivingBoat");
                int daThem =  addBottomRedShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }

            if (populableTroops.containsKey("BalloonCarrier")) {
                Unit unit = UnitFactory.getUnit("BalloonCarrier");
                int daThem =  addBottomSupportLeftRedShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }

            if (populableTroops.containsKey("PaddleSpeedboat")) {
                Unit unit = UnitFactory.getUnit("PaddleSpeedboat");
                int daThem =  addBottomSupportRightRedShip(populableTroops.get(unit.getName()));
                if(daThem > 0){
                    totalAdditionAmount += daThem;
                }
            }
        }

        return totalAdditionAmount;
    }
    synchronized int addShip(int viewId, PopulableTroop populableTroop){
        int amount = populableTroop.getAmount();
        if(amount == 0)
            return 0;
        Unit unit = UnitFactory.getUnit(populableTroop.getUnitName());
        int maxAmount = getFieldSpaces().get(viewId) / unit.getUnitAttributes().getSize();
        FieldTroop fieldTroop = mFieldTroops.get(viewId);
        //TODO: Cân nhắc thêm biến amount có thể thêm khi mà cùng UnitName và còn chỗ trống
        if(fieldTroop != null && fieldTroop.isAlive()) {
            return 0;
        }
        else{
            if(fieldTroop == null){
               // Log.e(getClass().getSimpleName(),"Field Troop is null.");

            }
            else if(!fieldTroop.isAlive()){
               // Log.e(getClass().getSimpleName(),"Field Troop is die.");
            }
        }
        int amountSeThem = 0;
        
        if(maxAmount > amount){
            amountSeThem = amount;
        }
        else if(maxAmount <= amount){
            amountSeThem = maxAmount;
        }

        if(amountSeThem > 0){
            int subtracted = populableTroop.subtractAmount(amountSeThem);
            fieldTroop = new FieldTroop(viewId, unit.getName(), populableTroop.getParty(), subtracted, maxAmount);
            mFieldTroops.put(viewId, fieldTroop);
        }
        return amountSeThem;
    }
    protected abstract Map<Integer, Integer> getFieldSpaces();
    protected abstract int addTopBlueShip(PopulableTroop populableTroop);
    protected abstract int addTopBlueSupportShip(PopulableTroop populableTroop);
    protected abstract int addMiddleBlueShip(PopulableTroop populableTroop);
    protected abstract int addBottomBlueShip(PopulableTroop populableTroop);
    protected abstract int addBottomSupportLeftBlueShip(PopulableTroop populableTroop);
    protected abstract int addBottomSupportRightBlueShip(PopulableTroop populableTroop);
    protected abstract int addTopRedShip(PopulableTroop populableTroop);
    protected abstract int addMiddleRedShip(PopulableTroop populableTroop);
    protected abstract int addBottomRedShip(PopulableTroop populableTroop);
    protected abstract int addBottomSupportLeftRedShip(PopulableTroop populableTroop);
    protected abstract int addBottomSupportRightRedShip(PopulableTroop populableTroop);
    protected abstract int addTopRedSupportShip(PopulableTroop populableTroop);
}
