package com.thopv.projects.libraryforreader.home.presentation.adapters;

import android.view.View;
import android.widget.TextView;

import com.thopv.projects.libraryforreader.R;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;

/**
 * Created by thopv on 11/10/2017.
 */

public class MostSearchBookViewholder extends BookViewholder{
    private TextView searchTime;

    public MostSearchBookViewholder(View itemView) {
        super(itemView);
        searchTime = container.findViewById(R.id.bookSearchTimeView);
    }

    public void setBook(Book book, Events events) {
        super.setBook(book, events);
        String searchTImeStrng = "Lượt tìm kiếm: " + book.getSearchAmount();
        searchTime.setText(searchTImeStrng);
    }
}
