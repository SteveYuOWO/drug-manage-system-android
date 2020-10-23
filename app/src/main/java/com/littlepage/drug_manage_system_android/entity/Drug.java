package com.littlepage.drug_manage_system_android.entity;

import java.time.LocalDateTime;

public class Drug {
    Integer id;
    String name;
    String info;
    LocalDateTime productTime;
    Integer storageMonth;

    public Drug() { }

    public Drug(Integer id, String name, String info, LocalDateTime productTime, Integer storageMonth) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.productTime = productTime;
        this.storageMonth = storageMonth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDateTime getProductTime() {
        return productTime;
    }

    public void setProductTime(LocalDateTime productTime) {
        this.productTime = productTime;
    }

    public Integer getStorageMonth() {
        return storageMonth;
    }

    public void setStorageMonth(Integer storageMonth) {
        this.storageMonth = storageMonth;
    }

    @Override
    public String toString() {
        return "Drug{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", productTime=" + productTime +
                ", storageMonth=" + storageMonth +
                '}';
    }
}
