package com.thopv.projects.ikariam.fight.domain.entity.fight.resources;

import com.thopv.projects.ikariam.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jlaotsezu on 22/11/2017.
 */

class VerticalResources {
    private static List<Integer> supportLeftOne1;
    private static List<Integer> supportLeftTwo1;
    private static List<Integer> supportLeftThree1;

    private static List<Integer> mainLeftThree1;
    private static List<Integer> mainLeftTwo1;
    private static List<Integer> mainLeftOne1;

    private static List<Integer> mainMiddle1;

    private static List<Integer> mainRightOne1;
    private static List<Integer> mainRightTwo1;
    private static List<Integer> mainRightThree1;

    private static List<Integer> supportRightThree1;
    private static List<Integer> supportRightTwo1;
    private static List<Integer> supportRightOne1;

    private static List<Integer> supportLeftOne2;
    private static List<Integer> supportLeftTwo2;
    private static List<Integer> supportLeftThree2;

    private static List<Integer> mainLeftThree2;
    private static List<Integer> mainLeftTwo2;
    private static List<Integer> mainLeftOne2;

    private static List<Integer> mainMiddle2;

    private static List<Integer> mainRightOne2;
    private static List<Integer> mainRightTwo2;
    private static List<Integer> mainRightThree2;

    private static List<Integer> supportRightThree2;
    private static List<Integer> supportRightTwo2;
    private static List<Integer> supportRightOne2;

    private static List<Integer> main1;
    private static List<Integer> supportLeft1;
    private static List<Integer> supportRight1;
    private static List<Integer> main2;
    private static List<Integer> supportLeft2;
    private static List<Integer> supportRight2;

    public static List<Integer> getMain1() {
        if(main1 == null){
            main1 = new LinkedList<>();

            main1.addAll(getMainMiddle1());
            main1.addAll(getMainLeftOne1());
            main1.addAll(getMainRightOne1());
            main1.addAll(getMainLeftTwo1());
            main1.addAll(getMainRightTwo1());
            main1.addAll(getMainLeftThree1());
            main1.addAll(getMainRightThree1());
        }
        return main1;
    }

    public static List<Integer> getSupportLeft1() {
        if(supportLeft1 == null){
            supportLeft1 = new LinkedList<>();
            supportLeft1.addAll(getSupportLeftOne1());
            supportLeft1.addAll(getSupportLeftTwo1());
            supportLeft1.addAll(getSupportLeftThree1());
        }
        return supportLeft1;
    }

    public static List<Integer> getSupportRight1() {
        if(supportRight1 == null){
            supportRight1 = new LinkedList<>();
            supportRight1.addAll(getSupportRightOne1());
            supportRight1.addAll(getSupportRightTwo1());
            supportRight1.addAll(getSupportRightThree1());
        }
        return supportRight1;
    }
    public static List<Integer> getMain2() {
        if(main2 == null){
            main2 = new LinkedList<>();
            main2.addAll(getMainMiddle2());
            main2.addAll(getMainLeftOne2());
            main2.addAll(getMainRightOne2());
            main2.addAll(getMainLeftTwo2());
            main2.addAll(getMainRightTwo2());
            main2.addAll(getMainLeftThree2());
            main2.addAll(getMainRightThree2());
        }
        return main2;
    }

    public static List<Integer> getSupportLeft2() {
        if(supportLeft2 == null){
            supportLeft2 = new LinkedList<>();
            supportLeft2.addAll(getSupportLeftOne2());
            supportLeft2.addAll(getSupportLeftTwo2());
            supportLeft2.addAll(getSupportLeftThree2());
        }
        return supportLeft2;
    }

    public static List<Integer> getSupportRight2() {
        if(supportRight2 == null){
            supportRight2 = new LinkedList<>();
            supportRight2.addAll(getSupportRightOne2());
            supportRight2.addAll(getSupportRightTwo2());
            supportRight2.addAll(getSupportRightThree2());
        }
        return supportRight2;
    }

    public static List<Integer> getSupportLeftOne1() {

        if(supportLeftOne1 == null){
            supportLeftOne1 = new LinkedList<>();
            supportLeftOne1.add(R.id.top_support_left_one1);
            supportLeftOne1.add(R.id.bottom_support_left_one1);
        }
        return supportLeftOne1;
    }

