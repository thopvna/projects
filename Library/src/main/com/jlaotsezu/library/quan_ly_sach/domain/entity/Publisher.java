package main.com.jlaotsezu.library.quan_ly_sach.domain.entity;

import javax.persistence.*;

@Entity
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int publisherId = 0;
    private String publisherName = null;
    private String publisherHeadQuarter = null;



    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int id) {
        this.publisherId = id;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String name) {
        this.publisherName = name;
    }

    public String getPublisherHeadQuarter() {
        return publisherHeadQuarter;
    }

    public void setPublisherHeadQuarter(String headquarter) {
        this.publisherHeadQuarter = headquarter;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Publisher){
            return publisherId == ((Publisher) obj).getPublisherId()
                    && publisherName.equalsIgnoreCase(((Publisher) obj).getPublisherName());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return publisherName;
    }
}
