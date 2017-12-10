package com.thopv.projects.libraryforreader.home.presentation.adapters;

import android.view.View;
import android.widget.TextView;

import com.thopv.projects.libraryforreader.R;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.support.utils.DateUtils;

/**
 * Created by thopv on 11/10/2017.
 */

public class NewestImportBookViewholder extends BookViewholder {
    private TextView importTimeView, bookCopyAmountView;
    public NewestImportBookViewholder(View itemView) {
        super(itemView);
        importTimeView = container.findViewById(R.id.importTimeView);
        bookCopyAmountView = container.findViewById(R.id.amountBookCopy);
    }

    public void setBook(Book book, Events events) {
        super.setBook(book, events);
        String importTIme;
        try {
            importTIme = DateUtils.getDistanceFromNow(book.getLastedImportTime()) + "trước";
        } catch (Exception e) {
            importTIme = "Vừa mới đây";
        }
        String booKCopyAMount = "Số bản copy: " + book.getBookCopyAmount();
        importTimeView.setText(importTIme);
        bookCopyAmountView.setText(booKCopyAMount);
    }

}