    public static List<Integer> getSupportLeftTwo1() {
        if(supportLeftTwo1 == null){
            supportLeftTwo1 = new LinkedList<>();
            supportLeftTwo1.add(R.id.top_support_left_two1);
            supportLeftTwo1.add(R.id.bottom_support_left_two1);
        }
        return supportLeftTwo1;
    }

    public static List<Integer> getSupportLeftThree1() {
        if(supportLeftThree1 == null){
            supportLeftThree1 = new LinkedList<>();
            supportLeftThree1.add(R.id.top_support_left_three1);
        }
        return supportLeftThree1;
    }

    public static List<Integer> getMainLeftThree1() {
        if(mainLeftThree1 == null){
            mainLeftThree1 = new LinkedList<>();
            mainLeftThree1.add(R.id.top_left_three1);
            mainLeftThree1.add(R.id.middle_left_three1);
        }
        return mainLeftThree1;
    }

    public static List<Integer> getMainLeftTwo1() {
        if(mainLeftTwo1 == null){
            mainLeftTwo1 = new LinkedList<>();
            mainLeftTwo1.add(R.id.top_left_two1);
            mainLeftTwo1.add(R.id.middle_left_two1);
            mainLeftTwo1.add(R.id.bottom_left_two1);
        }
        return mainLeftTwo1;
    }

    public static List<Integer> getMainLeftOne1() {
        if(mainLeftOne1 == null){
            mainLeftOne1 = new LinkedList<>();
            mainLeftOne1.add(R.id.top_left_one1);
            mainLeftOne1.add(R.id.middle_left_one1);
            mainLeftOne1.add(R.id.bottom_left_one1);
        }
        return mainLeftOne1;
    }

    public static List<Integer> getMainMiddle1() {
        if(mainMiddle1 == null){
            mainMiddle1 = new LinkedList<>();
            mainMiddle1.add(R.id.top_middle1);
            mainMiddle1.add(R.id.middle_middle1);
            mainMiddle1.add(R.id.bottom_middle1);
        }
        return mainMiddle1;
    }

    public static List<Integer> getMainRightOne1() {
        if(mainRightOne1 == null){
            mainRightOne1 = new LinkedList<>();
            mainRightOne1.add(R.id.top_right_one1);
            mainRightOne1.add(R.id.middle_right_one1);
            mainRightOne1.add(R.id.bottom_right_one1);
        }
        return mainRightOne1;
    }

    public static List<Integer> getMainRightTwo1() {
        if(mainRightTwo1 == null){
            mainRightTwo1 = new LinkedList<>();
            mainRightTwo1.add(R.id.top_right_two1);
            mainRightTwo1.add(R.id.middle_right_two1);
            mainRightTwo1.add(R.id.bottom_right_two1);
        }
        return mainRightTwo1;
    }

    public static List<Integer> getMainRightThree1() {
        if(mainRightThree1 == null){
            mainRightThree1 = new LinkedList<>();
            mainRightThree1.add(R.id.top_right_three1);
            mainRightThree1.add(R.id.middle_right_three1);
        }
        return mainRightThree1;
    }

    public static List<Integer> getSupportRightThree1() {
        if(supportRightThree1 == null){
            supportRightThree1 = new LinkedList<>();
            supportRightThree1.add(R.id.top_support_right_three1);
        }
        return supportRightThree1;
    }

    public static List<Integer> getSupportRightTwo1() {
        if(supportRightTwo1 == null){
            supportRightTwo1 = new LinkedList<>();
            supportRightTwo1.add(R.id.top_support_right_two1);
            supportRightTwo1.add(R.id.bottom_support_right_two1);
        }
        return supportRightTwo1;
    }

    public static List<Integer> getSupportRightOne1() {
        if(supportRightOne1 == null){
            supportRightOne1 = new LinkedList<>();
            supportRightOne1.add(R.id.top_support_right_one1);
            supportRightOne1.add(R.id.bottom_support_right_one1);
        }
        return supportRightOne1;
    }
    public static List<Integer> getSupportLeftOne2() {
        if(supportLeftOne2 == null){
            supportLeftOne2 = new LinkedList<>();
            supportLeftOne2.add(R.id.top_support_left_one2);
            supportLeftOne2.add(R.id.bottom_support_left_one2);
        }
        return supportLeftOne2;
    }

