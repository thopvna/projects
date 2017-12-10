package main.com.jlaotsezu.library.quan_ly_sach.presentation.mappers;

import main.com.jlaotsezu.library.support.architecture.Mapper;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Author;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.presentation.models.BookModel;

public class BookMapper implements Mapper<Book, BookModel> {
    @Override
    public BookModel map(Book book) {

        StringBuilder authorsBuild = new StringBuilder();
        if(book.getBookAuthors() != null){
            for(Author author : book.getBookAuthors()){
                authorsBuild.append(", ").append(author.getAuthorName());
            }
        }
        String authors = authorsBuild.toString().length() > 0 ? authorsBuild.substring(2) : "?";
        return new BookModel.Builder()
                .setBookId(book.getBookId())
                .setBookName(book.getBookTitle())
                .setClassification(book.getBookClassification().getClassificationName())
                .setPublisher(book.getBookPublisher().getPublisherName())
                .setAuthors(authors)
                .setPublishYear(book.getBookPublishYear())
                .build();
    }
}
