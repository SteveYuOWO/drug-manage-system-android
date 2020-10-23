package com.littlepage.drug_manage_system_android.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.littlepage.drug_manage_system_android.entity.Admin;
import com.littlepage.drug_manage_system_android.entity.LoginDto;
import com.littlepage.drug_manage_system_android.entity.User;
import com.littlepage.drug_manage_system_android.util.RestUtil;

import java.io.IOException;

public class LoginApi {
    /**
     * admin Login
     * @param admin
     * @return
     */
    public static LoginDto<Admin> adminLogin(Admin admin) {
        String res = "";
        try {
            res = RestUtil.post("login/admin", JSON.toJSONString(admin));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON.parseObject(res, new TypeReference<LoginDto<Admin>>() {});
    }

    /**
     * common Login
     * @param user
     * @return
     */
    public static LoginDto<User> commonLogin(User user) {
        String res = "";
        try {
            res = RestUtil.post("login/common", JSON.toJSONString(user));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON.parseObject(res, new TypeReference<LoginDto<User>>(){});
    }
}
