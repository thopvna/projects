package main.com.jlaotsezu.library.quan_ly_the_muon.domain.specifications;

import main.com.jlaotsezu.library.data.Specification;

public class FindBorrowCardByKeyword extends Specification {
    private String keyword;
    public FindBorrowCardByKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
