package com.thopv.projects.libraryforreader.dagger.usecase;

import com.thopv.projects.libraryforreader.data.source.repositories.UserRepository;
import com.thopv.projects.libraryforreader.welcome.domain.usecases.Login;
import com.thopv.projects.libraryforreader.welcome.domain.usecases.Register;

import dagger.Module;
import dagger.Provides;

/**
 * Created by thopv on 12/2/2017.
 */
@Module
public class UserUseCaseModule {
    @Provides
    Login login(UserRepository userRepository){
        return new Login(userRepository);
    }
    @Provides
    Register register(UserRepository userRepository){
        return new Register(userRepository);
    }
}
