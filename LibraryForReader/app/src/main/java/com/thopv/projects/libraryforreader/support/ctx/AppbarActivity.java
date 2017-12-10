package com.thopv.projects.libraryforreader.support.ctx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.thopv.projects.libraryforreader.R;
import com.thopv.projects.libraryforreader.home.domain.entity.BookStatus;
import com.thopv.projects.libraryforreader.home.presentation.presenters.BaseBookPresenter;
import com.thopv.projects.libraryforreader.home.presentation.views.BookOptionsMenu;
import com.thopv.projects.libraryforreader.home.presentation.views.CartView;
import com.thopv.projects.libraryforreader.home.presentation.views.FavoriteView;
import com.thopv.projects.libraryforreader.home.presentation.views.HomeView;
import com.thopv.projects.libraryforreader.loan.presentation.views.LoanView;
import com.thopv.projects.libraryforreader.support.utils.ToastUtils;
import com.thopv.projects.libraryforreader.welcome.presentation.views.LoginView;

/**
 * Created by thopv on 11/10/2017.
 */

public abstract class AppbarActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.cart){
            if(!(this instanceof CartView))
                startCartView();
        }
        else if(item.getItemId() == R.id.favorite){
            if(!(this instanceof FavoriteView))
                startFavoriteView();
        }
        else if(item.getItemId() == R.id.loan){
            if(!(this instanceof LoanView)){
                startLoanView();
            }
        }
        else if(item.getItemId() == R.id.logout){
            startLoginView();
        }
        return true;
    }

    private void startLoginView() {
        Intent intent = new Intent(this, LoginView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private ProgressDialog progressDialog;
    public void showProgressDialog(){
        progressDialog = ProgressDialog.show(this, "", "Đang xử lý...");
    }
    public void hideProgressDialog(){
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.cancel();
    }
    public void notifyFavoriteBookInserted(long bookId) {
        Snackbar.make(getWindow().getDecorView().getRootView(), "Đã thêm vào ưa thích", Snackbar.LENGTH_SHORT).setAction("Xem mục ưa thích", v -> {
            startFavoriteView();
        }).show();
    }

    public void notifyFavoriteBookDeleted(long bookId) {
        Snackbar.make(getWindow().getDecorView().getRootView(), "Đã xoá khỏi ưa thích", Snackbar.LENGTH_SHORT).setAction("Xem mục ưa thích", v -> {
            startFavoriteView();
        }).show();
    }

    public void notifyCartBookInserted(long bookId) {
        Snackbar.make(getWindow().getDecorView().getRootView(), "Đã thêm vào giỏ", Snackbar.LENGTH_SHORT).setAction("Xem giỏ", v -> {
            startCartView();
        }).show();
    }

    public void notifyCartBookDeleted(long bookId) {
        Snackbar.make(getWindow().getDecorView().getRootView(), "Đã xóa khỏi giỏ", Snackbar.LENGTH_SHORT).setAction("Xem giỏ", v -> {
            startCartView();
        }).show();
    }

    public void showMessage(String message) {
        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_SHORT).show();
    }

    public void showError(String err) {
        ToastUtils.getInstance(this).showMessage(err);
    }

    public void startCartView() {
        Intent intent = new Intent(this, CartView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        if(!(this instanceof HomeView)) {
            finish();
        }
    }

    public void startFavoriteView() {
        Intent intent = new Intent(this, FavoriteView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        if(!(this instanceof HomeView)) {
            finish();
        }
    }

    public void startLoanView(){
        Intent intent = new Intent(this, LoanView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        if(!(this instanceof HomeView)){
            finish();
        }
    }

    public void showBookOptionsMenu(BookStatus bookStatus, View view, BaseBookPresenter presenter) {
        BookOptionsMenu bookOptionsMenu = new BookOptionsMenu(view, bookStatus);
        bookOptionsMenu.show(new BookOptionsMenu.Events() {
            @Override
            public void shouldInsertToCart(long bookId) {
                presenter.addCartBook(bookId);
            }

            @Override
            public void shouldInsertToFavorite(long bookId) {
                presenter.addFavoriteBook(bookId);
            }

            @Override
            public void shouldRemoveFromCart(long bookId) {
                presenter.removeCartBook(bookId);
            }

            @Override
            public void shouldRemoveFromFavorite(long bookId) {
                presenter.removeFavoriteBook(bookId);
            }
        });
    }
}
