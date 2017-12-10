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
 * Created by thopv on 11/11/2017.
 */

public class BooksAdapter extends RecyclerView.Adapter<BookViewholder> {
    private BookViewholder.Events events;
    private List<Book> books;

    public BooksAdapter(BookViewholder.Events events) {
        this.events = events;
        books = new LinkedList<>();
    }

    public void setBooks(List<Book> books) {
        if(books != null) {
            this.books = books;
            notifyDataSetChanged();
        }
    }

    @Override
    public BookViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_book_cardview, parent, false);
        return new BookViewholder(view);
    }

    @Override
    public void onBindViewHolder(BookViewholder holder, int position) {
        Book book = books.get(position);
        holder.setBook(book, events);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void removeBook(long bookId){
        int index = -1;
        for(int i =0; i < books.size(); i++){
            if(bookId == books.get(i).getBookId()){
                index = i;
                break;
            }
        }
        if(index != -1) {
            books.remove(index);
            notifyItemRemoved(index);
        }
        else
            throw new RuntimeException("???");
    }
    public void insertBook(Book book){
        if(book != null){
            books.add(0, book);
            notifyItemInserted(0);
        }
    }

    public void clear() {
        this.books.clear();
        notifyDataSetChanged();
    }
}
