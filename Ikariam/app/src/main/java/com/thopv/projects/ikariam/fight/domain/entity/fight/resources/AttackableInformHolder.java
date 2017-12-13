package com.thopv.projects.ikariam.fight.domain.entity.fight.resources;

import com.thopv.projects.ikariam.R;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by jlaotsezu on 22/11/2017.
 */

public class AttackableInformHolder {
    private static Map<Integer, List<Integer>> attackableListMap;
    private static List<Integer> supportTopLeftOne1;
    private static List<Integer> supportTopLeftTwo1;
    private static List<Integer> supportTopLeftThree1;
    private static List<Integer> topLeftThree1;
    private static List<Integer> topLeftTwo1;
    private static List<Integer> topLeftOne1;
    private static List<Integer> topMiddle1;
    private static List<Integer> topRightOne1;
    private static List<Integer> topRightTwo1;
    private static List<Integer> topRightThree1;
    private static List<Integer> supportTopRightThree1;
    private static List<Integer> supportTopRightTwo1;
    private static List<Integer> supportTopRightOne1;
    private static List<Integer> middleLeftThree1;
    private static List<Integer> middleLeftTwo1;
    private static List<Integer> middleLeftOne1;
    private static List<Integer> middleMiddle1;
    private static List<Integer> middleRightOne1;
    private static List<Integer> middleRightTwo1;
    private static List<Integer> middleRightThree1;
    private static List<Integer> supportBottomLeftOne1;
    private static List<Integer> supportBottomLeftTwo1;
    private static List<Integer> bottomLeftTwo1;
    private static List<Integer> bottomLeftOne1;
    private static List<Integer> bottomMiddle1;
    private static List<Integer> bottomRightOne1;
    private static List<Integer> bottomRightTwo1;
    private static List<Integer> supportBottomRightTwo1;
    private static List<Integer> supportBottomRightOne1;

    private static List<Integer> supportTopLeftOne2;
    private static List<Integer> supportTopLeftTwo2;
    private static List<Integer> supportTopLeftThree2;
    private static List<Integer> topLeftThree2;
    private static List<Integer> topLeftTwo2;
    private static List<Integer> topLeftOne2;
    private static List<Integer> topMiddle2;
    private static List<Integer> topRightOne2;
    private static List<Integer> topRightTwo2;
    private static List<Integer> topRightThree2;
    private static List<Integer> supportTopRightThree2;
    private static List<Integer> supportTopRightTwo2;
    private static List<Integer> supportTopRightOne2;
    private static List<Integer> middleLeftThree2;
    private static List<Integer> middleLeftTwo2;
    private static List<Integer> middleLeftOne2;
    private static List<Integer> middleMiddle2;
    private static List<Integer> middleRightOne2;
    private static List<Integer> middleRightTwo2;
    private static List<Integer> middleRightThree2;
    private static List<Integer> supportBottomLeftOne2;
    private static List<Integer> supportBottomLeftTwo2;
    private static List<Integer> bottomLeftTwo2;
    private static List<Integer> bottomLeftOne2;
    private static List<Integer> bottomMiddle2;
    private static List<Integer> bottomRightOne2;
    private static List<Integer> bottomRightTwo2;
    private static List<Integer> supportBottomRightTwo2;
    private static List<Integer> supportBottomRightOne2;

