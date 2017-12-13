package com.thopv.projects.ikariam.home.domain.usecases;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.thopv.projects.ikariam.config.UseCaseComponent;
import com.thopv.projects.ikariam.data.schema.users.User;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by jlaotsezu on 25/11/2017.
 */
public class UpdateUserTest {
    UpdateUser updateUser;
    UseCaseComponent useCaseComponent;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        useCaseComponent = UseCaseComponent.getInstance(context);
        updateUser = useCaseComponent.getUpdateUser();
    }

    @Test
    public void executeUseCase() throws Exception {
        User user = new User("admin", "1", "Tho", 0);
        updateUser.setRequestValues(new UpdateUser.RequestValues(user));
        updateUser.setUseCaseCallback(response -> {
            Assert.assertNotNull(response);
            Assert.assertTrue(response.isSuccess());
        });
        updateUser.run();
    }

}