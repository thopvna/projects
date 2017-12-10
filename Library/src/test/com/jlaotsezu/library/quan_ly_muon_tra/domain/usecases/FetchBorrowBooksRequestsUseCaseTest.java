package test.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.FetchBorrowBooksRequestsUseCase;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.specifications.FindUserByKeyword;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import test.com.jlaotsezu.library.TestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FetchBorrowBooksRequestsUseCaseTest {

    private FetchBorrowBooksRequestsUseCase useCase;
    private Repository<Integer, BorrowBooksRequest> borrowBooksRequestRepository;
    private Repository<Integer, User> userRepository;

    @Before
    public void setUp() throws Exception {
        userRepository = mock(Repository.class);
        borrowBooksRequestRepository = mock(Repository.class);
        useCase = new FetchBorrowBooksRequestsUseCase(userRepository, borrowBooksRequestRepository);
    }
    @Test
    public void fetchBorrowBooksRequestsOfUserNonExisting(){
        User user = TestUtils.genUser();
        String keyword = user.getUserName();
        when(userRepository.find(any())).thenReturn(null);

        useCase.setRequestValues(new FetchBorrowBooksRequestsUseCase.RequestValues(keyword));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), FetchBorrowBooksRequestsUseCase.DONT_HAVE_ANY_USERS);
            verify(userRepository).find(any());
        });

        useCase.run();
    }
    @Test
    public void fetchBorrowBooksRequestsOfUserExisting_ButDontHaveAnyRequests(){
        User user = TestUtils.genUser();

        when(userRepository.find(any())).thenReturn(Collections.singletonList(user));
        when(borrowBooksRequestRepository.find(any())).thenReturn(null);

        useCase.setRequestValues(new FetchBorrowBooksRequestsUseCase.RequestValues(user.getUserName()));
        useCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), FetchBorrowBooksRequestsUseCase.DONT_HAVE_ANY_BORROW_BOOKS_REQUESTS);
            verify(userRepository).find(any());
            verify(borrowBooksRequestRepository).find(any());
        });

        useCase.run();
    }
    @Test
    public void fetchBorrowBooksRequestsOfUserExisting_HaveSomeRequests(){
        User user = TestUtils.genUser();
        List<BorrowBooksRequest> borrowBooksRequests = TestUtils.genBorrowBookRequests(2);

        when(userRepository.find(any())).thenReturn(Collections.singletonList(user));
        when(borrowBooksRequestRepository.find(any())).thenReturn(borrowBooksRequests);

        useCase.setRequestValues(new FetchBorrowBooksRequestsUseCase.RequestValues(user.getUserName()));
        useCase.setUseCaseCallback(response -> {
            assertTrue(response.getMessage(), response.isSuccess());
            assertNotNull(response.getPayload());
            Assertions.assertThat(response.getPayload().getBorrowBooksRequests()).isNotNull().isNotEmpty().isEqualTo(borrowBooksRequests);
            verify(userRepository).find(any());
            verify(borrowBooksRequestRepository).find(any());

        });

        useCase.run();
    }
}