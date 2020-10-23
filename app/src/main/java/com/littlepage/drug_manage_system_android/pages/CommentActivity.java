package com.littlepage.drug_manage_system_android.pages;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.littlepage.drug_manage_system_android.R;
import com.littlepage.drug_manage_system_android.api.CommentApi;
import com.littlepage.drug_manage_system_android.entity.CommentDto;

public class CommentActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);

        Button commentBtn = findViewById(R.id.commentBtn);
        /**
         * 评论按钮点击
         */
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText commentEditText = findViewById(R.id.commentText);
                String commentStr = commentEditText.getText().toString();

                if(commentStr.equals("")) {
                    Toast.makeText(CommentActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 请求留言
                        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                        int userId = sharedPreferences.getInt("userId", 0);
                        String res = CommentApi.postComment(new CommentDto(userId, commentStr));
                        Message msg = Message.obtain(handler);
                        msg.what = 1;
                        msg.obj = res;
                        msg.sendToTarget();
                    }
                }).start();
            }
        });
    }

    /**
     * 进程消息事件
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
        if (msg.what == 1) {
            Toast.makeText(CommentActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CommentActivity.this, CommonMenuActivity.class);
            startActivity(intent);
        }
        }
    };
}
