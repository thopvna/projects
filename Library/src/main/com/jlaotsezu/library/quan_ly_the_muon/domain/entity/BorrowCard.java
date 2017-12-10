package main.com.jlaotsezu.library.quan_ly_the_muon.domain.entity;


import main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity.User;

import javax.persistence.*;

@Entity
public class BorrowCard {
    @Id
    @Column(name = "borrowCardId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int borrowCardId;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private long issueTime = 0L;
    private long expiredTime = 0L;
    private long revokeTime = 0L;

    private int status = 1; // =1 active, 0 - deactive

    public long getExpiredTime() {
        return expiredTime;
    }

    public long getRevokeTime() {
        return isActive() ? 0 : revokeTime;
    }

    public String getStatusString(){
        return status == 1 ? "Active" : "De-Active";
    }
    public String getCounterStatusString(){
        return status != 1 ? "Active" : "De-Active";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BorrowCard){
            if(borrowCardId != ((BorrowCard) obj).getBorrowCardId()) return false;
            if(!user.equals(((BorrowCard) obj).getUser())) return false;
            return true;
        }
        return super.equals(obj);
    }

    public void disable() {
        this.status = 0;
    }

    public static class Builder{
        private User user;
        private long issueTime;
        private long revokeTime;
        private int status;
        public Builder(){
            revokeTime = 0;
            issueTime = System.currentTimeMillis();
            this.status = 1;
        }
        public BorrowCard build(){
            BorrowCard borrowCard = new BorrowCard();
            borrowCard.user = user;
            borrowCard.issueTime = issueTime;
            borrowCard.revokeTime = revokeTime;
            borrowCard.expiredTime = issueTime + 365 * 24 * 60 * 60 * 1000L ;
            revokeTime = 0;
            borrowCard.status = status;
            return borrowCard;
        }
        @Deprecated
        public Builder setRevokeTime(long revokeTime) {
            this.revokeTime = revokeTime;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }
        @Deprecated
        public Builder setIssueTime(long issueTime) {
            this.issueTime = issueTime;
            return this;
        }
        @Deprecated
        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }
    }

    public boolean isExpired(){
        return expiredTime < System.currentTimeMillis();
    }

    public void extend(){
        expiredTime = System.currentTimeMillis() + 365 * 24 * 60 * 60 * 1000L;
    }

    public long getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(long issueTime) {
        this.issueTime = issueTime;
    }

    public void setExpiredTime(long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void deActive(){
        status = 0;
        revokeTime = System.currentTimeMillis();
    }

    public boolean isActive(){
        return status == 1;
    }
    public void active(){
        status = 1;
    }

    public int getBorrowCardId() {
        return borrowCardId;
    }

    public void setBorrowCardId(int id) {
        this.borrowCardId = id;
    }

    public User getUser() {
        return user;
    }
    public int getBorrowerId(){
        return user.getUserId();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
