package com.littlepage.drug_manage_system_android.entity;

public class CommentDto {
    Integer userId;
    String message;

    public CommentDto() {
    }

    public CommentDto(Integer userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "userId=" + userId +
                ", message='" + message + '\'' +
                '}';
    }
}