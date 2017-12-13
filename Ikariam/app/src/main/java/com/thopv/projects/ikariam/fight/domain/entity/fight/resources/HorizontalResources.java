package com.thopv.projects.ikariam.fight.domain.entity.fight.resources;

import com.thopv.projects.ikariam.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jlaotsezu on 22/11/2017.
 */

class HorizontalResources {
    private static List<Integer> topSupportLeft1;
    private static List<Integer> topSupportRight1;
    private static List<Integer> topMain1;

    private static List<Integer> middleMain1;

    private static List<Integer> bottomSupportLeft1;
    private static List<Integer> bottomSupportRight1;
    private static List<Integer> bottomMain1;

    private static List<Integer> topSupportLeft2;
    private static List<Integer> topSupportRight2;
    private static List<Integer> topMain2;

    private static List<Integer> middleMain2;

    private static List<Integer> bottomSupportLeft2;
    private static List<Integer> bottomSupportRight2;
    private static List<Integer> bottomMain2;

    private static List<Integer> main1;
    private static List<Integer> reverseMain1;
    private static List<Integer> main2;
    private static List<Integer> reverseMain2;

    public static List<Integer> getReverseMain1() {
        if(reverseMain1 == null){
            reverseMain1 = new LinkedList<>();

            reverseMain1.addAll(getMiddleMain1());
            reverseMain1.addAll(getBottomMain1());
            reverseMain1.addAll(getTopMain1());
        }
        return reverseMain1;
    }

    public static List<Integer> getReverseMain2() {
        if(reverseMain2 == null){
            reverseMain2 = new LinkedList<>();

            reverseMain2.addAll(getMiddleMain2());
            reverseMain2.addAll(getBottomMain2());
            reverseMain2.addAll(getTopMain2());
        }
        return reverseMain2;
    }

    public static List<Integer> getTopSupportLeft1() {
        if(topSupportLeft1 == null){
            topSupportLeft1 = new LinkedList<>();
            topSupportLeft1.add(R.id.top_support_left_one1);
            topSupportLeft1.add(R.id.top_support_left_two1);
            topSupportLeft1.add(R.id.top_support_left_three1);
        }
        return topSupportLeft1;
    }

    public static List<Integer> getTopSupportRight1() {
        if(topSupportRight1 == null){
            topSupportRight1 = new LinkedList<>();
            topSupportRight1.add(R.id.top_support_right_one1);
            topSupportRight1.add(R.id.top_support_right_two1);
            topSupportRight1.add(R.id.top_support_right_three1);
        }
        return topSupportRight1;
    }

    public static List<Integer> getTopMain1() {
        if(topMain1 == null){
            topMain1 = new LinkedList<>();
            topMain1.add(R.id.top_left_three1);
            topMain1.add(R.id.top_left_two1);
            topMain1.add(R.id.top_left_one1);
            topMain1.add(R.id.top_middle1);
            topMain1.add(R.id.top_right_one1);
            topMain1.add(R.id.top_right_two1);
            topMain1.add(R.id.top_right_three1);
        }
        return topMain1;
    }
    private static List<Integer> topMainReverse1;
    public static List<Integer> getTopMainReverse1() {
        if(topMainReverse1 == null){
            topMainReverse1 = new LinkedList<>();

            topMainReverse1.add(R.id.top_right_one1);
            topMainReverse1.add(R.id.top_right_two1);
            topMainReverse1.add(R.id.top_right_three1);
            topMainReverse1.add(R.id.top_middle1);
            topMainReverse1.add(R.id.top_left_three1);
            topMainReverse1.add(R.id.top_left_two1);
            topMainReverse1.add(R.id.top_left_one1);
        }
        return topMainReverse1;
    }

