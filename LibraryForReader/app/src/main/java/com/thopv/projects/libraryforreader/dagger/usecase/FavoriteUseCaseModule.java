package com.thopv.projects.libraryforreader.dagger.usecase;

import com.thopv.projects.libraryforreader.data.source.repositories.BookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.FavoriteBookRepository;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.InsertFavoriteBook;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.LoadFavorites;
import com.thopv.projects.libraryforreader.home.domain.usecases.favorite_manager.RemoveFavoriteBook;

import dagger.Module;
import dagger.Provides;

/**
 * Created by thopv on 12/2/2017.
 */
@Module
public class FavoriteUseCaseModule {
    @Provides
    InsertFavoriteBook insertFavoriteBook(FavoriteBookRepository favoriteBookRepository, BookRepository bookRepository){
        return new InsertFavoriteBook(favoriteBookRepository, bookRepository);
    }
    @Provides
    LoadFavorites loadFavorites(FavoriteBookRepository favoriteBookRepository, BookRepository bookRepository){
        return new LoadFavorites(favoriteBookRepository, bookRepository);
    }
    @Provides
    RemoveFavoriteBook removeFavoriteBook(FavoriteBookRepository favoriteBookRepository){
        return new RemoveFavoriteBook(favoriteBookRepository);
    }
}
