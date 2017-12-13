package com.thopv.projects.ikariam.fight.domain.usecases;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.thopv.projects.ikariam.config.UseCaseComponent;
import com.thopv.projects.ikariam.UseCaseHandler;
import com.thopv.projects.ikariam.data.source.daos.AppDatabase;
import com.thopv.projects.ikariam.data.schema.houses.House;
import com.thopv.projects.ikariam.data.source.repositories.HouseRepository;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by jlaotsezu on 25/11/2017.
 */
public class PopulateUnitsTest {
    private PopulateUnits populateUnits;
    private HouseRepository houseRepository;
    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        UseCaseComponent useCaseComponent = UseCaseComponent.getInstance(context);
        populateUnits = useCaseComponent.getPopulateUnits();
        houseRepository = new HouseRepository(AppDatabase.getInstance(context));
    }

    @Test
    public void executeUseCase() throws Exception {
        House house = houseRepository.findById("House_7");
        Assert.assertNotNull(house);
        UseCaseHandler.execute(populateUnits, new PopulateUnits.RequestValues(), response -> {
            Assert.assertNotNull(response);
            Assert.assertTrue(response.getMessage(), response.isSuccess());
        });
    }

}