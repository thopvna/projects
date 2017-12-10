package main.com.jlaotsezu.library.quan_ly_sach.domain.entity;

public enum BookCopyType {
    REFERENCE,
    BORROWABLE;
    public static BookCopyType fromString(String type){
        if(type.equalsIgnoreCase("REFERENCE"))
            return REFERENCE;
        else
            return BORROWABLE;
    }

    @Override
    public String toString() {
        if(this == REFERENCE){
            return "Sách tham khảo";
        }
        else if(this == BORROWABLE){
            return "Sách cho mượn";
        }
        return super.toString();
    }
}
