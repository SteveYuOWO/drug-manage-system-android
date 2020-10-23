package com.littlepage.drug_manage_system_android.entity;

public class PurchaseDto {
    Integer userId;
    Integer drugId;

    public PurchaseDto() {
    }

    public PurchaseDto(Integer userId, Integer drugId) {
        this.userId = userId;
        this.drugId = drugId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    @Override
    public String toString() {
        return "PurchaseDto{" +
                "userId=" + userId +
                ", drugId=" + drugId +
                '}';
    }
}
