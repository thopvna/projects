package com.thopv.projects.ikariam.home.presentation.presenters;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.thopv.projects.ikariam.config.UseCaseComponent;
import com.thopv.projects.ikariam.UseCaseHandler;
import com.thopv.projects.ikariam.data.schema.users.User;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by jlaotsezu on 25/11/2017.
 */
public class HomePresenterTest {
    private UseCaseComponent useCaseComponent;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Context context = InstrumentationRegistry.getTargetContext();
        useCaseComponent = UseCaseComponent.getInstance(context);
    }

    @Test
    public void changeParty() throws Exception {
        UpdateUser updateUser = useCaseComponent.getUpdateUser();
        User user = new User("admin", "1", "Tho", 0);
        UseCaseHandler.execute(updateUser, new UpdateUser.RequestValues(user), response -> {
            Assert.assertNotNull(response);
            Assert.assertTrue(response.isSuccess());
        });
    }

}