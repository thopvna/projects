package main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.specifications;

import main.com.jlaotsezu.library.data.Specification;

public class FindUserByUserName extends Specification {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public FindUserByUserName(String userName) {
        this.userName = userName;
    }
}
