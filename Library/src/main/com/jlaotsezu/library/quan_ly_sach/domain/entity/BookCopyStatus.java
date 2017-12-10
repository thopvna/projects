package main.com.jlaotsezu.library.quan_ly_sach.domain.entity;

public enum BookCopyStatus {
    BORROWABLE,
    REFERENCING,
    BORROWED;
    static BookCopyStatus fromString(String status){
        if(status.equalsIgnoreCase("REFERENCING")){
            return REFERENCING;
        }
        else if(status.equalsIgnoreCase("BORROWED")){
            return BORROWED;
        }
        else
            return BORROWABLE;
    }

    @Override
    public String toString() {
        if(this == REFERENCING){
            return "Đang tham khảo";
        }
        else if(this == BORROWABLE){
            return "Có thể mượn";
        }
        else if(this == BORROWED){
            return "Đã bị mượn";
        }
        return super.toString();
    }
}
