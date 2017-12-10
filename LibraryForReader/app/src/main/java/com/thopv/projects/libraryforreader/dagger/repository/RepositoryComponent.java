package com.thopv.projects.libraryforreader.dagger.repository;


import com.thopv.projects.libraryforreader.data.source.repositories.BookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.CartBookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.FavoriteBookRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.LoanRepository;
import com.thopv.projects.libraryforreader.data.source.repositories.UserRepository;

import dagger.Component;

/**
 * Created by jlaotsezu on 23/11/2017.
 */
@RepositoryScope
@Component(modules = {RepositoryModule.class})
public interface RepositoryComponent {
    BookRepository getBookRepository();
    CartBookRepository getCartBookRepository();
    FavoriteBookRepository getFavoriteBookRepository();
    LoanRepository getLoanRepository();
    UserRepository getUserRepository();
}
