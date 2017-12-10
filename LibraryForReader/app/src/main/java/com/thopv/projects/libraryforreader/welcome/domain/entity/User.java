package com.thopv.projects.libraryforreader.welcome.domain.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by thopv on 12/2/2017.
 */
public class User {
    private String userName;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private boolean isActive;
    public User(@NonNull String userName, String password, String fullName, String email, String phone, boolean isActive) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.isActive = isActive;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            return ((User) obj).getUserName().equalsIgnoreCase(getUserName());
        }
        return super.equals(obj);
    }
}
