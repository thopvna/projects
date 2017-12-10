package test.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.ConfirmBorrowBooksRequestsUseCase;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.NewMannerBorrowBooksUseCase;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.support.architecture.UseCase;
import main.com.jlaotsezu.library.support.communicate.ComplexResponse;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ConfirmBorrowBooksRequestsUseCaseTest {
    Repository<Integer, BorrowBooksRequest> borrowBooksRequestRepository;
    ConfirmBorrowBooksRequestsUseCase useCase;
    Repository<Integer, Loan> loanRepository;
    Repository<Integer, BorrowCard> borrowCardRepository;
    NewMannerBorrowBooksUseCase newMannerBorrowBooksUseCase;
    @Before
    public void setUp() throws Exception {
        borrowBooksRequestRepository = mock(Repository.class);
        loanRepository = mock(Repository.class);
        borrowCardRepository = mock(Repository.class);
        newMannerBorrowBooksUseCase = mock(NewMannerBorrowBooksUseCase.class);
        useCase = new ConfirmBorrowBooksRequestsUseCase(borrowBooksRequestRepository, loanRepository, borrowCardRepository, newMannerBorrowBooksUseCase);
    }
    @Test
    public void confirmNonExistingBorrowBooksRequest(){
        int requestId = TestUtils.genAbsInt();
        int borrowCardId = TestUtils.genAbsInt();
        int librarianId = TestUtils.genAbsInt();
        when(borrowBooksRequestRepository.fetchById(requestId)).thenReturn(null);
        useCase.setRequestValues(new ConfirmBorrowBooksRequestsUseCase.RequestValues(borrowCardId, requestId, librarianId));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ConfirmBorrowBooksRequestsUseCase.BORROW_BOOKS_REQUEST_NON_EXISTING);
            verify(borrowBooksRequestRepository).fetchById(requestId);
        });
        useCase.run();
    }
    @Test
    public void confirmExistingBorrowBooksRequest_ButAdreadyConfirmed(){
        BorrowBooksRequest request = TestUtils.genBorrowBookRequest();
        int borrowCardId = TestUtils.genAbsInt();
        int librarianId = TestUtils.genAbsInt();
        request.confirm();
        when(borrowBooksRequestRepository.fetchById(request.getBorrowBooksRequestId())).thenReturn(request);
        useCase.setRequestValues(new ConfirmBorrowBooksRequestsUseCase.RequestValues(borrowCardId, request.getBorrowBooksRequestId(), librarianId));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ConfirmBorrowBooksRequestsUseCase.BORROW_BOOKS_REQUEST_ALREADY_CONFIRMED);
            verify(borrowBooksRequestRepository).fetchById(request.getBorrowBooksRequestId());
        });
        useCase.run();
    }
    @Test
    public void confirmExistingBorrowBooksRequest_ButIsNotAccept(){
        BorrowBooksRequest request = TestUtils.genBorrowBookRequest();
        request.setAccept(false);
        int borrowCardId = TestUtils.genAbsInt();
        int librarianId = TestUtils.genAbsInt();
        when(borrowBooksRequestRepository.fetchById(request.getBorrowBooksRequestId())).thenReturn(request);
        useCase.setRequestValues(new ConfirmBorrowBooksRequestsUseCase.RequestValues(borrowCardId, request.getBorrowBooksRequestId(), librarianId));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ConfirmBorrowBooksRequestsUseCase.BORROW_BOOKS_REQUEST_IS_NOT_ACCEPT);
            verify(borrowBooksRequestRepository).fetchById(request.getBorrowBooksRequestId());
        });
        useCase.run();
    }
    @Test
    public void confirmExistingBorrowBooksRequest_ButBorrowCardIsNotOfBorrower(){
        BorrowBooksRequest request = TestUtils.genBorrowBookRequest();
        request.setAccept(true);

        List<BorrowCard> borrowCards = TestUtils.genBorrowCards(2);
        int borrowCardId = TestUtils.genAbsInt();
        when(borrowCardRepository.find(any())).thenReturn(borrowCards);

        int librarianId = TestUtils.genAbsInt();
        when(borrowBooksRequestRepository.fetchById(request.getBorrowBooksRequestId())).thenReturn(request);

        useCase.setRequestValues(new ConfirmBorrowBooksRequestsUseCase.RequestValues(borrowCardId, request.getBorrowBooksRequestId(), librarianId));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ConfirmBorrowBooksRequestsUseCase.BORROWCARD_IS_NOT_OF_BORROWER);
            verify(borrowBooksRequestRepository).fetchById(request.getBorrowBooksRequestId());
            verify(borrowCardRepository).find(any());
        });
        useCase.run();
    }
    @Test
    public void confirmExistingBorrowBooksRequest_BorrowBooksFailResponse(){
        BorrowBooksRequest request = TestUtils.genBorrowBookRequest();
        request.setAccept(true);
        List<BorrowCard> borrowCards = TestUtils.genBorrowCards(2);
        int borrowCardId = borrowCards.get(0).getBorrowCardId();
        when(borrowCardRepository.find(any())).thenReturn(borrowCards);
        int librarianId = TestUtils.genAbsInt();
        String message = "any";
        when(borrowBooksRequestRepository.fetchById(request.getBorrowBooksRequestId())).thenReturn(request);
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.fail(message));
            return null;
        }).when(newMannerBorrowBooksUseCase).run();

        when(borrowBooksRequestRepository.update(request)).thenReturn(true);

        useCase.setRequestValues(new ConfirmBorrowBooksRequestsUseCase.RequestValues(borrowCardId, request.getBorrowBooksRequestId(), librarianId));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), message);
            verify(borrowBooksRequestRepository).fetchById(request.getBorrowBooksRequestId());
            verify(borrowBooksRequestRepository).update(request);
            verify(newMannerBorrowBooksUseCase).run();
        });
        useCase.run();
    }

    @Test
    public void confirmExistingBorrowBooksRequest_InternalDbError(){
        BorrowBooksRequest request = TestUtils.genBorrowBookRequest();
        request.setAccept(true);
        List<BorrowCard> borrowCards = TestUtils.genBorrowCards(2);
        int borrowCardId = borrowCards.get(0).getBorrowCardId();
        when(borrowCardRepository.find(any())).thenReturn(borrowCards);
        int librarianId = TestUtils.genAbsInt();
        Loan loan = TestUtils.genLoan();
        when(borrowBooksRequestRepository.fetchById(request.getBorrowBooksRequestId())).thenReturn(request);
        when(borrowBooksRequestRepository.update(request)).thenReturn(false);
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new NewMannerBorrowBooksUseCase.ResponseValues(loan)));
            return null;
        }).when(newMannerBorrowBooksUseCase).run();
        useCase.setRequestValues(new ConfirmBorrowBooksRequestsUseCase.RequestValues(borrowCardId, request.getBorrowBooksRequestId(), librarianId));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), ConfirmBorrowBooksRequestsUseCase.CONFIRM_BORROW_BOOKS_REQUEST_FAIL_INTERNAL_DB_ERROR);
            verify(borrowBooksRequestRepository).fetchById(request.getBorrowBooksRequestId());
            verify(borrowBooksRequestRepository).update(request);
            verify(newMannerBorrowBooksUseCase).run();
            verify(loanRepository).deleteById(loan.getLoanId());
        });
        useCase.run();
    }
    @Test
    public void happyConfirmExistingBorrowBooksRequest(){
        BorrowBooksRequest request = TestUtils.genBorrowBookRequest();
        request.setAccept(true);
        List<BorrowCard> borrowCards = TestUtils.genBorrowCards(2);
        int borrowCardId = borrowCards.get(0).getBorrowCardId();
        when(borrowCardRepository.find(any())).thenReturn(borrowCards);
        int librarianId = TestUtils.genAbsInt();
        Loan loan = TestUtils.genLoan();
        when(borrowBooksRequestRepository.fetchById(request.getBorrowBooksRequestId())).thenReturn(request);
        doAnswer(invocation -> {
            UseCase.UseCaseCallback callback = invocation.getArgumentAt(2, UseCase.UseCaseCallback.class);
            callback.onCompleted(ComplexResponse.success(new NewMannerBorrowBooksUseCase.ResponseValues(loan)));
            return null;
        }).when(newMannerBorrowBooksUseCase).run();
        when(borrowBooksRequestRepository.update(request)).thenReturn(true);
        useCase.setRequestValues(new ConfirmBorrowBooksRequestsUseCase.RequestValues(borrowCardId, request.getBorrowBooksRequestId(), librarianId));
        useCase.setUseCaseCallback(response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            verify(borrowBooksRequestRepository).fetchById(request.getBorrowBooksRequestId());
            verify(borrowBooksRequestRepository).update(request);
            verify(newMannerBorrowBooksUseCase).run();
        });
        useCase.run();
    }
}