package com.thopv.projects.ikariam.data.schema.houses;

import com.thopv.projects.ikariam.R;

/**
 * Created by thopv on 10/11/2017.
 */

public class HouseImageProvider {
    public static int getMeImageDrawableId(int townHallLevel){
        if( townHallLevel >= 18){
                return R.drawable.town_blue_8;
        }
        else if(townHallLevel >= 16){

                return R.drawable.town_blue_7;

        }
        else if(townHallLevel >= 13){

                return R.drawable.town_blue_6;

        }
        else if(townHallLevel >= 10){

                return R.drawable.town_blue_5;

        }
        else if(townHallLevel >= 7){

                return R.drawable.town_blue_4;

        }
        else if(townHallLevel >= 4){

                return R.drawable.town_blue_3;

        }
        else if(townHallLevel >= 2){

                return R.drawable.town_blue_2;

        }
        else {

                return R.drawable.town_blue_1;

        }
    }
    public static int getImageDrawableByCompareParty(int yourParty, int himParty, int townHalLLevel){
        if(yourParty == himParty)
            return getImageDrawableId(0, townHalLLevel);
        else
            return getImageDrawableId(1, townHalLLevel);
    }
    public static int getImageDrawableId(int party, int townHallLevel){
        if( townHallLevel >= 18){
            if(party == 0)
                return R.drawable.town_green_8;
            else
                return R.drawable.town_red_8;
        }
        else if(townHallLevel >= 16){
            if(party == 0)
                return R.drawable.town_green_7;
            else
                return R.drawable.town_red_7;
        }
        else if(townHallLevel >= 13){
            if(party == 0)
                return R.drawable.town_green_6;
            else
                return R.drawable.town_red_6;
        }
        else if(townHallLevel >= 10){
            if(party == 0)
                return R.drawable.town_green_5;
            else
                return R.drawable.town_red_5;
        }
        else if(townHallLevel >= 7){
            if(party == 0)
                return R.drawable.town_green_4;
            else
                return R.drawable.town_red_4;
        }
        else if(townHallLevel >= 4){
            if(party == 0)
                return R.drawable.town_green_3;
            else
                return R.drawable.town_red_3;
        }
        else if(townHallLevel >= 2){
            if(party == 0)
                return R.drawable.town_green_2;
            else
                return R.drawable.town_red_2;
        }
        else {
            if(party == 0)
                return R.drawable.town_green_1;
            else
                return R.drawable.town_red_1;
        }
    }
}
