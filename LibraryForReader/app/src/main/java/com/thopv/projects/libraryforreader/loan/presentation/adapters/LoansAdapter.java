package com.thopv.projects.libraryforreader.loan.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thopv.projects.libraryforreader.R;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 11/12/2017.
 */

public class LoansAdapter extends RecyclerView.Adapter<LoanViewHolder> {
    private List<Loan> mLoans;
    private LoanViewHolder.CancelLoanEvents cancelLoanEvents;
    private LoanViewHolder.ClickEvents clickEvents;
    public LoansAdapter(LoanViewHolder.CancelLoanEvents cancelLoanEvents, LoanViewHolder.ClickEvents clickEvents) {
        this.mLoans = new LinkedList<>();
        this.cancelLoanEvents = cancelLoanEvents;
        this.clickEvents = clickEvents;
    }

    @Override
    public LoanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_cardview, parent, false);
        return new LoanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LoanViewHolder holder, int position) {
        Loan loan = mLoans.get(position);
        holder.setLoan(loan, cancelLoanEvents, clickEvents);
    }

    public void setLoans(List<Loan> loans) {
        if(loans != null) {
            mLoans = loans;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mLoans.size();
    }
}
