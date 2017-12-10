package main.com.jlaotsezu.library.quan_ly_sach.domain.specifications;

import main.com.jlaotsezu.library.data.Specification;

public class FindBookByKeyword extends Specification{
    private String keyword;

    public FindBookByKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
