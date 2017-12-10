package com.thopv.projects.libraryforreader.home.presentation.views;

import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.PopupMenu;

import com.thopv.projects.libraryforreader.home.domain.entity.BookStatus;

import java.lang.ref.WeakReference;

/**
 * Created by thopv on 12/4/2017.
 */

public class BookOptionsMenu {

    private final PopupMenu popupMenu;

    public interface Events{
        void shouldInsertToCart(long bookId);
        void shouldInsertToFavorite(long bookId);
        void shouldRemoveFromCart(long bookId);
        void shouldRemoveFromFavorite(long bookId);
    }
    public BookOptionsMenu(View parent, BookStatus bookStatus){
        popupMenu = new PopupMenu(parent.getContext(), parent);
        Menu menu = popupMenu.getMenu();

        long bookId = bookStatus.getBookId();
        genOptionsMenu(bookStatus, menu);
        setupOptionsMenuEvents(popupMenu, bookId);
    }
    private Events events;
    public void show(Events events){
        this.events = events;
        popupMenu.show();
    }
    private void setupOptionsMenuEvents(PopupMenu popupMenu, long bookId) {
        popupMenu.setOnMenuItemClickListener(item -> {
            if(events != null) {
                if (item.getItemId() == 0) {
                    events.shouldRemoveFromCart(bookId);
                } else if (item.getItemId() == 1) {
                    events.shouldInsertToCart(bookId);
                } else if (item.getItemId() == 2) {
                    events.shouldRemoveFromFavorite(bookId);
                } else if (item.getItemId() == 3) {
                    events.shouldInsertToFavorite(bookId);
                }
            }
            return true;
        });
    }

    private void genOptionsMenu(BookStatus bookStatus, Menu menu) {
        if(bookStatus.isInCart()){
            menu.add(0, 0, 0, "Xóa khỏi cart");
        }
        else{
            menu.add(0, 1, 1, "Thêm vào cart");
        }
        if(bookStatus.isInFavorite()){
            menu.add(0, 2, 2, "Xóa khỏi ưa thích");
        }
        else{
            menu.add(0, 3, 3, "Thêm vào ưa thích");
        }
    }
}
