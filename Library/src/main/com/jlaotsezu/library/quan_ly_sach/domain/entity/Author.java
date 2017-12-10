package main.com.jlaotsezu.library.quan_ly_sach.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorId = 0;
    private String authorName = null;
    private String authorAddress = null;
    private String authorBirthday = null;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Author){
            return authorId == ((Author) obj).getAuthorId()
                    && authorName.equalsIgnoreCase(((Author) obj).getAuthorName())
                    ;
        }
        return super.equals(obj);
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int id) {
        this.authorId = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String name) {
        this.authorName = name;
    }

    public String getAuthorAddress() {
        return authorAddress;
    }

    public void setAuthorAddress(String address) {
        this.authorAddress = address;
    }

    public String getAuthorBirthday() {
        return authorBirthday;
    }

    public void setAuthorBirthday(String birthday) {
        this.authorBirthday = birthday;
    }

}
