package com.thopv.projects.ikariam.fight.domain.entity.fight;

import android.util.Log;
import android.util.SparseArray;

import com.thopv.projects.ikariam.R;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FieldTroop;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FightStrategy;
import com.thopv.projects.ikariam.fight.domain.entity.fight.resources.AttackableInformHolder;
import com.thopv.projects.ikariam.fight.domain.entity.fight.resources.FieldUtils;

import junit.framework.Assert;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by jlaotsezu on 22/11/2017.
 */

public class SmartFightStrategy implements FightStrategy {
    private Map<Integer, FieldTroop> fieldTroops;
    private Events events;
    private boolean blueFightFirst;
    public interface Events{
        void onCompleted(List<FieldTroop> newFieldTroops);
    }
    public SmartFightStrategy(boolean blueFightFirst, List<FieldTroop> fieldTroopsList, Events events) {
        this.events = events;
        this.blueFightFirst = blueFightFirst;
        fieldTroops = new HashMap<>();
        for(FieldTroop fieldTroop : fieldTroopsList){
            fieldTroops.put(fieldTroop.getViewId(), fieldTroop);
        }
    }

    @Override
    public void fight() {
        List<Integer> firingOrders = blueFightFirst ? FieldUtils.getFiringOrders() : FieldUtils.getReverseFiringOrders();
        for(int index = 0; index < firingOrders.size(); index++){
            int viewId = firingOrders.get(index);
            if(fieldTroops.containsKey(viewId)) {
                FieldTroop attackTroop = fieldTroops.get(viewId);
                List<Integer> targetViewIds = AttackableInformHolder.getAttackableOf(viewId);

                if (targetViewIds == null || targetViewIds.size() == 0) {
                    throw new RuntimeException(getClass().getSimpleName() + "Super error: " + Integer.toHexString(viewId));
                } else /*if (attackTroop.isAlive())*/ {
                    for (int i = 0; i < targetViewIds.size(); i++) {
                        int targetViewId = targetViewIds.get(i);
                        if (targetTroopUnableAttacked(targetViewId)) continue;
                        FieldTroop targetFieldTroop = fieldTroops.get(targetViewId);
                        int restDame = targetFieldTroop.isFighted(attackTroop);
                        boolean hasRestDame = restDame != -1;
                        if (hasRestDame && (i < targetViewIds.size() - 1)) {
                            int nextTargetTroopId = targetViewIds.get(i + 1);
                            if (!targetTroopUnableAttacked(nextTargetTroopId)) {
                                fieldTroops.get(nextTargetTroopId).isFighted(restDame);
                            }
                        }
                        break;
                    }
                }
            }
        }
        if(events != null){
            List<FieldTroop> newFieldTroops = new LinkedList<>();
            for(int viewId: fieldTroops.keySet()){
                newFieldTroops.add(fieldTroops.get(viewId));
            }
            events.onCompleted(newFieldTroops);
        }

    }

    private boolean targetTroopUnableAttacked(int targetViewId) {
        if(!fieldTroops.containsKey(targetViewId)) return true;
        if(fieldTroops.get(targetViewId) == null) return true;
        if(!fieldTroops.get(targetViewId).isAlive()) return true;
        return false;
    }
}
