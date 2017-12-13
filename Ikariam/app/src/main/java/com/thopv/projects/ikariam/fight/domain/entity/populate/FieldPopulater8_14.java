package com.thopv.projects.ikariam.fight.domain.entity.populate;

import com.thopv.projects.ikariam.R;

import java.util.Map;

/**
 * Created by thopv on 10/26/2017.
 */

public class FieldPopulater8_14 extends FieldPopulater0_7 {
    @Override
    public int addTopBlueSupportShip(PopulableTroop populableTroop) {
        int result =  super.addTopBlueSupportShip(populableTroop);

        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.top_support_right_one1, populableTroop);
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.top_support_left_one1, populableTroop);


        return result;
    }

    @Override
    public int addTopBlueShip(PopulableTroop populableTroop) {
        int result = super.addTopBlueShip(populableTroop);

        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.top_right_two1, populableTroop);
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.top_left_two1, populableTroop);

        return result;
    }

    @Override
    public int addMiddleBlueShip(PopulableTroop populableTroop) {
        int result = super.addMiddleBlueShip(populableTroop);

        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.middle_right_two1, populableTroop);
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.middle_left_two1, populableTroop);

        return result;
    }

    @Override
    public int addBottomBlueShip(PopulableTroop populableTroop) {
        int result = super.addBottomBlueShip(populableTroop);
        
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.bottom_right_one1, populableTroop);
        return result;
    }

    @Override
    public int addBottomSupportRightBlueShip(PopulableTroop populableTroop) {
        return super.addBottomSupportRightBlueShip(populableTroop);
    }

    @Override
    public int addBottomSupportLeftBlueShip(PopulableTroop populableTroop) {
        return super.addBottomSupportLeftBlueShip(populableTroop);
    }

    @Override
    public int addTopRedSupportShip(PopulableTroop populableTroop) {
        int result =  super.addTopRedSupportShip(populableTroop);


        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.top_support_right_one2, populableTroop);
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.top_support_left_one2, populableTroop);

        return result;
    }

    @Override
    public int addTopRedShip(PopulableTroop populableTroop) {
        int result = super.addTopRedShip(populableTroop);


        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.top_right_two2, populableTroop);
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.top_left_two2, populableTroop);

        return result;
    }
    @Override
    public Map<Integer, Integer> getFieldSpaces(){
        return SpacesInform.getField8Spaces();
    }
    @Override
    public int addMiddleRedShip(PopulableTroop populableTroop) {
        int result = super.addMiddleRedShip(populableTroop);


        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.middle_right_two2, populableTroop);
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.middle_left_two2, populableTroop);

        return result;
    }

    @Override
    public int addBottomRedShip(PopulableTroop populableTroop) {
        int result = super.addBottomRedShip(populableTroop);
        
        if(populableTroop.getAmount() > 0)
            result += addShip(R.id.bottom_right_one2, populableTroop);
        return result;
    }

    @Override
    public int addBottomSupportRightRedShip(PopulableTroop populableTroop) {
        return super.addBottomSupportRightRedShip(populableTroop);
    }

    @Override
    public int addBottomSupportLeftRedShip(PopulableTroop populableTroop) {
        return super.addBottomSupportLeftRedShip(populableTroop);
    }
}
