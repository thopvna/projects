package com.thopv.projects.ikariam.home.presentation.contracts;

import android.arch.lifecycle.LifecycleOwner;

import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.schema.houses.ModelHouse;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import java.util.List;

/**
 * Created by thopv on 11/20/2017.
 */

public class HomeContract {

    public interface Presenter{
        void loadHouse(int party);
        void buildHouse(ModelHouse modelHouse);
        void loadField(House house);
        void setupUnits(House house, List<BaseTroop> troops);
    }

    /**
     * Created by thopv on 11/13/2017.
     */

    public interface Controller extends LifecycleOwner {
        void showError(String err);
        void showHouse(int party, int drawble);
        void showHouseBuilderDialog(int party);
        void showMeDialog(House house);
        void showEnemyDialog(House house);
        void showBattleField(House house);
    }
}
