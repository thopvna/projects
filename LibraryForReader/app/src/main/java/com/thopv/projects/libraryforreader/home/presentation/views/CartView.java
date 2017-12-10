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
import com.thopv.projects.libraryforreader.home.presentation.contracts.CartContract;
import com.thopv.projects.libraryforreader.home.presentation.presenters.CartPresenter;
import com.thopv.projects.libraryforreader.loan.presentation.views.LoanView;
import com.thopv.projects.libraryforreader.support.ctx.AppbarActivity;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;
import com.thopv.projects.libraryforreader.support.utils.ConfirmDialog;
import com.thopv.projects.libraryforreader.support.utils.ToastUtils;
import com.thopv.projects.libraryforreader.support.utils.ViewUtils;

import java.util.List;

public class CartView extends AppbarActivity implements CartContract.View{

    private CartPresenter cartPresenter;
    private BooksAdapter booksAdapter;
    private View sendBorrowRequestButton;
    private TextView noCartBooksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_controller);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        noCartBooksView = findViewById(R.id.noCartBookView);
        sendBorrowRequestButton = findViewById(R.id.sendBorrowRequest);
        sendBorrowRequestButton.setOnClickListener(v -> {
            ViewUtils.disableView(v);
            String title = "Xác nhận";
            String message = "Bạn chắc chắn muốn gửi request này? ";
            ConfirmDialog.show(this, title, message, () -> {
                cartPresenter.sendBorrowRequest();
            });
        });
        setupBooksView();
        initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartPresenter.loadCartBooks();
    }

    private void initPresenter() {
        UseCaseProvider useCaseProvider = UseCaseProvider.getInstance(this);
        cartPresenter = new CartPresenter(this,
                useCaseProvider.getInsertFavoriteBook(),
                useCaseProvider.getInsertCartBook(),
                useCaseProvider.getRemoveFavoriteBook(),
                useCaseProvider.getRemoveCartBook(),
                useCaseProvider.getLoadBookOptionsMenuStatus(),
                UseCaseHandler.getInstance(), useCaseProvider.getLoadCart(),
                useCaseProvider.getSendBorrowRequest(),
                useCaseProvider.getFetchBOokById()
        );
    }

    private void setupBooksView() {
        RecyclerView cartBooksView = findViewById(R.id.cartBooksView);
        LinearLayoutManager cartBooksViewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        booksAdapter = new BooksAdapter((bookId, view) -> {
            cartPresenter.loadOptionsMenuBook(bookId, view);
        });
        cartBooksView.setLayoutManager(cartBooksViewLayoutManager);
        cartBooksView.setAdapter(booksAdapter);
    }

    @Override
    public void showCartBooks(List<Book> books) {
       booksAdapter.setBooks(books);
    }

    @Override
    public void enableSendBorrowRequestButton() {
        sendBorrowRequestButton.setClickable(true);
    }

    @Override
    public void disableSendBorrowRequestButton() {
        sendBorrowRequestButton.setClickable(false);
    }

    @Override
    public void showSendBorrowRequestSuccess() {
        Snackbar.make(getWindow().getDecorView().getRootView(), "Gửi request thành công.", Snackbar.LENGTH_SHORT).setAction("Chi tiết", v -> {
            startLoanView();
        }).show();
    }

    @Override
    public void clearCart() {
        booksAdapter.clear();
    }

    @Override
    public void showNoCartBooks() {
        noCartBooksView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoCartBooks() {
        noCartBooksView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showOptionsMenu(BookStatus bookStatus, View view) {
        showBookOptionsMenu(bookStatus, view, cartPresenter);
    }

    @Override
    public void notifyFavoriteBookInserted(long bookId) {
        super.notifyFavoriteBookInserted(bookId);
    }


    @Override
    public void notifyFavoriteBookDeleted(long bookId) {
        super.notifyFavoriteBookDeleted(bookId);
    }
    @Override
    public void notifyCartBookInserted(long bookId) {
        cartPresenter.fetchBookById(bookId);
        if(booksAdapter.getItemCount() > 0){
            enableSendBorrowRequestButton();
        }
    }

    @Override
    public void notifyCartBookDeleted(long bookId) {
        booksAdapter.removeBook(bookId);
        if(booksAdapter.getItemCount() == 0){
            disableSendBorrowRequestButton();
            showNoCartBooks();
        }
    }

    @Override
    public void showFetchedBook(Book book) {
        booksAdapter.insertBook(book);
    }


}
