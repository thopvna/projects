package main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity;


public enum Role {
    USER, ADMINISTRATOR, LIBRARICIAN;
    Role fromString(String role){
        if(role.equalsIgnoreCase("ADMINISTRATOR")){
            return ADMINISTRATOR;
        }
        else if(role.equalsIgnoreCase("LIBRARICIAN")){
            return LIBRARICIAN;
        }
        else
            return USER;
    }
}
