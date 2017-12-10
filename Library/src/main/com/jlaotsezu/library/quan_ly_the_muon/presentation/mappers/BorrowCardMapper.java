package main.com.jlaotsezu.library.quan_ly_the_muon.presentation.mappers;

import main.com.jlaotsezu.library.support.architecture.Mapper;
import main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity.BorrowCard;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.models.BorrowCardModel;
import main.com.jlaotsezu.library.support.utils.DateUtils;

public class BorrowCardMapper implements Mapper<BorrowCard, BorrowCardModel> {
    @Override
    public BorrowCardModel map(BorrowCard borrowCard) {
        return new BorrowCardModel.Builder()
                .setBorrowCardId(borrowCard.getBorrowCardId())
                .setBorrowerId(borrowCard.getUser().getUserId())
                .setBorrowerName(borrowCard.getUser().getUserName())
                .setBorrowerFullName(borrowCard.getUser().getFullName())
                .setIssueTime(DateUtils.getBasicDate(borrowCard.getIssueTime()))
                .setExpireTime(DateUtils.getBasicDate(borrowCard.getExpiredTime()))
                .setDisableTime(borrowCard.isActive() ? "--/--/----" : DateUtils.getBasicDate(borrowCard.getRevokeTime()))
                .setStatus(borrowCard.isActive() ? "Active" : "Disable")
                .build();
    }
}