    public static List<Integer> getMiddleMain1() {
        if(middleMain1 == null){
            middleMain1 = new LinkedList<>();
            middleMain1.add(R.id.middle_left_three1);
            middleMain1.add(R.id.middle_left_two1);
            middleMain1.add(R.id.middle_left_one1);
            middleMain1.add(R.id.middle_middle1);
            middleMain1.add(R.id.middle_right_one1);
            middleMain1.add(R.id.middle_right_two1);
            middleMain1.add(R.id.middle_right_three1);
        }
        return middleMain1;
    }
    private static List<Integer> reverseMiddleMain1;
    public static List<Integer> getReverseMiddleMain1() {
        if(reverseMiddleMain1 == null){
            reverseMiddleMain1 = new LinkedList<>();

            reverseMiddleMain1.add(R.id.middle_right_one1);
            reverseMiddleMain1.add(R.id.middle_right_two1);
            reverseMiddleMain1.add(R.id.middle_right_three1);
            reverseMiddleMain1.add(R.id.middle_middle1);
            reverseMiddleMain1.add(R.id.middle_left_three1);
            reverseMiddleMain1.add(R.id.middle_left_two1);
            reverseMiddleMain1.add(R.id.middle_left_one1);
        }
        return reverseMiddleMain1;
    }

    public static List<Integer> getBottomSupportLeft1() {
        if(bottomSupportLeft1 == null){
            bottomSupportLeft1 = new LinkedList<>();
            bottomSupportLeft1.add(R.id.bottom_support_left_one1);
            bottomSupportLeft1.add(R.id.bottom_support_left_two1);
        }
        return bottomSupportLeft1;
    }

    public static List<Integer> getBottomSupportRight1() {
        if(bottomSupportRight1 == null){
            bottomSupportRight1 = new LinkedList<>();
            bottomSupportRight1.add(R.id.bottom_support_right_two1);
            bottomSupportRight1.add(R.id.bottom_support_right_one1);
        }
        return bottomSupportRight1;
    }

    public static List<Integer> getBottomMain1() {
        if(bottomMain1 == null){
            bottomMain1 = new LinkedList<>();
            bottomMain1.add(R.id.bottom_left_two1);
            bottomMain1.add(R.id.bottom_left_one1);
            bottomMain1.add(R.id.bottom_middle1);
            bottomMain1.add(R.id.bottom_right_one1);
            bottomMain1.add(R.id.bottom_right_two1);
        }
        return bottomMain1;
    }
    public static List<Integer> getTopSupportLeft2() {
        if(topSupportLeft2 == null){
            topSupportLeft2 = new LinkedList<>();
            topSupportLeft2.add(R.id.top_support_left_one2);
            topSupportLeft2.add(R.id.top_support_left_two2);
            topSupportLeft2.add(R.id.top_support_left_three2);
        }
        return topSupportLeft2;
    }

    public static List<Integer> getTopSupportRight2() {
        if(topSupportRight2 == null){
            topSupportRight2 = new LinkedList<>();
            topSupportRight2.add(R.id.top_support_right_one2);
            topSupportRight2.add(R.id.top_support_right_two2);
            topSupportRight2.add(R.id.top_support_right_three2);
        }
        return topSupportRight2;
    }

    public static List<Integer> getTopMain2() {
        if(topMain2 == null){
            topMain2 = new LinkedList<>();
            topMain2.add(R.id.top_left_three2);
            topMain2.add(R.id.top_left_two2);
            topMain2.add(R.id.top_left_one2);
            topMain2.add(R.id.top_middle2);
            topMain2.add(R.id.top_right_one2);
            topMain2.add(R.id.top_right_two2);
            topMain2.add(R.id.top_right_three2);
        }
        return topMain2;
    }
    private static List<Integer> topMainReverse2;
    public static List<Integer> getTopMainReverse2() {
        if(topMainReverse2 == null){
            topMainReverse2 = new LinkedList<>();
            topMainReverse2.add(R.id.top_right_one2);
            topMainReverse2.add(R.id.top_right_two2);
            topMainReverse2.add(R.id.top_right_three2);
            topMainReverse2.add(R.id.top_middle2);
            topMainReverse2.add(R.id.top_left_three2);
            topMainReverse2.add(R.id.top_left_two2);
            topMainReverse2.add(R.id.top_left_one2);
        }
        return topMainReverse2;
    }

