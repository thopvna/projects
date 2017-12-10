package test.com.jlaotsezu.library.quan_ly_sach.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookImportRequest;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.CancelBookImportRequestUseCase;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CancelBookImportRequestUseCaseTest {

    private Repository<Integer, BookImportRequest> bookImportRequestRepository;
    private CancelBookImportRequestUseCase useCase;

    @Before
    public void setUp() throws Exception {
        bookImportRequestRepository = mock(Repository.class);
        useCase = new CancelBookImportRequestUseCase(bookImportRequestRepository);
    }
    @Test
    public void cancelNonExistingBookImportRequest(){
        int requestId = TestUtils.genAbsInt();
        when(bookImportRequestRepository.fetchById(requestId)).thenReturn(null);
        useCase.setRequestValues(new CancelBookImportRequestUseCase.RequestValues(requestId));
        useCase.setUseCaseCallback( response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), CancelBookImportRequestUseCase.BOOK_IMPORT_REQUEST_NOT_EXISTING);
            verify(bookImportRequestRepository).fetchById(requestId);
        });
        useCase.run();
    }
    @Test
    public void cancelExistingBookImportRequest_ButAdreadyConfirmed(){
        BookImportRequest request = TestUtils.genBookImportRequest();
        request.setConfirm(true);
        when(bookImportRequestRepository.fetchById(request.getBookImportRequestId())).thenReturn(request);
        useCase.setRequestValues(new CancelBookImportRequestUseCase.RequestValues(request.getBookImportRequestId()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), CancelBookImportRequestUseCase.BOOK_IMPORT_REQUEST_ADREADY_CONFIRMED);
            verify(bookImportRequestRepository).fetchById(request.getBookImportRequestId());
        });
        useCase.run();
    }
    @Test
    public void cancelExistingBookImportRequest_ButHaveInternalDbError(){
        BookImportRequest request = TestUtils.genBookImportRequest();
        when(bookImportRequestRepository.fetchById(request.getBookImportRequestId())).thenReturn(request);
        when(bookImportRequestRepository.deleteById(request.getBookImportRequestId())).thenReturn(false);
        useCase.setRequestValues(new CancelBookImportRequestUseCase.RequestValues(request.getBookImportRequestId()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), CancelBookImportRequestUseCase.INTERNAL_DATABASE_ERROR);
            verify(bookImportRequestRepository).fetchById(request.getBookImportRequestId());
            verify(bookImportRequestRepository).deleteById(request.getBookImportRequestId());
        });
        useCase.run();
    }
    @Test
    public void happyCancelBookImportRequest(){
        BookImportRequest request = TestUtils.genBookImportRequest();
        when(bookImportRequestRepository.fetchById(request.getBookImportRequestId())).thenReturn(request);
        when(bookImportRequestRepository.deleteById(request.getBookImportRequestId())).thenReturn(true);
        useCase.setRequestValues(new CancelBookImportRequestUseCase.RequestValues(request.getBookImportRequestId()));
        useCase.setUseCaseCallback(response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            verify(bookImportRequestRepository).fetchById(request.getBookImportRequestId());
            verify(bookImportRequestRepository).deleteById(request.getBookImportRequestId());
        });
        useCase.run();
    }
}