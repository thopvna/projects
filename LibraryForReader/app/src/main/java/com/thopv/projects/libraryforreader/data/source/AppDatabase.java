package com.thopv.projects.libraryforreader.data.source;

import android.arch.persistence.room.Database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.thopv.projects.libraryforreader.data.source.daos.AuthorDAO;
import com.thopv.projects.libraryforreader.data.source.daos.BookDAO;
import com.thopv.projects.libraryforreader.data.source.daos.CartBookDAO;
import com.thopv.projects.libraryforreader.data.source.daos.ClassificationDAO;
import com.thopv.projects.libraryforreader.data.source.daos.FavoriteBookDAO;
import com.thopv.projects.libraryforreader.data.source.daos.LoanDAO;
import com.thopv.projects.libraryforreader.data.source.daos.PublisherDAO;
import com.thopv.projects.libraryforreader.data.source.daos.UserDAO;
import com.thopv.projects.libraryforreader.data.source.schemas.SAuthor;
import com.thopv.projects.libraryforreader.data.source.schemas.SBook;
import com.thopv.projects.libraryforreader.data.source.schemas.SBookAuthorDetail;
import com.thopv.projects.libraryforreader.data.source.schemas.SCartBook;
import com.thopv.projects.libraryforreader.data.source.schemas.SClassification;
import com.thopv.projects.libraryforreader.data.source.schemas.SFavoriteBook;
import com.thopv.projects.libraryforreader.data.source.schemas.SLoan;
import com.thopv.projects.libraryforreader.data.source.schemas.SLoanDetail;
import com.thopv.projects.libraryforreader.data.source.schemas.SPublisher;
import com.thopv.projects.libraryforreader.data.source.schemas.SUser;

import javax.inject.Singleton;

/**
 * Created by thopv on 11/9/2017.
 */
@Singleton
@Database(entities = {SUser.class, SBook.class, SBookAuthorDetail.class, SClassification.class, SAuthor.class, SPublisher.class , SLoan.class, SLoanDetail.class , SCartBook.class, SFavoriteBook.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "library").build();
        }
        return instance;
    }
    public abstract BookDAO getBookDAO();
    public abstract AuthorDAO getAuthorDAO();
    public abstract ClassificationDAO getClassificationDAO();
    public abstract PublisherDAO getPublisherDAO();
    public abstract UserDAO getUserDAO();
    public abstract LoanDAO getLoanDAO();

    public abstract CartBookDAO getCartBookDAO();
    public abstract FavoriteBookDAO getFavoriteBookDAO();
}
