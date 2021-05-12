package com.sm19003564.omnimarkerza;

public class UserProfileClass {

    String userID;
    String email;
    String password;

    public UserProfileClass(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserProfileClass() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
