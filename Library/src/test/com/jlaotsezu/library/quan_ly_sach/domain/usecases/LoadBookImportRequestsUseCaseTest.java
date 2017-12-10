package test.com.jlaotsezu.library.quan_ly_sach.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_sach.domain.entity.BookImportRequest;
import main.com.jlaotsezu.library.quan_ly_sach.domain.usecases.LoadBookImportRequestsUseCase;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoadBookImportRequestsUseCaseTest {

    private LoadBookImportRequestsUseCase useCase;
    private Repository<Integer, BookImportRequest> bookImportRequestRepository;
    private Repository<Integer, User> userRepository;

    @Before
    public void setup(){
        userRepository = mock(Repository.class);
        bookImportRequestRepository = mock(Repository.class);
        useCase = new LoadBookImportRequestsUseCase(bookImportRequestRepository, userRepository);
    }
    @Test
    public void loadRequestOfNonExistingLibrarian(){
        User user = TestUtils.genLibrarian();
        when(userRepository.fetchById(user.getUserId())).thenReturn(null);

        useCase.setRequestValues(new LoadBookImportRequestsUseCase.RequestValues(user.getUserId()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), LoadBookImportRequestsUseCase.DON_T_HAVE_ANY_LIBRARIAN_MATCHING);
            verify(userRepository).fetchById(user.getUserId());
        });

        useCase.run();
    }
    @Test
    public void loadRequestOfNonLibrarianUser(){
        User user = TestUtils.genUser();
        when(userRepository.fetchById(user.getUserId())).thenReturn(user);

        useCase.setRequestValues(new LoadBookImportRequestsUseCase.RequestValues(user.getUserId()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), LoadBookImportRequestsUseCase.CURRENT_USER_IS_NOT_A_LIBRARIAN);
            verify(userRepository).fetchById(user.getUserId());
        });

        useCase.run();
    }
    @Test
    public void loadRequestOfLibrarian_ButDontHaveANyRequests(){
        User librarian = TestUtils.genLibrarian();
        when(userRepository.fetchById(librarian.getUserId())).thenReturn(librarian);
        when(bookImportRequestRepository.find(any())).thenReturn(null);
        useCase.setRequestValues(new LoadBookImportRequestsUseCase.RequestValues(librarian.getUserId()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), LoadBookImportRequestsUseCase.LIBRARIAN_DON_T_HAVE_ANY_BOOK_IMPORT_REQUEST);
            verify(userRepository).fetchById(librarian.getUserId());
            verify(bookImportRequestRepository).find(any());
        });

        useCase.run();
    }
    @Test
    public void happyLoadRequests(){
        List<BookImportRequest> requests = TestUtils.genBookImportRequests(2);
        User librarian = TestUtils.genLibrarian();
        when(userRepository.fetchById(librarian.getUserId())).thenReturn(librarian);
        when(bookImportRequestRepository.find(any())).thenReturn(requests);
        useCase.setRequestValues(new LoadBookImportRequestsUseCase.RequestValues(librarian.getUserId()));
        useCase.setUseCaseCallback(response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            verify(userRepository).fetchById(librarian.getUserId());
            verify(bookImportRequestRepository).find(any());
        });

        useCase.run();
    }
}