package com.littlepage.drug_manage_system_android.api;

import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.littlepage.drug_manage_system_android.entity.Comment;
import com.littlepage.drug_manage_system_android.entity.CommentDto;
import com.littlepage.drug_manage_system_android.entity.Drug;
import com.littlepage.drug_manage_system_android.util.RestUtil;

import java.io.IOException;
import java.util.List;

public class CommentApi {
    /**
     * post comment
     * @param commentDto
     * @return
     */
    public static String postComment(CommentDto commentDto) {
        String res = "";
        try {
            res = RestUtil.post("comment", JSON.toJSONString(commentDto));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * get all comments
     * @return
     */
    public static List<Comment> getAllComments() {
        String commentData = null;
        try {
            commentData = RestUtil.get("comment");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Comment> list = null;
        if(commentData != null) {
            list = JSONArray.parseArray(commentData, Comment.class);
        }
        return list;
    }

    /**
     * delete comment by Id
     * @param commentId
     * @return
     */
    public static String deleteCommentById(Integer commentId) {
        String res = "";
        try {
            res = RestUtil.delete("comment/" + commentId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
