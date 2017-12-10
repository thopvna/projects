package com.thopv.projects.libraryforreader.dagger.usecase;

import com.thopv.projects.libraryforreader.data.source.repositories.BookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.CartBookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.FavoriteBookRepository;
import com.thopv.projects.libraryforreader.home.domain.usecases.FetchBookById;
import com.thopv.projects.libraryforreader.home.domain.usecases.LoadBookOptionsMenuStatus;
import com.thopv.projects.libraryforreader.home.domain.usecases.LoadBooks;
import com.thopv.projects.libraryforreader.home.domain.usecases.SearchBooks;

import dagger.Module;
import dagger.Provides;

/**
 * Created by thopv on 12/2/2017.
 */
@Module
public class BookUseCaseModule {
    @Provides
    LoadBooks loadBooks(BookRepository bookRepository){
        return new LoadBooks(bookRepository);
    }
    @Provides
    SearchBooks searchBooks(BookRepository bookRepository){
        return new SearchBooks(bookRepository);
    }
    @Provides
    LoadBookOptionsMenuStatus loadBookOptionsMenuStatus(CartBookRepository cartBookRepository, FavoriteBookRepository favoriteBookRepository){
        return new LoadBookOptionsMenuStatus(cartBookRepository, favoriteBookRepository);
    }
    @Provides
    FetchBookById fetchBookById(BookRepository bookRepository){
        return new FetchBookById(bookRepository);
    }
}
