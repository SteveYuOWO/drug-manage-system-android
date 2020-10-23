package com.littlepage.drug_manage_system_android.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.littlepage.drug_manage_system_android.entity.DrugComment;
import com.littlepage.drug_manage_system_android.entity.DrugCommentDto;
import com.littlepage.drug_manage_system_android.util.RestUtil;

import java.io.IOException;
import java.util.List;

public class DrugCommentApi {
    /**
     * get drug comments
     * @return
     */
    public static List<DrugComment> getDrugComments() {
        List<DrugComment> drugComments = null;
        try {
            String res = RestUtil.get("drugcomment");
            drugComments = JSONArray.parseArray(res, DrugComment.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drugComments;
    }
    /**
     * drug comment post
     * @param drugComment
     * @return
     */
    public static String postDrugComment(DrugCommentDto drugComment) {
        String res = "";
        try {
            res = RestUtil.post("drugcomment", JSON.toJSONString(drugComment));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * deleteDrugCommentById
     * @param drugCommentId
     * @return
     */
    public static String deleteDrugCommentById(Integer drugCommentId) {
        String res = "";
        try {
            res = RestUtil.delete("drugcomment/" + drugCommentId);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
