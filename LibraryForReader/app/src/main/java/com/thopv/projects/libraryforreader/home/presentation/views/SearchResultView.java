package com.thopv.projects.libraryforreader.home.presentation.views;

import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thopv.projects.libraryforreader.R;
import com.thopv.projects.libraryforreader.dagger.usecase.UseCaseProvider;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.BookStatus;
import com.thopv.projects.libraryforreader.home.presentation.adapters.BooksAdapter;
import com.thopv.projects.libraryforreader.home.presentation.contracts.BaseBookContract;
import com.thopv.projects.libraryforreader.home.presentation.presenters.BaseBookPresenter;
import com.thopv.projects.libraryforreader.support.ctx.AppbarActivity;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;

import junit.framework.Assert;

import java.util.List;

public class SearchResultView extends AppbarActivity implements BaseBookContract.View{

    private List<Book> books;
    private BaseBookPresenter presenter;
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search_result);

        Assert.assertNotNull(getIntent());
        Assert.assertNotNull(getIntent().getExtras());
        Assert.assertNotNull(getIntent().getExtras().getString("books"));
        Assert.assertNotNull(getIntent().getExtras().getString("keyword"));

        TypeToken<List<Book>> token = new TypeToken<List<Book>>(){};

        books = new Gson().fromJson(getIntent().getExtras().getString("books"), token.getType());
        keyword = getIntent().getExtras().getString("keyword");


        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        setupBooksList();
        initPresenter();
    }

    private void initPresenter() {
        UseCaseProvider useCaseProvider = UseCaseProvider.getInstance(this);
        presenter = new BaseBookPresenter(this,
                UseCaseHandler.getInstance(),
                useCaseProvider.getInsertFavoriteBook(),
                useCaseProvider.getInsertCartBook(),
                useCaseProvider.getRemoveFavoriteBook(),
                useCaseProvider.getRemoveCartBook(),
                useCaseProvider.getLoadBookOptionsMenuStatus(),
                useCaseProvider.getFetchBOokById()
                );
    }

    private void setupBooksList() {
        RecyclerView bookListView = findViewById(R.id.bookListView);
        BooksAdapter booksAdapter = new BooksAdapter((bookId, view) -> {
            presenter.loadOptionsMenuBook(bookId, view);
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        bookListView.setLayoutManager(linearLayoutManager);
        bookListView.setAdapter(booksAdapter);

        TextView countView = findViewById(R.id.countResultView);
        String count = "Có " + books.size() + " kết quả phù hợp với từ khóa '" +  keyword +"'";
        countView.setText(count);

        booksAdapter.setBooks(books);
    }

    @Override
    public void showOptionsMenu(BookStatus bookStatus, View view) {
        showBookOptionsMenu(bookStatus, view, presenter);
    }

    @Override
    public void showFetchedBook(Book book) {

    }
}
