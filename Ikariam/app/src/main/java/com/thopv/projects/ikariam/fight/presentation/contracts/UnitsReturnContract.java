package com.thopv.projects.ikariam.fight.presentation.contracts;

import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import java.util.List;
import java.util.Map;

/**
 * Created by thopv on 11/20/2017.
 */

public class UnitsReturnContract {
    public interface Presenter{
        void returnUnits(List<BaseTroop> troops, long goTime);
    }
    public interface Controller {
        void showHouses(List<House> houses);
        void showMessage(String msg);
        void cancel();
    }
}
