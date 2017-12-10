package main.com.jlaotsezu.library.quan_ly_nguoi_dung.domain.entity;

import com.google.gson.Gson;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String userName;
    private String password;
    private String fullName;
    private int gender;
    private String phone;
    private String email;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "userRoles", joinColumns = @JoinColumn(name = "userId"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Set<Role> roles;
    public User(){

    }

    public User(String userName, String password, String fullName, int gender, String phone, String email, Set<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.roles = roles;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User){
            return userId == ((User) obj).getUserId()
                    && userName.equalsIgnoreCase(((User) obj).getUserName())
                    && password.equalsIgnoreCase(((User) obj).getPassword())
                    ;
        }
        return super.equals(obj);
    }

    public static class Builder{
        private String userName = "";
        private String password = "";
        private String fullName = "";
        private int gender = 1;
        private String phone = "";
        private String email = "";
        private Set<Role> roles;

        public User build(){
            if(roles == null){
                roles = new HashSet<>();
                roles.add(Role.USER);
            }
            return new User(userName, password, fullName, gender, phone, email, roles);
        }


        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder setGender(int gender) {
            this.gender = gender;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        @Deprecated
        public Builder setRole(Role role) {
            this.roles = new HashSet<>();
            roles.add(role);
            return this;
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        if(gender == 1){
            return "Nam";
        }
        else if (gender == -1){
            return "Nữ";
        }
        else{
            return "Chưa xác định";
        }
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean isLibrarian(){
        return roles!= null && roles.contains(Role.LIBRARICIAN);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public boolean equals(String keyword){
        if(userName.equalsIgnoreCase(keyword) || password.equalsIgnoreCase(keyword) || fullName.equalsIgnoreCase(keyword) || phone.equalsIgnoreCase(keyword))
            return true;
        return false;
    }

    public boolean isBorrower(){
        return roles != null && roles.contains(Role.USER);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
