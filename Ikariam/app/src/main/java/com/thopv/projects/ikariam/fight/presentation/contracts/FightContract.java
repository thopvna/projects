package com.thopv.projects.ikariam.fight.presentation.contracts;

import android.arch.lifecycle.LifecycleOwner;

import com.thopv.projects.ikariam.data.schema.armies.HomeTroop;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;
import com.thopv.projects.ikariam.home.presentation.models.ModelAttackTroop;
import com.thopv.projects.ikariam.home.presentation.models.ModelFieldTroop;
import com.thopv.projects.ikariam.data.schema.houses.House;

import java.util.List;
import java.util.Map;

/**
 * Created by thopv on 11/20/2017.
 */

public class FightContract {
    public interface Presenter{
        void sendUnits();
        void returnUnits(Map<String, Integer> unitsAmount, long goTime, long before);
        void returnUnits();
        void changeFightTimeDistance(long newDistance);
    }
    public  interface Controller extends LifecycleOwner {
        void showAttackOptionsMenu();
        void showError(String err);
        void hideAttackOptionsMenu();
        void hideDefOptionsMenu();
        void showFightStatus(boolean isFighting, long startTime, int turn);
        void showFightingTroops(List<ModelFieldTroop> troop);
        void showAttackReverseTroops(List<BaseTroop> troops);
        void showHomeTroops(List<BaseTroop> homeTroops);
        void showExtraAttackReverseTroops(List<BaseTroop> troops);
        void showExtraHomeTroops(List<BaseTroop> homeTroops);
        void showUnitsSend(House house);
        void showUnitsReturn(List<BaseTroop> troops);
        void showDefLoEffect();
        void showAttackLoEffect();
    }
}
