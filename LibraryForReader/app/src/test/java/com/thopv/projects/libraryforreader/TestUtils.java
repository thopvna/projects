package com.thopv.projects.libraryforreader;

import com.thopv.projects.libraryforreader.home.domain.entity.Author;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.domain.entity.Classification;
import com.thopv.projects.libraryforreader.home.domain.entity.Publisher;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;
import com.thopv.projects.libraryforreader.support.utils.DateUtils;

import java.util.Collections;
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
                .setPublisher(new Publisher(0, "Publisher Name " + genAbsRandomInt(), "Publisher Address " + genAbsRandomInt()))
                .setClassification(new Classification(0, "Classification Name " + genAbsRandomInt()))
                .setAuthors(Collections.singletonList(new Author(0, "Author Name " + genAbsRandomInt(), System.currentTimeMillis(), System.currentTimeMillis() + genAbsRandomLong(), "Author Address " + genAbsRandomInt())))
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

    public static List<Loan> genLoans() {
        List<Loan> loans = new LinkedList<>();
        loans.add(genLoan());
        loans.add(genLoan());
        loans.add(genLoan());
        loans.add(genLoan());
        loans.add(genLoan());
        return loans;
    }
}
