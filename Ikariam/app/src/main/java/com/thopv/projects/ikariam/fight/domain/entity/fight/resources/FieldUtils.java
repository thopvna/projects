package com.thopv.projects.ikariam.fight.domain.entity.fight.resources;

import com.thopv.projects.ikariam.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/27/2017.
 */

public class FieldUtils {
    private static List<Integer> blueMainViewIds;

    public static List<Integer> getMainBlueViewIds() {
        if(blueMainViewIds == null){
            blueMainViewIds = new LinkedList<>();
            blueMainViewIds.addAll(HorizontalResources.getTopSupportLeft1());
            blueMainViewIds.addAll(HorizontalResources.getTopMain1());
            blueMainViewIds.addAll(HorizontalResources.getTopSupportRight1());

            blueMainViewIds.addAll(HorizontalResources.getMiddleMain1());
        }
        return blueMainViewIds;
    }
    private static List<Integer> redMainViewIds;
    public static List<Integer> getMainRedViewIds() {
        if(redMainViewIds == null){
            redMainViewIds = new LinkedList<>();
            redMainViewIds.addAll(HorizontalResources.getTopSupportLeft2());
            redMainViewIds.addAll(HorizontalResources.getTopMain2());
            redMainViewIds.addAll(HorizontalResources.getTopSupportRight2());

            redMainViewIds.addAll(HorizontalResources.getMiddleMain2());
        }
        return redMainViewIds;
    }
    private static List<Integer> redViewIds;

    public static List<Integer> getRedViewIds() {
        if(redViewIds == null){
            redViewIds = new LinkedList<>();
            redViewIds.addAll(getMainRedViewIds());
            redViewIds.addAll(getRedBottomTroopsViewIds());
        }
        return redViewIds;
    }
    private static List<Integer> blueViewIds;

    public static List<Integer> getBlueViewIds() {
        if(blueViewIds == null){
            blueViewIds = new LinkedList<>();
            blueViewIds.addAll(getMainBlueViewIds());
            blueViewIds.addAll(getBlueBottomTroopsViewIds());
        }
        return blueViewIds;
    }

    private static List<Integer> redBottomTroopsViewIds;
    public static List<Integer> getRedBottomTroopsViewIds(){
        if(redBottomTroopsViewIds == null){
            redBottomTroopsViewIds = new LinkedList<>();
            redBottomTroopsViewIds.addAll(HorizontalResources.getBottomSupportLeft2());
            redBottomTroopsViewIds.addAll(HorizontalResources.getBottomMain2());
            redBottomTroopsViewIds.addAll(HorizontalResources.getBottomSupportRight2());
        }
        return redBottomTroopsViewIds;
    }
    private static List<Integer> blueBottomTroopsViewIds;

    public static List<Integer> getBlueBottomTroopsViewIds() {
        if(blueBottomTroopsViewIds == null){
            blueBottomTroopsViewIds = new LinkedList<>();;
            blueBottomTroopsViewIds.addAll(HorizontalResources.getBottomSupportLeft1());
            blueBottomTroopsViewIds.addAll(HorizontalResources.getBottomMain1());
            blueBottomTroopsViewIds.addAll(HorizontalResources.getBottomSupportRight1());
        }
        return blueBottomTroopsViewIds;
    }
    private static List<Integer> firingOrders;
    public static List<Integer> getFiringOrders(){
        if(firingOrders == null){
            firingOrders = new LinkedList<>();
            firingOrders.addAll(HorizontalResources.getBottomSupportRight1());
            firingOrders.addAll(HorizontalResources.getBottomSupportRight2());
            firingOrders.addAll(HorizontalResources.getBottomSupportLeft1());
            firingOrders.addAll(HorizontalResources.getBottomSupportLeft2());
            firingOrders.addAll(HorizontalResources.getBottomMain1());
            firingOrders.addAll(HorizontalResources.getBottomMain2());
            firingOrders.addAll(HorizontalResources.getTopSupportLeft1());
            firingOrders.addAll(HorizontalResources.getTopSupportLeft2());
            firingOrders.addAll(HorizontalResources.getTopSupportRight1());
            firingOrders.addAll(HorizontalResources.getTopSupportRight2());
            firingOrders.addAll(HorizontalResources.getMiddleMain1());
            firingOrders.addAll(HorizontalResources.getMiddleMain2());
            firingOrders.addAll(HorizontalResources.getTopMain1());
            firingOrders.addAll(HorizontalResources.getTopMain2());
        }
        return firingOrders;
    }
    private static List<Integer> reverseFiringOrders;
    public static List<Integer> getReverseFiringOrders(){
        if(reverseFiringOrders == null){
            reverseFiringOrders = new LinkedList<>();
            reverseFiringOrders.addAll(HorizontalResources.getBottomSupportRight2());
            reverseFiringOrders.addAll(HorizontalResources.getBottomSupportRight1());
            reverseFiringOrders.addAll(HorizontalResources.getBottomSupportLeft2());
            reverseFiringOrders.addAll(HorizontalResources.getBottomSupportLeft1());
            reverseFiringOrders.addAll(HorizontalResources.getBottomMain2());
            reverseFiringOrders.addAll(HorizontalResources.getBottomMain1());
            reverseFiringOrders.addAll(HorizontalResources.getTopSupportLeft2());
            reverseFiringOrders.addAll(HorizontalResources.getTopSupportLeft1());
            reverseFiringOrders.addAll(HorizontalResources.getTopSupportRight2());
            reverseFiringOrders.addAll(HorizontalResources.getTopSupportRight1());
            reverseFiringOrders.addAll(HorizontalResources.getMiddleMain2());
            reverseFiringOrders.addAll(HorizontalResources.getMiddleMain1());
            reverseFiringOrders.addAll(HorizontalResources.getTopMain2());
            reverseFiringOrders.addAll(HorizontalResources.getTopMain1());
        }
        return reverseFiringOrders;
    }
}
