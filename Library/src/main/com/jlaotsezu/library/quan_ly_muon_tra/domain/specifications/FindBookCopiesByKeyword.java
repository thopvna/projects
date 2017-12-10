package main.com.jlaotsezu.library.quan_ly_muon_tra.domain.specifications;

import main.com.jlaotsezu.library.data.Specification;

public class FindBookCopiesByKeyword extends Specification{
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public FindBookCopiesByKeyword(String keyword) {
        this.keyword = keyword;
    }
}
