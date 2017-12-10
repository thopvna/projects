package com.thopv.projects.libraryforreader.home.presentation.adapters;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thopv.projects.libraryforreader.R;
import com.thopv.projects.libraryforreader.home.domain.entity.Author;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.BookStatus;
import com.thopv.projects.libraryforreader.support.utils.BookIconUtils;

import java.util.List;

/**
 * Created by thopv on 11/10/2017.
 */

public class BookViewholder extends RecyclerView.ViewHolder{
    protected ImageView bookImageView;
    protected TextView bookNameView;
    protected TextView authorView;
    protected TextView classificationView;
    protected View container;
    protected ImageView moreImageView;

    public BookViewholder(View itemView) {
        super(itemView);
        container = itemView;
        bookImageView = container.findViewById(R.id.bookImageView);
        bookNameView = container.findViewById(R.id.bookNameView);
        authorView = container.findViewById(R.id.authorView);
        classificationView = container.findViewById(R.id.classificationView);
        moreImageView = container.findViewById(R.id.moreImageView);
    }
    public interface Events {
        void onOptionsMenuClicked(long bookId, View view);
    }
    @CallSuper
    public void setBook(Book book, Events events){
        long bookId = book.getBookId();
        String bookName = book.getTitle();
        String authorName = getAuthor(book.getAuthors());
        String classificationName = "Thể loại: " + book.getClassification().getClassificationName();
        int imageDrawableId = BookIconUtils.getRandomImageDrawableId();

        bookNameView.setText(bookName);
        authorView.setText(authorName);
        classificationView.setText(classificationName);

        Picasso.with(container.getContext())
                .load(imageDrawableId)
                .placeholder(R.drawable.ic_book)
                .fit()
                .into(bookImageView);

        moreImageView.setOnClickListener(v -> {
            events.onOptionsMenuClicked(bookId, v);
        });
    }

    private String getAuthor(List<Author> authors) {
        if(authors == null) return "Chưa có thông tin tác giả.";
        StringBuilder resultBuilder = new StringBuilder(authors.get(0).getAuthorName());
        for(Author author : authors){
            resultBuilder.append(", ").append(author.getAuthorName());
        }
        return resultBuilder.toString();
    }
}
