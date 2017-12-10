package com.thopv.projects.libraryforreader.dagger.repository;

import android.content.Context;

import com.thopv.projects.libraryforreader.data.source.AppDatabase;
import com.thopv.projects.libraryforreader.data.source.repositories.BookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.CartBookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.FavoriteBookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.LoanRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.UserRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jlaotsezu on 23/11/2017.
 */
@Module
public class RepositoryModule {
    private Context context;
    private AppDatabase appDatabase;
    public RepositoryModule(Context context){
        this.context = context;
        appDatabase = AppDatabase.getInstance(context);
    }
    @Provides
    @RepositoryScope
    BookRepository bookRepository(){
        return new BookRepository(appDatabase);
    }
    @Provides
    @RepositoryScope
    CartBookRepository cartBookRepository(){
        return new CartBookRepository(appDatabase);
    }
    @Provides
    @RepositoryScope
    FavoriteBookRepository favoriteBookRepository(){
        return new FavoriteBookRepository(appDatabase);
    }
    @Provides
    @RepositoryScope
    LoanRepository loanRepository(){
        return new LoanRepository(appDatabase);
    }
    @Provides
    @RepositoryScope
    UserRepository userRepository(){
        return new UserRepository(appDatabase);
    }
}
