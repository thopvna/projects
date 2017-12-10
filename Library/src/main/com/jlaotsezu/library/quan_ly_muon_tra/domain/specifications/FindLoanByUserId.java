package main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications;

import main.com.jlaotsezu.library.data.Specification;

public class FindLoanByUserId extends Specification {
    private int userId;
    public FindLoanByUserId(int userId) {
        this. userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
