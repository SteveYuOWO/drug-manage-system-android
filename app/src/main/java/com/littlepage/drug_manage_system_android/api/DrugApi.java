package com.littlepage.drug_manage_system_android.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.littlepage.drug_manage_system_android.entity.Drug;
import com.littlepage.drug_manage_system_android.util.RestUtil;

import java.io.IOException;
import java.util.List;

public class DrugApi {
    /**
     * 获取所有的药品
     * @return
     */
    public static List<Drug> getAllDrugs() {
        String drugsData = null;
        try {
            drugsData = RestUtil.get("drug");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Drug> list = null;
        if(drugsData != null) {
             list = JSONArray.parseArray(drugsData, Drug.class);
        }
        return list;
    }

    /**
     * 根据id删除药品
     * @param id
     * @return
     */
    public static String deleteDrugById(Integer id) {
        String res = "删除失败";
        try {
            res = RestUtil.delete("drug/" + id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 发布药品
     * @param drug
     * @return
     */
    public static String postDrug(Drug drug) {
        String res = "";
        try {
            res = RestUtil.post("drug", JSON.toJSONString(drug));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
