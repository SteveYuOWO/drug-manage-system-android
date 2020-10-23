package com.littlepage.drug_manage_system_android.entity;

public class Purchase {
    Integer id;
    User user;
    Drug drug;

    public Purchase() {
    }

    public Purchase(Integer id, User user, Drug drug) {
        this.id = id;
        this.user = user;
        this.drug = drug;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", user=" + user +
                ", drug=" + drug +
                '}';
    }
}
