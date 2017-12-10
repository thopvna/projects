package com.thopv.projects.libraryforreader;

import com.thopv.projects.libraryforreader.home.domain.entity.Author;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.Classification;
import com.thopv.projects.libraryforreader.home.domain.entity.Publisher;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;
import com.thopv.projects.libraryforreader.support.utils.DateUtils;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by thopv on 12/3/2017.
 */

public class TestUtils {
    private static Random random = new Random();
    public static Book genBook() {
        return new Book.Builder()
                .setBookId(genAbsRandomLong())
                .setTitle("Book title " + genAbsRandomInt())
                .setBriefContent("Brief content "  + genAbsRandomInt())
                .setPublisher(genPublisher())
                .setClassification(getClassification())
                .setAuthors(genAuthors())
                .setPublishTime(System.currentTimeMillis())
                .setSearchAmount(genAbsRandomInt())
                .setBookCopyAmount(genAbsRandomInt())
                .setLastedImportTime(System.currentTimeMillis())
                .build();
    }
    public static List<Book> genBooks() {
        List<Book> books = new LinkedList<>();
        books.add(genBook());
        books.add(genBook());
        books.add(genBook());
        books.add(genBook());
        books.add(genBook());
        return books;
    }
    public static List<Book> genBooks(int size) {
        List<Book> books = new LinkedList<>();
        for(int i =0; i < size; i++){
            books.add(genBook());
        }
        return books;
    }

    public static long genAbsRandomLong() {
        return Math.abs(random.nextLong());
    }

    public static int genAbsRandomInt() {
        return Math.abs(random.nextInt());
    }
    public static Loan genLoan(){
        List<Book> books = genBooks();

        List<Long> booksId = new LinkedList<>();
        for(Book book : books){
            booksId.add(book.getBookId());
        }
        int ok = genAbsRandomInt();
        if(ok % 2 == 0)
            return new Loan.Builder()
                .setLoanId(genAbsRandomLong())
                .setBornTime(System.currentTimeMillis())
                .setReturnTimeExpected(0)
                .setStartTime(0)
                .setReturnTime(0)
                .setFee(0)
                .setBookIds(booksId)
                .setWasReturn(false)
                .setWasLend(false)
                .build();
        else if(ok % 3 == 0)
            return new Loan.Builder()
                    .setLoanId(genAbsRandomLong())
                    .setBornTime(System.currentTimeMillis())
                    .setReturnTimeExpected(System.currentTimeMillis() + 2 * DateUtils.WEEK)
                    .setStartTime(System.currentTimeMillis())
                    .setReturnTime(0)
                    .setFee(0)
                    .setBookIds(booksId)
                    .setWasLend(true)
                    .setWasReturn(false)
                    .build();
        else
            return new Loan.Builder()
                    .setLoanId(genAbsRandomLong())
                    .setBornTime(System.currentTimeMillis())
                    .setReturnTimeExpected(System.currentTimeMillis() + 2 * DateUtils.WEEK)
                    .setStartTime(System.currentTimeMillis())
                    .setReturnTime(System.currentTimeMillis() + DateUtils.MINUTE)
                    .setFee(300000)
                    .setBookIds(booksId)
                    .setWasLend(true)
                    .setWasReturn(true)
                    .build();
    }
    public static Author genAuthor(){
        return new Author(TestUtils.genAbsRandomLong(),
                "Author Name " + genAbsRandomInt(),
                System.currentTimeMillis(),
                System.currentTimeMillis() + genAbsRandomInt(),
                "Author Address " + genAbsRandomInt());
    }
    public static List<Author> genAuthors(){
        List<Author> authors = new LinkedList<>();
        authors.add(genAuthor());
        authors.add(genAuthor());
        return authors;
    }
    public static Publisher genPublisher(){
        return new Publisher(TestUtils.genAbsRandomLong(),
                "Publisher Name " + genAbsRandomInt(),
                "Publisher Address " + genAbsRandomInt());
    }
    public static Classification getClassification(){
        return new Classification(TestUtils.genAbsRandomLong(),
                "Classification Name " + genAbsRandomInt());
    }
    public static List<Loan> genLoans() {
        List<Loan> loans = new LinkedList<>();
        loans.add(genLoan());
        loans.add(genLoan());
        loans.add(genLoan());
        loans.add(genLoan());
        loans.add(genLoan());
        return loans;
    }
    public static List<Loan> genLoans(int size) {
        List<Loan> loans = new LinkedList<>();
        for(int i =0; i < size ; i++){
            loans.add(genLoan());
        }
        return loans;
    }
}
