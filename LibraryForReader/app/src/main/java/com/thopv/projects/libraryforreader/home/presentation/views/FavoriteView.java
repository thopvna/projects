package com.thopv.projects.libraryforreader.home.presentation.views;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.thopv.projects.libraryforreader.R;
import com.thopv.projects.libraryforreader.dagger.usecase.UseCaseProvider;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.BookStatus;
import com.thopv.projects.libraryforreader.home.presentation.adapters.BooksAdapter;
import com.thopv.projects.libraryforreader.home.presentation.contracts.FavoriteContract;
import com.thopv.projects.libraryforreader.home.presentation.presenters.FavoritePresenter;
import com.thopv.projects.libraryforreader.support.ctx.AppbarActivity;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;
import com.thopv.projects.libraryforreader.support.utils.ToastUtils;

import java.util.List;

public class FavoriteView extends AppbarActivity implements FavoriteContract.View{

    private FavoritePresenter favoritePresenter;
    private BooksAdapter favoriteBooksAdapter;
    private TextView noFavoriteBooksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_controller);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        noFavoriteBooksView = findViewById(R.id.noFavoriteBookView);
        setupBooksView();
        initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        favoritePresenter.loadFavoriteBooks();
    }

    private void setupBooksView() {
        RecyclerView favoriteBooksView = findViewById(R.id.favoriteBookListView);
        LinearLayoutManager favoriteBooksLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        favoriteBooksAdapter = new BooksAdapter((bookId, view) -> {
            favoritePresenter.loadOptionsMenuBook(bookId, view);
        });
        favoriteBooksView.setLayoutManager(favoriteBooksLayoutManager);
        favoriteBooksView.setAdapter(favoriteBooksAdapter);
    }

    private void initPresenter() {
        UseCaseProvider useCaseProvider = UseCaseProvider.getInstance(this);
        favoritePresenter = new FavoritePresenter(this,
                UseCaseHandler.getInstance(), useCaseProvider.getInsertCartBook(), useCaseProvider.getRemoveFavoriteBook(), useCaseProvider.getRemoveCartBook(), useCaseProvider.getLoadBookOptionsMenuStatus(), useCaseProvider.getInsertFavoriteBook(),
                useCaseProvider.getLoadFavorites(),
                useCaseProvider.getFetchBOokById()
                );
    }
    @Override
    public void showOptionsMenu(BookStatus bookStatus, View view) {
        showBookOptionsMenu(bookStatus, view, favoritePresenter);
    }

    @Override
    public void notifyFavoriteBookInserted(long bookId) {
        favoritePresenter.fetchBookById(bookId);
        if(favoriteBooksAdapter.getItemCount() == 1){
            hideNoFavoriteBooks();
        }
    }

    @Override
    public void notifyFavoriteBookDeleted(long bookId) {
        favoriteBooksAdapter.removeBook(bookId);
        if(favoriteBooksAdapter.getItemCount() == 0){
            showNoFavoriteBooks();
        }
    }

    @Override
    public void notifyCartBookInserted(long bookId) {
        super.notifyCartBookInserted(bookId);
    }
    @Override
    public void notifyCartBookDeleted(long bookId) {
        super.notifyCartBookDeleted(bookId);
    }

    @Override
    public void showFetchedBook(Book book) {
        favoriteBooksAdapter.insertBook(book);
    }

    @Override
    public void showFavoriteBooks(List<Book> books) {
        favoriteBooksAdapter.setBooks(books);
    }

    @Override
    public void showNoFavoriteBooks() {
        noFavoriteBooksView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoFavoriteBooks() {
        noFavoriteBooksView.setVisibility(View.INVISIBLE);
    }
}