    public static List<Integer> getSupportLeftTwo2() {
        if(supportLeftTwo2 == null){
            supportLeftTwo2 = new LinkedList<>();
            supportLeftTwo2.add(R.id.top_support_left_two2);
            supportLeftTwo2.add(R.id.bottom_support_left_two2);
        }
        return supportLeftTwo2;
    }

    public static List<Integer> getSupportLeftThree2() {
        if(supportLeftThree2 == null){
            supportLeftThree2 = new LinkedList<>();
            supportLeftThree2.add(R.id.top_support_left_three2);
        }
        return supportLeftThree2;
    }

    public static List<Integer> getMainLeftThree2() {
        if(mainLeftThree2 == null){
            mainLeftThree2 = new LinkedList<>();
            mainLeftThree2.add(R.id.top_left_three2);
            mainLeftThree2.add(R.id.middle_left_three2);
        }
        return mainLeftThree2;
    }

    public static List<Integer> getMainLeftTwo2() {
        if(mainLeftTwo2 == null){
            mainLeftTwo2 = new LinkedList<>();
            mainLeftTwo2.add(R.id.top_left_two2);
            mainLeftTwo2.add(R.id.middle_left_two2);
            mainLeftTwo2.add(R.id.bottom_left_two2);
        }
        return mainLeftTwo2;
    }

    public static List<Integer> getMainLeftOne2() {
        if(mainLeftOne2 == null){
            mainLeftOne2 = new LinkedList<>();
            mainLeftOne2.add(R.id.top_left_one2);
            mainLeftOne2.add(R.id.midle_left_one2);
            mainLeftOne2.add(R.id.bottom_left_one2);
        }
        return mainLeftOne2;
    }

    public static List<Integer> getMainMiddle2() {
        if(mainMiddle2 == null){
            mainMiddle2 = new LinkedList<>();
            mainMiddle2.add(R.id.top_middle2);
            mainMiddle2.add(R.id.middle_middle2);
            mainMiddle2.add(R.id.bottom_middle2);
        }
        return mainMiddle2;
    }

    public static List<Integer> getMainRightOne2() {
        if(mainRightOne2 == null){
            mainRightOne2 = new LinkedList<>();
            mainRightOne2.add(R.id.top_right_one2);
            mainRightOne2.add(R.id.middle_right_one2);
            mainRightOne2.add(R.id.bottom_right_one2);
        }
        return mainRightOne2;
    }

    public static List<Integer> getMainRightTwo2() {
        if(mainRightTwo2 == null){
            mainRightTwo2 = new LinkedList<>();
            mainRightTwo2.add(R.id.top_right_two2);
            mainRightTwo2.add(R.id.middle_right_two2);
            mainRightTwo2.add(R.id.bottom_right_two2);
        }
        return mainRightTwo2;
    }

    public static List<Integer> getMainRightThree2() {
        if(mainRightThree2 == null){
            mainRightThree2 = new LinkedList<>();
            mainRightThree2.add(R.id.top_right_three2);
            mainRightThree2.add(R.id.middle_right_three2);
        }
        return mainRightThree2;
    }

    public static List<Integer> getSupportRightThree2() {
        if(supportRightThree2 == null){
            supportRightThree2 = new LinkedList<>();
            supportRightThree2.add(R.id.top_support_right_three2);
        }
        return supportRightThree2;
    }

    public static List<Integer> getSupportRightTwo2() {
        if(supportRightTwo2 == null){
            supportRightTwo2 = new LinkedList<>();
            supportRightTwo2.add(R.id.top_support_right_two2);
            supportRightTwo2.add(R.id.bottom_support_right_two2);
        }
        return supportRightTwo2;
    }

    public static List<Integer> getSupportRightOne2() {
        if(supportRightOne2 == null){
            supportRightOne2 = new LinkedList<>();
            supportRightOne2.add(R.id.top_support_right_one2);
            supportRightOne2.add(R.id.bottom_support_right_one2);
        }
        return supportRightOne2;
    }
}
