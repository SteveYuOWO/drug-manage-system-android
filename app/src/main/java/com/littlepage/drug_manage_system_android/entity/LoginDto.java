package com.littlepage.drug_manage_system_android.entity;

public class LoginDto<T> {
    String loginType;
    T user;
    String message;

    public LoginDto() {
    }

    public LoginDto(String loginType, T user, String message) {
        this.loginType = loginType;
        this.user = user;
        this.message = message;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LoginDto{" +
                "loginType='" + loginType + '\'' +
                ", user=" + user +
                ", message='" + message + '\'' +
                '}';
    }
}