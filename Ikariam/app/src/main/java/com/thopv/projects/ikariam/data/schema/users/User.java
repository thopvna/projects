package com.thopv.projects.ikariam.data.schema.users;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by thopv on 10/29/2017.
 */
@Entity
public class User {
    @PrimaryKey
    @NonNull
    private String userName;
    private String password;
    private String fullName;
    private int party;

    public User(@NonNull String userName, String password, String fullName, int party) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.party = party;
    }



    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setParty(int party) {
        this.party = party;
    }

    public static class Builder{
        private String userName;
        private String password;
        private String fullName;
        private int party;
        public User build(){

            return new User(userName, password, fullName, party);
        }
        public Builder(){
            party = -1;
        }
        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }
        @Deprecated
        public Builder setParty(int party) {
            this.party = party;
            return this;
        }
    }

    public int getParty() {
        return party;
    }

    public boolean isNeutralParty(){
        return party == -1;
    }
    public boolean isRed(){
        return party == 1;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }
}
