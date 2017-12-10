package main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications;

import main.com.jlaotsezu.library.data.Specification;

public class FindBorrowCardByUserId extends Specification {
    private int userId;

    public int getUserId() {
        return userId;
    }

    public FindBorrowCardByUserId(int userId) {
        this.userId = userId;
    }
}