    public static List<Integer> getAttackableOf(int viewId){
        if(attackableListMap == null){
            attackableListMap = new HashMap<>();
            attackableListMap.put(R.id.top_support_left_one1, getAttackableOfSupportTopLeftOne1());
            attackableListMap.put(R.id.top_support_left_two1, getAttackableOfSupportTopLeftTwo1());
            attackableListMap.put(R.id.top_support_left_three1, getAttackableOfSupportTopLeftThree1());
            attackableListMap.put(R.id.top_left_three1, getAttackableOfTopLeftThree1());
            attackableListMap.put(R.id.top_left_two1, getAttackableOfTopLeftTwo1());
            attackableListMap.put(R.id.top_left_one1, getAttackableOfTopLeftOne1());
            attackableListMap.put(R.id.top_middle1, getAttackableOfTopMiddle1());
            attackableListMap.put(R.id.top_right_one1, getAttackableOfTopRightOne1());
            attackableListMap.put(R.id.top_right_two1, getAttackableOfTopRightTwo1());
            attackableListMap.put(R.id.top_right_three1, getAttackableOfTopRightThree1());
            attackableListMap.put(R.id.top_support_right_three1, getAttackableOfSupportTopRightThree1());
            attackableListMap.put(R.id.top_support_right_two1, getAttackableOfSupportTopRightTwo1());
            attackableListMap.put(R.id.top_support_right_one1, getAttackableOfSupportTopRightOne1());
            attackableListMap.put(R.id.middle_left_three1, getAttackableOfMiddleLeftThree1());
            attackableListMap.put(R.id.middle_left_two1, getAttackableOfMiddleLeftTwo1());
            attackableListMap.put(R.id.middle_left_one1, getAttackableOfMiddleLeftOne1());
            attackableListMap.put(R.id.middle_middle1, getAttackableOfMiddleMiddle1());
            attackableListMap.put(R.id.middle_right_one1, getAttackableOfMiddleRightOne1());
            attackableListMap.put(R.id.middle_right_two1, getAttackableOfMiddleRightTwo1());
            attackableListMap.put(R.id.middle_right_three1, getAttackableOfMiddleRightThree1());
            attackableListMap.put(R.id.bottom_support_left_one1, getAttackableOfSupportBottomLeftOne1());
            attackableListMap.put(R.id.bottom_support_left_two1, getAttackableOfSupportBottomLeftTwo1());
            attackableListMap.put(R.id.bottom_left_two1, getAttackableOfBottomLeftTwo1());
            attackableListMap.put(R.id.bottom_left_one1, getAttackableOfBottomLeftOne1());
            attackableListMap.put(R.id.bottom_middle1, getAttackableOfBottomMiddle1());
            attackableListMap.put(R.id.bottom_right_one1, getAttackableOfBottomRightOne1());
            attackableListMap.put(R.id.bottom_right_two1, getAttackableOfBottomRightTwo1());
            attackableListMap.put(R.id.bottom_support_right_two1, getAttackableOfSupportBottomRightTwo1());
            attackableListMap.put(R.id.bottom_support_right_one1, getAttackableOfSupportBottomRightOne1());
            
            attackableListMap.put(R.id.top_support_left_one2, getAttackableOfSupportTopLeftOne2());
            attackableListMap.put(R.id.top_support_left_two2, getAttackableOfSupportTopLeftTwo2());
            attackableListMap.put(R.id.top_support_left_three2, getAttackableOfSupportTopLeftThree2());
            attackableListMap.put(R.id.top_left_three2, getAttackableOfTopLeftThree2());
            attackableListMap.put(R.id.top_left_two2, getAttackableOfTopLeftTwo2());
            attackableListMap.put(R.id.top_left_one2, getAttackableOfTopLeftOne2());
            attackableListMap.put(R.id.top_middle2, getAttackableOfTopMiddle2());
            attackableListMap.put(R.id.top_right_one2, getAttackableOfTopRightOne2());
            attackableListMap.put(R.id.top_right_two2, getAttackableOfTopRightTwo2());
            attackableListMap.put(R.id.top_right_three2, getAttackableOfTopRightThree2());
            attackableListMap.put(R.id.top_support_right_three2, getAttackableOfSupportTopRightThree2());
            attackableListMap.put(R.id.top_support_right_two2, getAttackableOfSupportTopRightTwo2());
            attackableListMap.put(R.id.top_support_right_one2, getAttackableOfSupportTopRightOne2());
            attackableListMap.put(R.id.middle_left_three2, getAttackableOfMiddleLeftThree2());
            attackableListMap.put(R.id.middle_left_two2, getAttackableOfMiddleLeftTwo2());
            attackableListMap.put(R.id.midle_left_one2, getAttackableOfMiddleLeftOne2());
            attackableListMap.put(R.id.middle_middle2, getAttackableOfMiddleMiddle2());
            attackableListMap.put(R.id.middle_right_one2, getAttackableOfMiddleRightOne2());
            attackableListMap.put(R.id.middle_right_two2, getAttackableOfMiddleRightTwo2());
            attackableListMap.put(R.id.middle_right_three2, getAttackableOfMiddleRightThree2());
            attackableListMap.put(R.id.bottom_support_left_one2, getAttackableOfSupportBottomLeftOne2());
            attackableListMap.put(R.id.bottom_support_left_two2, getAttackableOfSupportBottomLeftTwo2());
            attackableListMap.put(R.id.bottom_left_two2, getAttackableOfBottomLeftTwo2());
            attackableListMap.put(R.id.bottom_left_one2, getAttackableOfBottomLeftOne2());
            attackableListMap.put(R.id.bottom_middle2, getAttackableOfBottomMiddle2());
            attackableListMap.put(R.id.bottom_right_one2, getAttackableOfBottomRightOne2());
            attackableListMap.put(R.id.bottom_right_two2, getAttackableOfBottomRightTwo2());
            attackableListMap.put(R.id.bottom_support_right_two2, getAttackableOfSupportBottomRightTwo2());
            attackableListMap.put(R.id.bottom_support_right_one2, getAttackableOfSupportBottomRightOne2());
        }
        return attackableListMap.get(viewId);
    }
    private static List<Integer> getAttackableOfSupportTopLeftOne1(){
        if(supportTopLeftOne1 == null) {
            supportTopLeftOne1 = new LinkedList<>();

            supportTopLeftOne1.add(R.id.top_support_left_one2);
            supportTopLeftOne1.addAll(HorizontalResources.getTopSupportLeft2());
            supportTopLeftOne1.addAll(HorizontalResources.getReverseMain2());
        }
        return supportTopLeftOne1;
    }
    private static List<Integer> getAttackableOfSupportTopLeftTwo1(){
        if(supportTopLeftTwo1 == null) {
            supportTopLeftTwo1 = new LinkedList<>();

            supportTopLeftTwo1.add(R.id.top_support_left_two2);
            supportTopLeftTwo1.addAll(HorizontalResources.getTopSupportLeft2());
            supportTopLeftTwo1.addAll(HorizontalResources.getReverseMain2());
        }
        return supportTopLeftTwo1;
    }
    private static List<Integer> getAttackableOfSupportTopLeftThree1(){
        if(supportTopLeftThree1 == null) {
            supportTopLeftThree1 = new LinkedList<>();

            supportTopLeftThree1.add(R.id.top_support_left_three2);
            supportTopLeftThree1.addAll(HorizontalResources.getTopSupportLeft2());
            supportTopLeftThree1.addAll(HorizontalResources.getReverseMain2());
        }
        return supportTopLeftThree1;
    }
    private static List<Integer> getAttackableOfTopLeftThree1(){
        if(topLeftThree1 == null) {
            topLeftThree1 = new LinkedList<>();
            topLeftThree1.add(R.id.top_left_three2);
            topLeftThree1.addAll(HorizontalResources.getTopMain2());
        }
        return topLeftThree1;
    }
    private static List<Integer> getAttackableOfTopLeftTwo1(){
        if(topLeftTwo1 == null){
            topLeftTwo1 = new LinkedList<>();
            topLeftTwo1.add(R.id.top_left_two2);
            topLeftTwo1.addAll(HorizontalResources.getTopMain2());
        }
        return topLeftTwo1;
    }
    private static List<Integer> getAttackableOfTopLeftOne1(){
        if(topLeftOne1 == null){
            topLeftOne1 = new LinkedList<>();
            topLeftOne1.add(R.id.top_left_one2);
            topLeftOne1.addAll(HorizontalResources.getTopMain2());
            }


        return topLeftOne1;
    }
    private static List<Integer> getAttackableOfTopMiddle1(){
        if(topMiddle1 == null){
            topMiddle1 = new LinkedList<>();
            topMiddle1.add(R.id.top_middle2);
            topMiddle1.addAll(HorizontalResources.getTopMain2());
        }
        return topMiddle1;
    }
    private static List<Integer> getAttackableOfTopRightOne1(){
        if(topRightOne1 == null){
            topRightOne1 = new LinkedList<>();
            topRightOne1.add(R.id.top_right_one2);
            topRightOne1.addAll(HorizontalResources.getTopMainReverse2());
        }
        return topRightOne1;
    }
    private static List<Integer> getAttackableOfTopRightTwo1(){
        if(topRightTwo1 == null){
            topRightTwo1 = new LinkedList<>();
            topRightTwo1.add(R.id.top_right_two2);
            topRightTwo1.addAll(HorizontalResources.getTopMainReverse2());
        }
        return topRightTwo1;
    }
    private static List<Integer> getAttackableOfTopRightThree1(){
        if(topRightThree1 == null){
            topRightThree1 = new LinkedList<>();
            topRightThree1.add(R.id.top_right_three2);
            topRightThree1.addAll(HorizontalResources.getTopMainReverse2());
        }
        return topRightThree1;
    }
    private static List<Integer> getAttackableOfSupportTopRightThree1(){
        if(supportTopRightThree1 == null){
            supportTopRightThree1 = new LinkedList<>();
            supportTopRightThree1.add(R.id.top_support_right_three2);
            supportTopRightThree1.addAll(HorizontalResources.getTopSupportRight2());
            supportTopRightThree1.addAll(HorizontalResources.getReverseMain2());
        }

        return supportTopRightThree1;
    }
    private static List<Integer> getAttackableOfSupportTopRightTwo1(){
        if(supportTopRightTwo1 == null){
            supportTopRightTwo1 = new LinkedList<>();
            supportTopRightTwo1.add(R.id.top_support_right_two2);
            supportTopRightTwo1.addAll(HorizontalResources.getTopSupportRight2());
            supportTopRightTwo1.addAll(HorizontalResources.getReverseMain2());
        }
        
        return supportTopRightTwo1;
    }
    private static List<Integer> getAttackableOfSupportTopRightOne1(){
        if(supportTopRightOne1 == null){
            supportTopRightOne1 = new LinkedList<>();
            supportTopRightOne1.add(R.id.top_support_right_one2);
            supportTopRightOne1.addAll(HorizontalResources.getTopSupportRight2());
            supportTopRightOne1.addAll(HorizontalResources.getReverseMain2());
        }
        return supportTopRightOne1;
    }
    private static List<Integer> getAttackableOfMiddleLeftThree1(){
        if(middleLeftThree1 == null){
            middleLeftThree1 = new LinkedList<>();
            middleLeftThree1.add(R.id.top_left_three2);
            middleLeftThree1.addAll(HorizontalResources.getTopMain2());
            middleLeftThree1.add(R.id.middle_left_three2);
            middleLeftThree1.addAll(HorizontalResources.getMiddleMain2());
            }

        return middleLeftThree1;
    }
    private static List<Integer> getAttackableOfMiddleLeftTwo1(){
        if(middleLeftTwo1 == null){
            middleLeftTwo1 = new LinkedList<>();
            middleLeftTwo1.add(R.id.top_left_two2);
            middleLeftTwo1.addAll(HorizontalResources.getTopMain2());
            middleLeftTwo1.add(R.id.middle_left_two2);
            middleLeftTwo1.addAll(HorizontalResources.getMiddleMain2());
            }

        
        return middleLeftTwo1;
    }
    private static List<Integer> getAttackableOfMiddleLeftOne1(){
        if(middleLeftOne1 == null){
            middleLeftOne1 = new LinkedList<>();
            middleLeftOne1.add(R.id.top_left_one2);
            middleLeftOne1.addAll(HorizontalResources.getTopMain2());
            middleLeftOne1.add(R.id.midle_left_one2);
            middleLeftOne1.addAll(HorizontalResources.getMiddleMain2());
            }
        
        return middleLeftOne1;
    }
    private static List<Integer> getAttackableOfMiddleMiddle1(){
        if(middleMiddle1 == null){
            middleMiddle1 = new LinkedList<>();
            middleMiddle1.add(R.id.top_middle2);
            middleMiddle1.addAll(HorizontalResources.getTopMain2());
            middleMiddle1.add(R.id.middle_middle2);
            middleMiddle1.addAll(HorizontalResources.getMiddleMain2());
            }
        
        return middleMiddle1;
    }
    private static List<Integer> getAttackableOfMiddleRightOne1(){
        if(middleRightOne1 == null){
            middleRightOne1 = new LinkedList<>();
            middleRightOne1.add(R.id.top_right_one2);
            middleRightOne1.addAll(HorizontalResources.getTopMain2());
            middleRightOne1.add(R.id.middle_right_one2);
            middleRightOne1.addAll(HorizontalResources.getReverseMiddleMain2());
            }
        
        return middleRightOne1;
    }
    private static List<Integer> getAttackableOfMiddleRightTwo1(){
        if(middleRightTwo1 == null){
            middleRightTwo1 = new LinkedList<>();
            middleRightTwo1.add(R.id.top_right_two2);
            middleRightTwo1.addAll(HorizontalResources.getTopMain2());
            middleRightTwo1.add(R.id.middle_right_two2);
            middleRightTwo1.addAll(HorizontalResources.getReverseMiddleMain2());
            }
        
        return middleRightTwo1;
    }
    private static List<Integer> getAttackableOfMiddleRightThree1(){
        if(middleRightThree1 == null){
            middleRightThree1 = new LinkedList<>();
            middleRightThree1.add(R.id.top_right_three2);
            middleRightThree1.addAll(HorizontalResources.getTopMain2());
            middleRightThree1.add(R.id.middle_right_three2);
            middleRightThree1.addAll(HorizontalResources.getReverseMiddleMain2());
            }
        
        return middleRightThree1;
    }
    private static List<Integer> getAttackableOfSupportBottomLeftOne1(){
        if(supportBottomLeftOne1 == null){
            supportBottomLeftOne1 = new LinkedList<>();
            supportBottomLeftOne1.addAll(HorizontalResources.getBottomMain2());
            supportBottomLeftOne1.addAll(HorizontalResources.getMiddleMain2());
            supportBottomLeftOne1.addAll(HorizontalResources.getTopMain2());
            supportBottomLeftOne1.addAll(HorizontalResources.getTopSupportLeft2());
            supportBottomLeftOne1.addAll(HorizontalResources.getTopSupportRight2());
        }
        
        return supportBottomLeftOne1;
    }
    private static List<Integer> getAttackableOfSupportBottomLeftTwo1(){
        if(supportBottomLeftTwo1 == null){
            supportBottomLeftTwo1 = new LinkedList<>();
            supportBottomLeftTwo1.addAll(HorizontalResources.getBottomMain2());
            supportBottomLeftTwo1.addAll(HorizontalResources.getMiddleMain2());
            supportBottomLeftTwo1.addAll(HorizontalResources.getTopMain2());
            supportBottomLeftTwo1.addAll(HorizontalResources.getTopSupportLeft2());
            supportBottomLeftTwo1.addAll(HorizontalResources.getTopSupportRight2());
            }
        
        return supportBottomLeftTwo1;
    }
    private static List<Integer> getAttackableOfBottomLeftTwo1(){
        if(bottomLeftTwo1 == null){
            bottomLeftTwo1 = new LinkedList<>();
            bottomLeftTwo1.add(R.id.top_left_two2);
        }
        
        return bottomLeftTwo1;
    }
    private static List<Integer> getAttackableOfBottomLeftOne1(){
        if(bottomLeftOne1 == null){
            bottomLeftOne1 = new LinkedList<>();
            bottomLeftOne1.add(R.id.top_left_one2);
        }
        return bottomLeftOne1;
    }
    private static List<Integer> getAttackableOfBottomMiddle1(){
        if(bottomMiddle1 == null){
            bottomMiddle1 = new LinkedList<>();
            bottomMiddle1.add(R.id.top_middle2);
        }
        
        return bottomMiddle1;
    }
    private static List<Integer> getAttackableOfBottomRightOne1(){
        if(bottomRightOne1 == null){
            bottomRightOne1 = new LinkedList<>();
            bottomRightOne1.add(R.id.top_right_one2);
        }
        
        return bottomRightOne1;
    }
    private static List<Integer> getAttackableOfBottomRightTwo1(){
        if(bottomRightTwo1 == null){
            bottomRightTwo1 = new LinkedList<>();
            bottomRightTwo1.add(R.id.top_right_two2);
        }
        
        return bottomRightTwo1;
    }
    private static List<Integer> getAttackableOfSupportBottomRightTwo1(){
        if(supportBottomRightTwo1 == null){
            supportBottomRightTwo1 = new LinkedList<>();
            supportBottomRightTwo1.add(R.id.bottom_support_left_two2);
            supportBottomRightTwo1.addAll(HorizontalResources.getBottomSupportLeft2());
            supportBottomRightTwo1.add(R.id.bottom_support_right_two2);
            supportBottomRightTwo1.addAll(HorizontalResources.getBottomSupportRight2());
        }
        
        return supportBottomRightTwo1;
    }
    private static List<Integer> getAttackableOfSupportBottomRightOne1(){
        if(supportBottomRightOne1 == null){
            supportBottomRightOne1 = new LinkedList<>();
            supportBottomRightOne1.add(R.id.bottom_support_left_one2);
            supportBottomRightOne1.addAll(HorizontalResources.getBottomSupportLeft2());
            supportBottomRightOne1.add(R.id.bottom_support_right_one2);
            supportBottomRightOne1.addAll(HorizontalResources.getBottomSupportRight2());
        }
        
        return supportBottomRightOne1;
    }
    private static List<Integer> getAttackableOfSupportTopLeftOne2(){
        if(supportTopLeftOne2 == null) {
            supportTopLeftOne2 = new LinkedList<>();

            supportTopLeftOne2.add(R.id.top_support_left_one1);
            supportTopLeftOne2.addAll(HorizontalResources.getTopSupportLeft1());
            supportTopLeftOne2.addAll(HorizontalResources.getReverseMain1());
        }
        return supportTopLeftOne2;
    }
    private static List<Integer> getAttackableOfSupportTopLeftTwo2(){
        if(supportTopLeftTwo2 == null) {
            supportTopLeftTwo2 = new LinkedList<>();

            supportTopLeftTwo2.add(R.id.top_support_left_two1);
            supportTopLeftTwo2.addAll(HorizontalResources.getTopSupportLeft1());
            supportTopLeftTwo2.addAll(HorizontalResources.getReverseMain1());
        }
        return supportTopLeftTwo2;
    }
    private static List<Integer> getAttackableOfSupportTopLeftThree2(){
        if(supportTopLeftThree2 == null) {
            supportTopLeftThree2 = new LinkedList<>();

            supportTopLeftThree2.add(R.id.top_support_left_three1);
            supportTopLeftThree2.addAll(HorizontalResources.getTopSupportLeft1());
            supportTopLeftThree2.addAll(HorizontalResources.getReverseMain1());
        }
        return supportTopLeftThree2;
    }
    private static List<Integer> getAttackableOfTopLeftThree2(){
        if(topLeftThree2 == null) {
            topLeftThree2 = new LinkedList<>();
            topLeftThree2.add(R.id.top_left_three1);
            topLeftThree2.addAll(HorizontalResources.getTopMain1());
        }
        return topLeftThree2;
    }
    private static List<Integer> getAttackableOfTopLeftTwo2(){
        if(topLeftTwo2 == null){
            topLeftTwo2 = new LinkedList<>();
            topLeftTwo2.add(R.id.top_left_two1);
            topLeftTwo2.addAll(HorizontalResources.getTopMain1());
        }
        return topLeftTwo2;
    }
    private static List<Integer> getAttackableOfTopLeftOne2(){
        if(topLeftOne2 == null){
            topLeftOne2 = new LinkedList<>();
            topLeftOne2.add(R.id.top_left_one1);
            topLeftOne2.addAll(HorizontalResources.getTopMain1());
        }
        return topLeftOne2;
    }
    private static List<Integer> getAttackableOfTopMiddle2(){
        if(topMiddle2 == null){
            topMiddle2 = new LinkedList<>();
            topMiddle2.add(R.id.top_middle1);
            topMiddle2.addAll(HorizontalResources.getTopMain1());
            }

        
        return topMiddle2;
    }
    private static List<Integer> getAttackableOfTopRightOne2(){
        if(topRightOne2 == null){
            topRightOne2 = new LinkedList<>();
            topRightOne2.add(R.id.top_right_one1);
            topRightOne2.addAll(HorizontalResources.getTopMainReverse1());
        }
        return topRightOne2;
    }
    private static List<Integer> getAttackableOfTopRightTwo2(){
        if(topRightTwo2 == null){
            topRightTwo2 = new LinkedList<>();
            topRightTwo2.add(R.id.top_right_two1);
            topRightTwo2.addAll(HorizontalResources.getTopMainReverse1());
        }
        return topRightTwo2;
    }
    private static List<Integer> getAttackableOfTopRightThree2(){
        if(topRightThree2 == null){
            topRightThree2 = new LinkedList<>();
            topRightThree2.add(R.id.top_right_three1);
            topRightThree2.addAll(HorizontalResources.getTopMainReverse1());
        }
        return topRightThree2;
    }
    private static List<Integer> getAttackableOfSupportTopRightThree2(){
        if(supportTopRightThree2 == null){
            supportTopRightThree2 = new LinkedList<>();
            supportTopRightThree2.add(R.id.top_support_right_three1);
            supportTopRightThree2.addAll(HorizontalResources.getTopSupportRight1());
            supportTopRightThree2.addAll(HorizontalResources.getReverseMain1());
        }

        return supportTopRightThree2;
    }
    private static List<Integer> getAttackableOfSupportTopRightTwo2(){
        if(supportTopRightTwo2 == null){
            supportTopRightTwo2 = new LinkedList<>();
            supportTopRightTwo2.add(R.id.top_support_right_two1);
            supportTopRightTwo2.addAll(HorizontalResources.getTopSupportRight1());
            supportTopRightTwo2.addAll(HorizontalResources.getReverseMain1());
        }
        
        return supportTopRightTwo2;
    }
    private static List<Integer> getAttackableOfSupportTopRightOne2(){
        if(supportTopRightOne2 == null){
            supportTopRightOne2 = new LinkedList<>();
            supportTopRightOne2.add(R.id.top_support_right_one1);
            supportTopRightOne2.addAll(HorizontalResources.getTopSupportRight1());
            supportTopRightOne2.addAll(HorizontalResources.getReverseMain1());
        }
        return supportTopRightOne2;
    }
    private static List<Integer> getAttackableOfSupportBottomLeftOne2(){
        if(supportBottomLeftOne2 == null){
            supportBottomLeftOne2 = new LinkedList<>();
            supportBottomLeftOne2.addAll(HorizontalResources.getBottomMain1());
            supportBottomLeftOne2.addAll(HorizontalResources.getMiddleMain1());
            supportBottomLeftOne2.addAll(HorizontalResources.getTopMain1());
            supportBottomLeftOne2.addAll(HorizontalResources.getTopSupportLeft1());
            supportBottomLeftOne2.addAll(HorizontalResources.getTopSupportRight1());
        }
        
        return supportBottomLeftOne2;
    }
    private static List<Integer> getAttackableOfSupportBottomLeftTwo2(){
        if(supportBottomLeftTwo2 == null){
            supportBottomLeftTwo2 = new LinkedList<>();
            supportBottomLeftTwo2.addAll(HorizontalResources.getBottomMain1());
            supportBottomLeftTwo2.addAll(HorizontalResources.getMiddleMain1());
            supportBottomLeftTwo2.addAll(HorizontalResources.getTopMain1());
            supportBottomLeftTwo2.addAll(HorizontalResources.getTopSupportLeft1());
            supportBottomLeftTwo2.addAll(HorizontalResources.getTopSupportRight1());
            }
        
        return supportBottomLeftTwo2;
    }
    private static List<Integer> getAttackableOfBottomLeftTwo2(){
        if(bottomLeftTwo2 == null){
            bottomLeftTwo2 = new LinkedList<>();
            bottomLeftTwo2.add(R.id.top_left_two1);
        }

        return bottomLeftTwo2;
    }
    private static List<Integer> getAttackableOfBottomLeftOne2(){
        if(bottomLeftOne2 == null){
            bottomLeftOne2 = new LinkedList<>();
            bottomLeftOne2.add(R.id.top_left_one1);
        }

        return bottomLeftOne2;
    }
    private static List<Integer> getAttackableOfBottomMiddle2(){
        if(bottomMiddle2 == null){
            bottomMiddle2 = new LinkedList<>();
            bottomMiddle2.add(R.id.top_middle1);
        }

        return bottomMiddle2;
    }
    private static List<Integer> getAttackableOfBottomRightOne2(){
        if(bottomRightOne2 == null){
            bottomRightOne2 = new LinkedList<>();
            bottomRightOne2.add(R.id.top_right_one1);
        }

        return bottomRightOne2;
    }
    private static List<Integer> getAttackableOfBottomRightTwo2(){
        if(bottomRightTwo2 == null){
            bottomRightTwo2 = new LinkedList<>();
            bottomRightTwo2.add(R.id.top_right_two1);
        }

        return bottomRightTwo2;
    }
    private static List<Integer> getAttackableOfSupportBottomRightTwo2(){
        if(supportBottomRightTwo2 == null){
            supportBottomRightTwo2 = new LinkedList<>();
            supportBottomRightTwo2.add(R.id.bottom_support_left_two1);
            supportBottomRightTwo2.addAll(HorizontalResources.getBottomSupportLeft1());
            supportBottomRightTwo2.add(R.id.bottom_support_right_two1);
            supportBottomRightTwo2.addAll(HorizontalResources.getBottomSupportRight1());
        }
        
        return supportBottomRightTwo2;
    }
    private static List<Integer> getAttackableOfSupportBottomRightOne2(){
        if(supportBottomRightOne2 == null){
            supportBottomRightOne2 = new LinkedList<>();
            supportBottomRightOne2.add(R.id.bottom_support_left_one1);
            supportBottomRightOne2.addAll(HorizontalResources.getBottomSupportLeft1());
            supportBottomRightOne2.add(R.id.bottom_support_left_one1);
            supportBottomRightOne2.addAll(HorizontalResources.getBottomSupportRight1());
        }
        
        return supportBottomRightOne2;
    }

