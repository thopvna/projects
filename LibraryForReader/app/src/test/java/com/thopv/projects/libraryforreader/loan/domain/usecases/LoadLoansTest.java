package com.thopv.projects.libraryforreader.loan.domain.usecases;

import com.thopv.projects.libraryforreader.TestUtils;
import com.thopv.projects.libraryforreader.data.source.repositories.LoanRepository;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by thopv on 12/3/2017.
 */
public class LoadLoansTest {

    LoanRepository loanRepository;
    LoadLoans loadLoans;

    @Before
    public void setUp() throws Exception {
        loanRepository = mock(LoanRepository.class);
        loadLoans = new LoadLoans(loanRepository);
    }
    @Test
    public void loadEmptyLoans(){
        when(loanRepository.fetchAll()).thenReturn(null);
        loadLoans.executeUseCase(new LoadLoans.RequestValues(), response -> {
            assertFalse(response.isSuccess());
            assertEquals(response.getMessage(), LoadLoans.LOANS_EMPTY_ERR);
        });
    }
    @Test
    public void happyLoadLoans(){
        List<Loan> loans = TestUtils.genLoans();
        when(loanRepository.fetchAll()).thenReturn(loans);
        loadLoans.executeUseCase(new LoadLoans.RequestValues(), response -> {
            assertTrue(response.isSuccess());
            assertNotNull(response.getPayload());
            assertThat(response.getPayload()
                    .getLoans())
                    .isNotNull()
                    .isNotEmpty()
                    .isEqualTo(loans);
        });
    }
}