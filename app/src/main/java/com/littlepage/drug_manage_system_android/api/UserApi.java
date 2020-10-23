package com.littlepage.drug_manage_system_android.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.littlepage.drug_manage_system_android.entity.Drug;
import com.littlepage.drug_manage_system_android.entity.User;
import com.littlepage.drug_manage_system_android.util.RestUtil;

import java.io.IOException;
import java.util.List;

public class UserApi {
    public static List<User> getAllUsers() {
        String usersData = null;
        try {
            usersData = RestUtil.get("user");
            System.out.println(usersData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<User> list = null;
        if(usersData != null) {
            list = JSONArray.parseArray(usersData, User.class);
        }
        return list;
    }

    public static String deleteUserById(Integer id) {
        String res = "删除失败";
        try {
            res = RestUtil.delete("user/" + id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String postUser(User user) {
        String res = "";
        try {
            res = RestUtil.post("user", JSON.toJSONString(user));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
