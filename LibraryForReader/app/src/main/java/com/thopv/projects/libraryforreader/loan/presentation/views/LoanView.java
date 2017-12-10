package com.thopv.projects.libraryforreader.loan.presentation.views;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thopv.projects.libraryforreader.R;
import com.thopv.projects.libraryforreader.dagger.usecase.UseCaseProvider;
import com.thopv.projects.libraryforreader.home.domain.entity.Book;
import com.thopv.projects.libraryforreader.home.presentation.adapters.BooksAdapter;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;
import com.thopv.projects.libraryforreader.loan.presentation.adapters.LoansAdapter;
import com.thopv.projects.libraryforreader.loan.presentation.contracts.LoanContract;
import com.thopv.projects.libraryforreader.loan.presentation.presenters.LoanPresenter;
import com.thopv.projects.libraryforreader.support.ctx.AppbarActivity;
import com.thopv.projects.libraryforreader.support.usecase.UseCaseHandler;

import java.util.List;

public class LoanView extends AppbarActivity implements LoanContract.View{

    private LoanPresenter loanPresenter;
    private LoansAdapter loansAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_view);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        setupLoansView();
        initPresenter();
    }

    private void initPresenter() {
        UseCaseProvider useCaseProvider = UseCaseProvider.getInstance(this);
        loanPresenter = new LoanPresenter(this,
                UseCaseHandler.getInstance(), useCaseProvider.getCancelLoan(),
                useCaseProvider.getLoadLoans(),
                useCaseProvider.getLoadLoanDetails());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loanPresenter.loadLoans();
    }

    private void setupLoansView() {
        RecyclerView loansView = findViewById(R.id.loansView);
        LinearLayoutManager loansViewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        loansView.setLayoutManager(loansViewLayoutManager);
        loansAdapter = new LoansAdapter(loanId -> {
            loanPresenter.cancelLoan(loanId);
        }, loanId -> {
            loanPresenter.loadDetails(loanId);
        });
        loansView.setAdapter(loansAdapter);
    }
    @Override
    public void showLoans(List<Loan> loans) {
        loansAdapter.setLoans(loans);
    }
    private AlertDialog detailsDialog;
    private BooksAdapter detailBooksAdapter;
    @Override
    public void showLoanDetails(List<Book> books) {
        if(detailsDialog == null) {
            View container = getLayoutInflater().inflate(R.layout.dialog_recycler_view_content, null, false);
            RecyclerView content  = container.findViewById(R.id.booksView);
            detailBooksAdapter = new BooksAdapter((bookId, view) -> {
                showError("Unsupport operation.");
            });
            content.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            content.setAdapter(detailBooksAdapter);
            detailsDialog = new AlertDialog.Builder(this)
                    .setTitle("Chi tiết danh sách Sách.")
                    .setView(container)
                    .setCancelable(false)
                    .setPositiveButton("Đóng", (dialog, which) ->{
                        dialog.cancel();
                    })
                    .create();
        }
        detailBooksAdapter.setBooks(books);
        detailsDialog.show();
    }
}
