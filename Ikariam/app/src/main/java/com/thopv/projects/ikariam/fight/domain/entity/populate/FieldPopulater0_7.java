package com.thopv.projects.ikariam.fight.domain.entity.populate;

import com.thopv.projects.ikariam.R;

import java.util.Map;

/**
 * Created by thopv on 10/26/2017.
 */

public class FieldPopulater0_7 extends BaseFieldPopulater {
    public Map<Integer, Integer> getFieldSpaces() {
        return SpacesInform.getField0Spaces();
    }

    public int addTopBlueSupportShip(PopulableTroop populableTroop){
        return 0;
    }
    public int addTopBlueShip(PopulableTroop populableTroop){
        int result = 0;
        int amount = populableTroop.getAmount();
        result = addShip(R.id.top_middle1, populableTroop);

        if(result < amount)
            result += addShip(R.id.top_right_one1, populableTroop);
        if(result < amount)
            result += addShip(R.id.top_left_one1, populableTroop);

        return result;
    }
    public int addMiddleBlueShip(PopulableTroop populableTroop){
        int result = 0;
        int amount = populableTroop.getAmount();
        result = addShip(R.id.middle_middle1, populableTroop);

        if(result < amount)
            result += addShip(R.id.middle_right_one1, populableTroop);
        if(result < amount)
            result += addShip(R.id.middle_left_one1, populableTroop);
        return result;
    }
    public int addBottomBlueShip(PopulableTroop populableTroop){
        int result = 0;
        result = addShip(R.id.bottom_middle1, populableTroop);
        return result;
    }
    public int addBottomSupportRightBlueShip(PopulableTroop populableTroop){
        int result = 0;
        result = addShip(R.id.bottom_support_right_one1, populableTroop);
        return result;
    }
    public int addBottomSupportLeftBlueShip(PopulableTroop populableTroop){
        int result = 0;
        result = addShip(R.id.bottom_support_left_one1, populableTroop);
        return result;
    }
    public int addTopRedSupportShip(PopulableTroop populableTroop){
        return 0;
    }
    public int addTopRedShip(PopulableTroop populableTroop){
        int result = 0;
        int amount = populableTroop.getAmount();
        result = addShip(R.id.top_middle2, populableTroop);

        if(result < amount)
            result += addShip(R.id.top_right_one2, populableTroop);
        if(result < amount)
            result += addShip(R.id.top_left_one2, populableTroop);
        return result;
    }
    public int addMiddleRedShip(PopulableTroop populableTroop){
        int result = 0;
        int amount = populableTroop.getAmount();
        result = addShip(R.id.middle_middle2, populableTroop);

        if(result < amount)
            result += addShip(R.id.middle_right_one2, populableTroop);
        if(result < amount)
            result += addShip(R.id.midle_left_one2, populableTroop);

        return result;
    }
    public int addBottomRedShip(PopulableTroop populableTroop){
        int result = 0;
        result = addShip(R.id.bottom_middle2, populableTroop);
        return result;
    }
    public int addBottomSupportRightRedShip(PopulableTroop populableTroop){
        int result = 0;
        result = addShip(R.id.bottom_support_right_one2, populableTroop);
        return result;
    }
    public int addBottomSupportLeftRedShip(PopulableTroop populableTroop){
        int result = 0;
        result = addShip(R.id.bottom_support_left_one2, populableTroop);
        return result;
    }
}
