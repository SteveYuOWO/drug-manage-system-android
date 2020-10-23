package com.littlepage.drug_manage_system_android.entity;

public class Comment {
    Integer id;
    User from;
    Admin to;
    String message;

    public Comment() {
    }

    public Comment(Integer id, User from, Admin to, String message) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Admin getTo() {
        return to;
    }

    public void setTo(Admin to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", message='" + message + '\'' +
                '}';
    }
}
