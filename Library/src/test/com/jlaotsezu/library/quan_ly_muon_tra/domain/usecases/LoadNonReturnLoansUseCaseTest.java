package test.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases;

import main.com.jlaotsezu.library.data.Repository;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.Loan;
import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.usecases.LoadNonReturnLoansUseCase;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
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

public class LoadNonReturnLoansUseCaseTest {

    private Repository<Integer, Loan> loanRepository;
    private Repository<Integer, User> userRepository;
    private LoadNonReturnLoansUseCase loadNonReturnLoansUseCase;

    @Before
    public void setUp() throws Exception {
        loanRepository = mock(Repository.class);
        userRepository = mock(Repository.class);
        loadNonReturnLoansUseCase = new LoadNonReturnLoansUseCase(loanRepository, userRepository);
    }
    @Test
    public void loadNonReturnLoans_EMptykeyword(){
        String keyword = "";
        loadNonReturnLoansUseCase.setRequestValues(new LoadNonReturnLoansUseCase.RequestValues(keyword));
        loadNonReturnLoansUseCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), LoadNonReturnLoansUseCase.INPUT_INVALID_KEYWORD_EMPTY);
        });
        loadNonReturnLoansUseCase.run();
    }
    @Test
    public void loadNonReturnLoansOf_NonExistingUser(){
        User user = TestUtils.genUser();
        when(userRepository.find(any())).thenReturn(null);

        loadNonReturnLoansUseCase.setRequestValues(new LoadNonReturnLoansUseCase.RequestValues(user.getUserName()));
        loadNonReturnLoansUseCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), LoadNonReturnLoansUseCase.DONT_HAVE_ANY_USER_MATCHING);
            verify(userRepository).find(any());
        });

        loadNonReturnLoansUseCase.run();
    }
    @Test
    public void loadNonReturnLoansOf_ExistingUser_ButDontHaveAnyLoans(){
        User user = TestUtils.genUser();
        when(userRepository.find(any())).thenReturn(Collections.singletonList(user));
        when(loanRepository.find(any())).thenReturn(null);

        loadNonReturnLoansUseCase.setRequestValues(new LoadNonReturnLoansUseCase.RequestValues(user.getUserName()));
        loadNonReturnLoansUseCase.setUseCaseCallback(response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), LoadNonReturnLoansUseCase.DONT_HAVE_ANY_NON_RETURN_LOANS);
            verify(userRepository).find(any());
            verify(loanRepository).find(any());
        });

        loadNonReturnLoansUseCase.run();
    }

    @Test
    public void loadNonReturnLoansOf_ExistingUser_HaveSomeNonReturnLoans(){
        User user = TestUtils.genUser();

        List<Loan> loans = TestUtils.genNonReturnLoans(2);

        when(userRepository.find(any())).thenReturn(Collections.singletonList(user));
        when(loanRepository.find(any())).thenReturn(loans);

        loadNonReturnLoansUseCase.setRequestValues(new LoadNonReturnLoansUseCase.RequestValues(user.getUserName()));
        loadNonReturnLoansUseCase.setUseCaseCallback(response -> {
            assertTrue(response.isSuccess());
            assertNotNull(response.getPayload());
            Assertions.assertThat(response.getPayload().getLoans()).isNotNull().isNotEmpty().isEqualTo(loans);
            verify(userRepository).find(any());
            verify(loanRepository).find(any());
        });

        loadNonReturnLoansUseCase.run();
    }

}