    public static List<Integer> getMiddleMain2() {
        if(middleMain2 == null){
            middleMain2 = new LinkedList<>();
            middleMain2.add(R.id.middle_left_three2);
            middleMain2.add(R.id.middle_left_two2);
            middleMain2.add(R.id.midle_left_one2);
            middleMain2.add(R.id.middle_middle2);
            middleMain2.add(R.id.middle_right_one2);
            middleMain2.add(R.id.middle_right_two2);
            middleMain2.add(R.id.middle_right_three2);
        }
        return middleMain2;
    }
    private static List<Integer> reverseMiddleMain2;
    public static List<Integer> getReverseMiddleMain2() {
        if(reverseMiddleMain2 == null){
            reverseMiddleMain2 = new LinkedList<>();
            reverseMiddleMain2.add(R.id.middle_right_one2);
            reverseMiddleMain2.add(R.id.middle_right_two2);
            reverseMiddleMain2.add(R.id.middle_right_three2);
            reverseMiddleMain2.add(R.id.middle_middle2);
            reverseMiddleMain2.add(R.id.middle_left_three2);
            reverseMiddleMain2.add(R.id.middle_left_two2);
            reverseMiddleMain2.add(R.id.midle_left_one2);
        }
        return reverseMiddleMain2;
    }

    public static List<Integer> getBottomSupportLeft2() {
        if(bottomSupportLeft2 == null){
            bottomSupportLeft2 = new LinkedList<>();
            bottomSupportLeft2.add(R.id.bottom_support_left_one2);
            bottomSupportLeft2.add(R.id.bottom_support_left_two2);
        }
        return bottomSupportLeft2;
    }

    public static List<Integer> getBottomSupportRight2() {
        if(bottomSupportRight2 == null){
            bottomSupportRight2 = new LinkedList<>();
            bottomSupportRight2.add(R.id.bottom_support_right_two2);
            bottomSupportRight2.add(R.id.bottom_support_right_one2);
        }
        return bottomSupportRight2;
    }

    public static List<Integer> getBottomMain2() {
        if(bottomMain2 == null){
            bottomMain2 = new LinkedList<>();
            bottomMain2.add(R.id.bottom_left_two2);
            bottomMain2.add(R.id.bottom_left_one2);
            bottomMain2.add(R.id.bottom_middle2);
            bottomMain2.add(R.id.bottom_right_one2);
            bottomMain2.add(R.id.bottom_right_two2);
        }
        return bottomMain2;
    }
    private static List<Integer> bottomMain2Reverse;
    public static List<Integer> getBottomMain2Reverse() {
        if(bottomMain2Reverse == null){
            bottomMain2Reverse = new LinkedList<>();
            bottomMain2Reverse.add(R.id.bottom_right_two2);
            bottomMain2Reverse.add(R.id.bottom_right_one2);

            bottomMain2Reverse.add(R.id.bottom_middle2);
            bottomMain2Reverse.add(R.id.bottom_left_one2);
            bottomMain2Reverse.add(R.id.bottom_left_two2);
        }
        return bottomMain2Reverse;
    }
    private static List<Integer> bottomMain1Reverse;
    public static List<Integer> getBottomMain1Reverse() {
        if(bottomMain1Reverse == null){
            bottomMain1Reverse = new LinkedList<>();
            bottomMain1Reverse.add(R.id.bottom_right_two1);
            bottomMain1Reverse.add(R.id.bottom_right_one1);

            bottomMain1Reverse.add(R.id.bottom_middle1);
            bottomMain1Reverse.add(R.id.bottom_left_one1);
            bottomMain1Reverse.add(R.id.bottom_left_two1);
        }
        return bottomMain1Reverse;
    }
}