    private static List<Integer> getAttackableOfMiddleLeftThree2(){
        if(middleLeftThree2 == null){
            middleLeftThree2 = new LinkedList<>();
            middleLeftThree2.add(R.id.top_left_three1);
            middleLeftThree2.addAll(HorizontalResources.getTopMain1());
            middleLeftThree2.add(R.id.middle_left_three1);
            middleLeftThree2.addAll(HorizontalResources.getMiddleMain1());
        }

        return middleLeftThree2;
    }
    private static List<Integer> getAttackableOfMiddleLeftTwo2(){
        if(middleLeftTwo2 == null){
            middleLeftTwo2 = new LinkedList<>();
            middleLeftTwo2.add(R.id.top_left_two1);
            middleLeftTwo2.addAll(HorizontalResources.getTopMain1());
            middleLeftTwo2.add(R.id.middle_left_two1);
            middleLeftTwo2.addAll(HorizontalResources.getMiddleMain1());
        }


        return middleLeftTwo2;
    }
    private static List<Integer> getAttackableOfMiddleLeftOne2(){
        if(middleLeftOne2 == null){
            middleLeftOne2 = new LinkedList<>();
            middleLeftOne2.add(R.id.top_left_one1);
            middleLeftOne2.addAll(HorizontalResources.getTopMain1());
            middleLeftOne2.add(R.id.middle_left_one1);
            middleLeftOne2.addAll(HorizontalResources.getMiddleMain1());
        }

        return middleLeftOne2;
    }
    private static List<Integer> getAttackableOfMiddleMiddle2(){
        if(middleMiddle2 == null){
            middleMiddle2 = new LinkedList<>();
            middleMiddle2.add(R.id.top_middle1);
            middleMiddle2.addAll(HorizontalResources.getTopMain1());
            middleMiddle2.add(R.id.middle_middle1);
            middleMiddle2.addAll(HorizontalResources.getMiddleMain1());
        }

        return middleMiddle2;
    }
    private static List<Integer> getAttackableOfMiddleRightOne2(){
        if(middleRightOne2 == null){
            middleRightOne2 = new LinkedList<>();
            middleRightOne2.add(R.id.top_right_one1);
            middleRightOne2.addAll(HorizontalResources.getTopMain1());
            middleRightOne2.add(R.id.middle_right_one1);
            middleRightOne2.addAll(HorizontalResources.getReverseMiddleMain1());
        }

        return middleRightOne2;
    }
    private static List<Integer> getAttackableOfMiddleRightTwo2(){
        if(middleRightTwo2 == null){
            middleRightTwo2 = new LinkedList<>();
            middleRightTwo2.add(R.id.top_right_two1);
            middleRightTwo2.addAll(HorizontalResources.getTopMain1());
            middleRightTwo2.add(R.id.middle_right_two1);
            middleRightTwo2.addAll(HorizontalResources.getReverseMiddleMain1());
        }

        return middleRightTwo2;
    }
    private static List<Integer> getAttackableOfMiddleRightThree2(){
        if(middleRightThree2 == null){
            middleRightThree2 = new LinkedList<>();
            middleRightThree2.add(R.id.top_right_three1);
            middleRightThree2.addAll(HorizontalResources.getTopMain1());
            middleRightThree2.add(R.id.middle_right_three1);
            middleRightThree2.addAll(HorizontalResources.getReverseMiddleMain1());
        }

        return middleRightThree2;
    }
}
