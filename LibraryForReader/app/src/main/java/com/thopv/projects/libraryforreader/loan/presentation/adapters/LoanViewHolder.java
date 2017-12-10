package com.thopv.projects.libraryforreader.loan.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thopv.projects.libraryforreader.R;
import com.thopv.projects.libraryforreader.loan.domain.entity.Loan;
import com.thopv.projects.libraryforreader.support.utils.DateUtils;
import com.thopv.projects.libraryforreader.support.utils.ViewUtils;

/**
 * Created by thopv on 11/12/2017.
 */

public class LoanViewHolder extends RecyclerView.ViewHolder {
    private View container;
    private TextView loanStatusView, loanBookCountView, loanTimeBornView, loanLendTimeView, loanReturnTimeExpectedView;
    private ImageView moreImageView;
    private TextView loanReturnTimeView;
    private ImageView loanStatusIconView;

    public interface CancelLoanEvents {
        void shouldCancelLoan(long loanId);
    }
    public interface ClickEvents{
        void onClick(long loanId);
    }
    public LoanViewHolder(View itemView) {
        super(itemView);
        container = itemView.findViewById(R.id.loanContainer);
        loanStatusIconView = container.findViewById(R.id.loanStatusIcon);
        loanStatusView = container.findViewById(R.id.loanStatusView);
        loanBookCountView = container.findViewById(R.id.loanBookCountView);
        loanTimeBornView = container.findViewById(R.id.loanBornTimeView);
        loanLendTimeView = container.findViewById(R.id.lendTimeView);
        loanReturnTimeExpectedView = container.findViewById(R.id.loanReturnTimeExpectedView);
        loanReturnTimeView = container.findViewById(R.id.loanReturnTimeView);
        moreImageView = container.findViewById(R.id.moreImageView);

    }

    public void setLoan(Loan loan, CancelLoanEvents cancelLoanEvents, ClickEvents clickEvents){
        String status = !loan.isWasLend() ? "Trạng thái: Chờ xử lý" : !loan.isWasReturn() ? "Trạng thái: Đã nhận sách | Chưa trả" : "Trạng thái: Đã nhận sách | Đã trả";
        String loanBookCount = "Tổng cộng " + loan.getBookIds().size()  + " cuốn";
        String bornTime;
        try {
            bornTime = "Gửi: " + DateUtils.getDistanceFromNow(loan.getBornTime()) + "trước";
        } catch (Exception e) {
            bornTime = "Vừa gửi xong";
        }
        String lendTime;
        try {
            lendTime = "Nhận sách: " + DateUtils.getDistanceFromNow(loan.getStartTime()) + "trước";
        } catch (Exception e) {
            lendTime = "Vừa nhận sách";
        }
        String returnTimeExpected;
        boolean isExpired = System.currentTimeMillis() - loan.getReturnTimeExpected() > 0;
        returnTimeExpected = isExpired ? "Đã quá hạn trả " : "Hạn trả: " + DateUtils.getDate(loan.getReturnTimeExpected());

        String returnTime;
        returnTime = "Trả: " + DateUtils.getDate(loan.getReturnTime());

        int iconId = loan.isWasLend() ? R.drawable.ic_borrow_books_black : R.drawable.ic_pending;

        if(!loan.isWasLend()) {
            lendTime = "Chưa nhận sách";
            loanLendTimeView.setVisibility(View.GONE);
            loanReturnTimeExpectedView.setVisibility(View.GONE);
            moreImageView.setVisibility(View.VISIBLE);
        }
        else{
            loanLendTimeView.setVisibility(View.VISIBLE);
            loanReturnTimeExpectedView.setVisibility(View.VISIBLE);
            moreImageView.setVisibility(View.GONE);
        }
        if(loan.isWasReturn()){
            loanReturnTimeView.setVisibility(View.VISIBLE);
        }
        else{
            loanReturnTimeView.setVisibility(View.GONE);
        }

        long loanId = loan.getLoanId();
        loanStatusView.setText(status);
        loanBookCountView.setText(loanBookCount);
        loanTimeBornView.setText(bornTime);
        loanReturnTimeExpectedView.setText(returnTimeExpected);
        loanReturnTimeView.setText(returnTime);
        loanLendTimeView.setText(lendTime);
        loanStatusIconView.setImageDrawable(container.getResources().getDrawable(iconId));

        container.setOnClickListener(v -> {
            ViewUtils.disableView(v);
            clickEvents.onClick(loanId);
        });
        moreImageView.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(container.getContext(), v);
            Menu menu = popupMenu.getMenu();
            menu.add(0, 0, 0, "Hủy yêu cầu");
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == 0) {
                    cancelLoanEvents.shouldCancelLoan(loanId);
                }
                return true;
            });
            popupMenu.show();
        });
    }
}
