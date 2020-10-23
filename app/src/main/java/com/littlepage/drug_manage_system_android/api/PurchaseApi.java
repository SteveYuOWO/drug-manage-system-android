package com.littlepage.drug_manage_system_android.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.littlepage.drug_manage_system_android.entity.Drug;
import com.littlepage.drug_manage_system_android.entity.Purchase;
import com.littlepage.drug_manage_system_android.entity.PurchaseDto;
import com.littlepage.drug_manage_system_android.util.RestUtil;

import java.io.IOException;
import java.util.List;

public class PurchaseApi {
    /**
     * purchase
     * @param purchaseDto
     * @return
     */
    public static String purchase(PurchaseDto purchaseDto) {
        String res = "";
        try {
            res = RestUtil.post("purchase", JSON.toJSONString(purchaseDto));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * get all purchases by user id
     * @param userId
     * @return
     */
    public static List<Purchase> getAllPurchasesByUserId(int userId) {
        String res = null;
        try {
            res = RestUtil.get("purchase/" + userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Purchase> list = null;
        if(res != null) {
            list = JSONArray.parseArray(res, Purchase.class);
        }
        return list;
    }
}
