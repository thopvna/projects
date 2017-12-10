package main.com.jlaotsezu.library.quan_ly_the_muon.presentation.mappers;

import main.com.jlaotsezu.library.support.architecture.Mapper;
import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;
import main.com.jlaotsezu.library.quan_ly_the_muon.presentation.models.BorrowerModel;

public class BorrowerMapper implements Mapper<User, BorrowerModel> {
    @Override
    public BorrowerModel map(User user) {
        return new BorrowerModel.Builder()
                .setBorrowerId(user.getUserId())
                .setBorrowUserName(user.getUserName())
                .setBorrowerFullName(user.getFullName())
                .setBorrowerGender(user.getGender())
                .setBorrowerEmail(user.getEmail())
                .setBorrowerPhone(user.getPhone())
                .build();
    }
}
