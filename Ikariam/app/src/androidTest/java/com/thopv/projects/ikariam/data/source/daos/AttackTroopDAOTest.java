package com.thopv.projects.ikariam.data.source.daos;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.thopv.projects.ikariam.data.schema.armies.AttackTroop;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by jlaotsezu on 26/11/2017.
 */
public class AttackTroopDAOTest {
    private AttackTroopDAO attackTroopDAO;
    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        attackTroopDAO = AppDatabase.getInstance(context).getAttackTroopDAO();
    }

    @Test
    public void getAll() throws Exception {
        List<AttackTroop> attackTroops = attackTroopDAO.getAll();
        Assert.assertNotNull(attackTroops);
        Assert.assertTrue(attackTroops.size() > 0);
    }

}