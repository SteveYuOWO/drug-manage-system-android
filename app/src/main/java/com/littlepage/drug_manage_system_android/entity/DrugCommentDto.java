package com.littlepage.drug_manage_system_android.entity;

public class DrugCommentDto {
    Integer userId;
    Integer drugId;
    String message;

    public DrugCommentDto() {
    }

    public DrugCommentDto(Integer userId, Integer drugId, String message) {
        this.userId = userId;
        this.drugId = drugId;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DrugCommentDto{" +
                "userId=" + userId +
                ", drugId=" + drugId +
                ", message='" + message + '\'' +
                '}';
    }
}

