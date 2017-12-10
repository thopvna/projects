package main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.specifications;

import main.com.jlaotsezu.library.data.Specification;

public class FindUserByKeyword extends Specification {
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public FindUserByKeyword(String keyword) {
        this.keyword = keyword;
    }
}
