package main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.mappers;

import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.models.BookCopyModel;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Author;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.Book;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import main.com.jlaotsezu.library.support.architecture.Mapper;

public class BookCopyMapper implements Mapper<BookCopy, BookCopyModel> {
    @Override
    public BookCopyModel map(BookCopy bookCopy) {
        Book book = bookCopy.getBook();
        StringBuilder authorsBuild = new StringBuilder();
        if(book.getBookAuthors() != null){
            for(Author author : book.getBookAuthors()){
                authorsBuild.append(", ").append(author.getAuthorName());
            }
        }
        String authors = authorsBuild.toString().length() > 0 ? authorsBuild.substring(2) : "?";
        return new BookCopyModel.Builder()
                .setBookCopyId(bookCopy.getBookCopyId())
                .setBookId(book.getBookId())
                .setAuthors(authors)
                .setClassification(book.getBookClassification().getClassificationName())
                .setPublisher(book.getBookPublisher().getPublisherName())
                .setPublishYear(book.getBookPublishYear())
                .setBookCopyStatus(bookCopy.getBookCopyStatus().toString())
                .setBookCopyType(bookCopy.getBookCopyType().toString())
                .setBookName(book.getBookTitle())
                .build();
    }
}
