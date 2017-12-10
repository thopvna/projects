package com.thopv.projects.libraryforreader.home.presentation.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.thopv.projects.libraryforreader.R;
import com.thopv.projects.libraryforreader.dagger.usecase.UseCaseProvider;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.BookStatus;
import com.thopv.projects.libraryforreader.home.presentation.adapters.MostSearchBooksAdapter;
import com.thopv.projects.libraryforreader.home.presentation.adapters.NewestImportBooksAdapter;
import com.thopv.projects.libraryforreader.home.presentation.contracts.BookContract;
import com.thopv.projects.libraryforreader.home.presentation.presenters.BookPresenter;
import com.thopv.projects.libraryforreader.support.ctx.AppbarActivity;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;
import com.thopv.projects.libraryforreader.support.utils.ViewUtils;

import java.util.List;

public class HomeView extends AppbarActivity implements BookContract.View{
    private BookPresenter bookPresenter;
    private MostSearchBooksAdapter mostSearchBooksAdapter;
    private NewestImportBooksAdapter newestImportBooksAdapter;
    private ProgressBar mostSearchProgressBar;
    private ProgressBar newestImportProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_controller);

        mostSearchProgressBar = findViewById(R.id.mostSearchProgressBar);
        newestImportProgressBar = findViewById(R.id.newestImportProgressBar);

        setupMostSearchBooksView();
        setupNewestImportBooksView();
        initBookPresenter();

        findViewById(R.id.searchButtonView).setOnClickListener(v -> {
            ViewUtils.disableView(v);
            bookPresenter.searchBook(getKeyword());
        });
    }
    public String getKeyword(){
        TextView keyWordView = findViewById(R.id.keywordView);
        return keyWordView.getText().toString();
    }
    private void initBookPresenter() {
        UseCaseProvider useCaseProvider = UseCaseProvider.getInstance(this);
        bookPresenter = new BookPresenter(this,
                UseCaseHandler.getInstance(), useCaseProvider.getSearchBooks(), useCaseProvider.getInsertFavoriteBook(), useCaseProvider.getInsertCartBook(), useCaseProvider.getRemoveFavoriteBook(), useCaseProvider.getRemoveCartBook(), useCaseProvider.getLoadBookOptionsMenuStatus(), useCaseProvider.getLoadBooks(),
                useCaseProvider.getFetchBOokById()
        );
    }

    private void setupNewestImportBooksView() {
        RecyclerView newestImportBooksView = findViewById(R.id.newBooksList);
        LinearLayoutManager newestImportBooksLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        newestImportBooksAdapter = new NewestImportBooksAdapter((bookId, view) -> {
            bookPresenter.loadOptionsMenuBook(bookId, view);
        });
        newestImportBooksView.setLayoutManager(newestImportBooksLayoutManager);
        newestImportBooksView.setAdapter(newestImportBooksAdapter);
    }

    private void setupMostSearchBooksView() {
        RecyclerView mostSearchBooksView = findViewById(R.id.mostSearchBooksList);
        LinearLayoutManager mostSearchBooksLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mostSearchBooksAdapter = new MostSearchBooksAdapter((bookId, view) -> {
            bookPresenter.loadOptionsMenuBook(bookId, view);
        });
        mostSearchBooksView.setLayoutManager(mostSearchBooksLayoutManager);
        mostSearchBooksView.setAdapter(mostSearchBooksAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookPresenter.loadNewestImportBooks();
        bookPresenter.loadMostSearchBooks();
    }

    @Override
    public void showMostSearchBooks(List<Book> books) {
        mostSearchBooksAdapter.setBooks(books);
    }

    @Override
    public void showNewestImportBooks(List<Book> books) {
        newestImportBooksAdapter.setBooks(books);
    }

    @Override
    public void showOptionsMenu(BookStatus bookStatus, View view) {
        showBookOptionsMenu(bookStatus, view, bookPresenter);
    }

    @Override
    public void showFetchedBook(Book book) {

    }

    @Override
    public void showSearchResult(List<Book> books, String keyword) {
        Intent intent = new Intent(this, SearchResultView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("books", new Gson().toJson(books));
        intent.putExtra("keyword", keyword);
        startActivity(intent);
    }

    @Override
    public void showEmptySearchResult() {
        //TODO: Show empty search result.
        showMessage("Không có kết quả nào phù hợp.");
    }

    @Override
    public void showMostSearchProgressBar() {
        mostSearchProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNewestImportProgressBar() {
        newestImportProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMostSearchProgressBar() {
        mostSearchProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideNewestImportProgressBar() {
        newestImportProgressBar.setVisibility(View.INVISIBLE);
    }
}
