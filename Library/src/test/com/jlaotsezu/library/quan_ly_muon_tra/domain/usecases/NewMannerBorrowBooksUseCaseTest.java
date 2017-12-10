package test.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications.FindLoanByUserId;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.NewMannerBorrowBooksUseCase;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookCopy;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class NewMannerBorrowBooksUseCaseTest {
    NewMannerBorrowBooksUseCase usecase;
    private Repository<Integer, User> userRepository;
    private Repository<Integer, BookCopy> bookCopyRepository;
    private Repository<Integer, BorrowCard> borrowCardRepository;
    private Repository<Integer, Loan> loanRepository;

    @Before
    public void setUp() throws Exception {
        userRepository = mock(Repository.class);
        bookCopyRepository = mock(Repository.class);
        borrowCardRepository = mock(Repository.class);
        loanRepository = mock(Repository.class);
        usecase = new NewMannerBorrowBooksUseCase(userRepository, bookCopyRepository, borrowCardRepository, loanRepository);
    }

    @Test
    public void borrowWithEmptyBooksCopy(){
        User librarian = TestUtils.genLibrarian();
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        usecase.setRequestValues(new NewMannerBorrowBooksUseCase.RequestValues(librarian.getUserId(), borrowCard.getBorrowCardId(), null));
        usecase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), NewMannerBorrowBooksUseCase.INPUT_INVALID_DON_T_HAVE_ANY_BOOK_COPY);
        });
        usecase.run();
    }
    @Test
    public void borrowWithBeyondBooksCopyAmount(){
        User librarian = TestUtils.genLibrarian();
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        List<Integer> bookCopies = TestUtils.genInts(7);

        usecase.setRequestValues(new NewMannerBorrowBooksUseCase.RequestValues(librarian.getUserId(), borrowCard.getBorrowCardId(), bookCopies));
        usecase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), NewMannerBorrowBooksUseCase.INPUT_INVALID_BOOK_COPY_AMOUNT_5);
        });
        usecase.run();
    }
    @Test
    public void borrowWithBorrowCardNotExisting(){
        User librarian = TestUtils.genLibrarian();
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        List<Integer> bookCopies = TestUtils.genInts(5);

        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(null);
        when(userRepository.fetchById(librarian.getUserId())).thenReturn(librarian);

        usecase.setRequestValues(new NewMannerBorrowBooksUseCase.RequestValues(librarian.getUserId(), borrowCard.getBorrowCardId(), bookCopies));
        usecase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), NewMannerBorrowBooksUseCase.BORROWCARD_IS_NOT_EXISTING);
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
        });

        usecase.run();
    }
    @Test
    public void borrowWithLibrarianDontExisting(){
        User librarian = TestUtils.genLibrarian();
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        List<Integer> bookCopies = TestUtils.genInts(5);

        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(borrowCard);
        when(userRepository.fetchById(librarian.getUserId())).thenReturn(null);

        usecase.setRequestValues(new NewMannerBorrowBooksUseCase.RequestValues(librarian.getUserId(), borrowCard.getBorrowCardId(), bookCopies));
        usecase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), NewMannerBorrowBooksUseCase.LIBRARIAN_IS_NOT_EXISTING);
            verify(userRepository).fetchById(librarian.getUserId());
        });

        usecase.run();
    }
    @Test
    public void borrowWithLibrarianDontHaveRoleLibrarian(){
        User librarian = TestUtils.genBorrower();
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        List<Integer> bookCopies = TestUtils.genInts(5);

        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(borrowCard);
        when(userRepository.fetchById(librarian.getUserId())).thenReturn(librarian);

        usecase.setRequestValues(new NewMannerBorrowBooksUseCase.RequestValues(librarian.getUserId(), borrowCard.getBorrowCardId(), bookCopies));
        usecase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), NewMannerBorrowBooksUseCase.USER_WITH_LIBRARIAN_ID_IS_NOT_A_LIRARIAN);
            verify(userRepository).fetchById(librarian.getUserId());
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
        });

        usecase.run();
    }
    @Test
    public void borrowWith_BorrowerBorrowing(){
        User librarian = TestUtils.genLibrarian();
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        List<Integer> bookCopies = TestUtils.genInts(5);
        Loan loan = TestUtils.genLoan();

        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(borrowCard);
        when(userRepository.fetchById(librarian.getUserId())).thenReturn(librarian);
        when(loanRepository.find(any())).thenReturn(Collections.singletonList(loan));

        usecase.setRequestValues(new NewMannerBorrowBooksUseCase.RequestValues(librarian.getUserId(), borrowCard.getBorrowCardId(), bookCopies));
        usecase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), NewMannerBorrowBooksUseCase.BORROWER_ALREADY_BORROWING);
            verify(userRepository).fetchById(librarian.getUserId());
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
            verify(loanRepository, atLeastOnce()).find(any());
        });

        usecase.run();
    }
    @Test
    public void borrowWithBorrowCardExpired(){
        User librarian = TestUtils.genLibrarian();
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        borrowCard.setExpiredTime(0);
        List<Integer> bookCopies = TestUtils.genInts(5);

        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(borrowCard);
        when(userRepository.fetchById(librarian.getUserId())).thenReturn(librarian);
        when(loanRepository.find(new FindLoanByUserId(borrowCard.getBorrowerId()))).thenReturn(null);

        usecase.setRequestValues(new NewMannerBorrowBooksUseCase.RequestValues(librarian.getUserId(), borrowCard.getBorrowCardId(), bookCopies));
        usecase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), NewMannerBorrowBooksUseCase.BORROW_CARD_IS_EXPIRED);
            verify(userRepository).fetchById(librarian.getUserId());
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
        });

        usecase.run();
    }
    @Test
    public void borrowWithBorrowCardDisabled(){
        User librarian = TestUtils.genLibrarian();
        BorrowCard borrowCard = TestUtils.genBorrowCard();
        borrowCard.setExpiredTime(System.currentTimeMillis() + 36000);
        borrowCard.disable();
        List<Integer> bookCopies = TestUtils.genInts(5);

        when(borrowCardRepository.fetchById(borrowCard.getBorrowCardId())).thenReturn(borrowCard);
        when(userRepository.fetchById(librarian.getUserId())).thenReturn(librarian);
        when(loanRepository.find(new FindLoanByUserId(borrowCard.getBorrowerId()))).thenReturn(null);

        usecase.setRequestValues(new NewMannerBorrowBooksUseCase.RequestValues(librarian.getUserId(), borrowCard.getBorrowCardId(), bookCopies));
        usecase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), NewMannerBorrowBooksUseCase.BORROW_CARD_IS_DISABLED);
            verify(userRepository).fetchById(librarian.getUserId());
            verify(borrowCardRepository).fetchById(borrowCard.getBorrowCardId());
        });

        usecase.run();
    }
}