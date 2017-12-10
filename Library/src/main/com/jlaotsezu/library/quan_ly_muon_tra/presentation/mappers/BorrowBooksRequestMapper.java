package main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.mappers;

import main.com.jlaotsezu.library.quan_ly_muon_tra.domain.entity.BorrowBooksRequest;
import main.com.jlaotsezu.library.quan_ly_muon_tra.presentation.models.BorrowBooksRequestModel;
import main.com.jlaotsezu.library.support.architecture.Mapper;
import main.com.jlaotsezu.library.support.utils.DateUtils;

public class BorrowBooksRequestMapper implements Mapper<BorrowBooksRequest, BorrowBooksRequestModel> {
    @Override
    public BorrowBooksRequestModel map(BorrowBooksRequest request) {

        return new BorrowBooksRequestModel.Builder()
                .setRequestId(request.getBorrowBooksRequestId())
                .setBorrowerId(request.getBorrower().getUserId())
                .setBorrowerUserName(request.getBorrower().getUserName())
                .setBorrowerFullName(request.getBorrower().getFullName())
                .setBookCopies(request.getBookCopies())
                .setBornTime(DateUtils.getBasicDate(request.getBornTime()))
                .setAcceptTime(DateUtils.getBasicDate(request.getAcceptTime()))
                .setConfirmTime(DateUtils.getBasicDate(request.getConfirmTime()))
                .setExpiredTime(DateUtils.getBasicDate(request.getExpiredTime()))
                .setStatus(!request.isAccept() ? "Chưa chấp nhận" : request.isConfirm() ? "Đã nhận sách" : !request.isExpired() ? "Chờ lấy sách" : "Hết hạn lấy")
                .build();
    }
}
