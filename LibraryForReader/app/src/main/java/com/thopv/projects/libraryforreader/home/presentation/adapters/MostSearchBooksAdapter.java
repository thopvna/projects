package com.thopv.projects.libraryforreader.home.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thopv.projects.libraryforreader.R;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/10/2017.
 */

public class MostSearchBooksAdapter extends RecyclerView.Adapter<MostSearchBookViewholder> {
    private List<Book> books;
    private BookViewholder.Events events;

    public MostSearchBooksAdapter(BookViewholder.Events events) {
        this.books = new LinkedList<>();
        this.events = events;
    }

    public void setBooks(List<Book> books){
        if(books != null) {
            this.books = books;
            notifyDataSetChanged();
        }
    }
    @Override
    public MostSearchBookViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.most_search_book_cardview, parent, false);
        return new MostSearchBookViewholder(view);
    }

    @Override
    public void onBindViewHolder(MostSearchBookViewholder holder, int position) {
        Book book = books.get(position);
        holder.setBook(book, events);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
