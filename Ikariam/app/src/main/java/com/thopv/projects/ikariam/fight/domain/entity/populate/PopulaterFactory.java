package com.thopv.projects.ikariam.fight.domain.entity.populate;

/**
 * Created by thopv on 11/15/2017.
 */

public class PopulaterFactory {
    public static BaseFieldPopulater create(int portLevel, int shipyardLevel){
        int level = portLevel > shipyardLevel ? portLevel : shipyardLevel;
        if (level < 8) {
            return new FieldPopulater0_7();
        } else if (level < 15) {
            return new FieldPopulater8_14();
        } else if (level < 22) {
            return new FieldPopulater15_21();
        } else if (level < 29) {
            return new FieldPopulater22_28();
        } else {
            return new FieldPopulater29_();
        }
    }
}
