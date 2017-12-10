package test.com.jlaotsezu.library;

import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.Role;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.*;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;

import java.util.*;

public class TestUtils {
    private static final Random random = new Random();
    public static User genUser(){
        User user = new User();
        user.setUserId(genAbsInt());
        user.setUserName("Username " + genAbsInt());
        user.setPassword("Password " + genAbsInt());
        user.setFullName("Fullname " + genAbsInt());
        user.setEmail("Email " + genAbsInt());
        user.setPhone("Phone " + genAbsInt());
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);
        return user;
    }
    public static List<Book> genBooks(int size){
        List<Book> books = new LinkedList<>();
        for(int i =0; i < size; i++){
            books.add(genBook());
        }
        return books;
    }

    public static Book genBook(){
        return new Book.Builder()
                .id(genAbsInt())
                .title("Title " + genAbsInt())
                .briefcontent("Brief content " + genAbsInt())
                .publisher(genPublisher())
                .classification(genClassification())
                .authors(genAuthors())
                .publishYear(2000)
                .build();
    }
    private static Author genAuthor(){
        Author author = new Author();
        author.setAuthorId(genAbsInt());
        author.setAuthorName("Author name " + genAbsInt());
        author.setAuthorAddress("Author address " + genAbsInt());
        author.setAuthorBirthday("Author Birthday " + genAbsInt());
        return author;
    }
    private static List<Author> genAuthors(){
        List<Author> authors = new LinkedList<>();
        authors.add(genAuthor());
        authors.add(genAuthor());
        return authors;
    }
    private static Classification genClassification(){
        Classification classification = new Classification();
        classification.setClassificationId(genAbsInt());
        classification.setClassificationName("Classification name " + genAbsInt());
        return classification;
    }
    private static Publisher genPublisher(){
        Publisher publisher = new Publisher();
        publisher.setPublisherId(genAbsInt());
        publisher.setPublisherName("Publisher name" + genAbsInt());
        publisher.setPublisherHeadQuarter("Publisher headquarter " + genAbsInt());
        return publisher;
    }
    public static int genAbsInt(){
        return Math.abs(random.nextInt());
    }
    public static long genAbsLong(){
        return Math.abs(random.nextLong());
    }

    public static List<BookCopy> genBorrowableBookCopies(int size) {
        List<BookCopy> bookCopies = new LinkedList<>();
        for(int i = 0; i < size; i++){
            bookCopies.add(genBorrowableBookCopy());
        }
        return bookCopies;
    }
    public static List<BookCopy> genBorrowedBookCopies(int size) {
        List<BookCopy> bookCopies = new LinkedList<>();
        for(int i = 0; i < size; i++){
            bookCopies.add(genBorrowedBookCopy());
        }
        return bookCopies;
    }

    public static BookCopy genBorrowableBookCopy() {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setBookCopyId(genAbsInt());
        bookCopy.setBook(genBook());
        bookCopy.setBookCopyStatus(BookCopyStatus.BORROWABLE);
        bookCopy.setBookCopyPrice(0);
        bookCopy.setBookCopyType(BookCopyType.BORROWABLE);
        return bookCopy;
    }
    public static BookCopy genBorrowedBookCopy() {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setBookCopyId(genAbsInt());
        bookCopy.setBook(genBook());
        bookCopy.setBookCopyStatus(BookCopyStatus.BORROWED);
        bookCopy.setBookCopyPrice(0);
        bookCopy.setBookCopyType(BookCopyType.BORROWABLE);
        return bookCopy;
    }
    public static BookCopy genReferencingBookCopy() {
        BookCopy bookCopy = new BookCopy();
        bookCopy.setBookCopyId(genAbsInt());
        bookCopy.setBook(genBook());
        bookCopy.setBookCopyStatus(BookCopyStatus.REFERENCING);
        bookCopy.setBookCopyPrice(0);
        bookCopy.setBookCopyType(BookCopyType.REFERENCE);
        return bookCopy;
    }
    public static User genLibrarian(){
        User user = genUser();
        Set<Role> roles = new HashSet<>();
        roles.add(Role.LIBRARICIAN);
        user.setRoles(roles);
        return user;
    }
    public static User genBorrower(){
        User user = genUser();
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);
        return user;
    }

    public static List<BorrowCard> genBorrowCardsExpired(int size) {
        List<BorrowCard> borrowCards = new LinkedList<>();

        for(int i =0; i < size ; i++){
            BorrowCard borrowCard = genBorrowCard();
            borrowCard.setExpiredTime(0);
            borrowCards.add(borrowCard);
        }

        return borrowCards;
    }
    public static List<BorrowCard> genBorrowCards(int size) {
        List<BorrowCard> borrowCards = new LinkedList<>();

        for(int i =0; i < size ; i++){
            BorrowCard borrowCard = genBorrowCard();
            borrowCards.add(borrowCard);
        }

        return borrowCards;
    }
    public static BorrowCard genBorrowCard(){
        BorrowCard borrowCard = new BorrowCard.Builder()
                .setUser(genUser())
                .build();
        borrowCard.setBorrowCardId(genAbsInt());
        return borrowCard;
    }

    public static List<Loan> genNonReturnLoans(int size) {
        List<Loan> loans = new LinkedList<>();
        for(int i =0; i < size; i++){
            loans.add(genLoan());
        }
        return loans;
    }
    public static Loan genLoan(){
        Loan loan = new Loan();
        loan.setLoanId(genAbsInt());
        loan.setBookCopies(genBorrowedBookCopies(5));
        loan.setBorrower(genBorrower());
        loan.setLibrarian(genLibrarian());
        loan.setReturn(false);
        loan.setFee(genAbsInt());
        return loan;
    }

    public static List<BorrowBooksRequest> genBorrowBookRequests(int size) {
        List<BorrowBooksRequest> borrowBooksRequests = new LinkedList<>();
        for(int i =0; i < size; i++){
            borrowBooksRequests.add(genBorrowBookRequest());
        }
        return borrowBooksRequests;
    }

    public static BorrowBooksRequest genBorrowBookRequest() {
        BorrowBooksRequest borrowBooksRequest = new BorrowBooksRequest();
        borrowBooksRequest.setBorrowBooksRequestId(genAbsInt());
        borrowBooksRequest.setBorrower(genBorrower());
        borrowBooksRequest.setBookCopies(genBorrowableBookCopies(4));
        borrowBooksRequest.setConfirm(false);
        borrowBooksRequest.setBornTime(System.currentTimeMillis());
        return borrowBooksRequest;
    }

    public static List<BookImportRequest> genBookImportRequests(int size) {
        List<BookImportRequest> requests = new LinkedList<>();
        for(int i =0; i < size; i++){
            requests.add(genBookImportRequest());
        }
        return requests;
    }
    public static BookImportRequest genBookImportRequest() {
        BookImportRequest bookImportRequest = new BookImportRequest();
        bookImportRequest.setBookImportRequestId(genAbsInt());
        bookImportRequest.setBooksAmounts(genBookAmounts(3));
        bookImportRequest.setLibrarian(genLibrarian());
        bookImportRequest.setBornTime(System.currentTimeMillis());
        bookImportRequest.setConfirm(false);
        return bookImportRequest;
    }
    public static BookAmount genBookAmount(){
        BookAmount bookAmount = new BookAmount();
        bookAmount.setBook(genBook());
        return bookAmount;
    }
    public static List<BookAmount> genBookAmounts(int size){
        List<BookAmount> bookAmounts = new LinkedList<>();
        for(int i =0; i < size; i++){
            bookAmounts.add(genBookAmount());
        }
        return bookAmounts;
    }

    public static List<Integer> genInts(int size) {
        List<Integer> result = new LinkedList<>();
        for(int i = 0; i < size; i++){
            result.add(genAbsInt());
        }
        return result;
    }

    public static List<Loan> genLoans(int size) {
        List<Loan> loans = new LinkedList<>();
        for(int i =0; i < size; i++){
            loans.add(genLoan());
        }
        return loans;
    }
}
