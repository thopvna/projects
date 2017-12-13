package com.thopv.projects.ikariam.data.source.mappers.field_troops;

import com.thopv.projects.ikariam.data.schema.armies.SFieldTroop;
import com.thopv.projects.ikariam.data.source.mappers.Mapper;
import com.thopv.projects.ikariam.fight.domain.entity.fight.FieldTroop;

import junit.framework.Assert;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by thopv on 11/21/2017.
 */

public class FromEFieldTroopToSFieldTroop implements Mapper<FieldTroop, SFieldTroop> {
    @Override
    public SFieldTroop map(FieldTroop source) {
        return new SFieldTroop.Builder()
                .setViewId(source.getViewId())
                .setUnitName(source.getUnitName())
                .setParty(source.getParty())
                .setAmount(source.getAmount())
                .setMaxAmount(source.getMaxAmount())
                .setCurrentHitPoints(source.getCurrentTotalHitpoints())
                .setCurrentMunitions(source.getFirstClassMunitions())
                .build();
    }
}
