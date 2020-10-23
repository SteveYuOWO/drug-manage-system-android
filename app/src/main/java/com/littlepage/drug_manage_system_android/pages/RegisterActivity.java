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
import com.littlepage.drug_manage_system_android.api.UserApi;
import com.littlepage.drug_manage_system_android.entity.Admin;
import com.littlepage.drug_manage_system_android.entity.LoginDto;
import com.littlepage.drug_manage_system_android.entity.User;

public class RegisterActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // 前往登录页面
        Button goToLoginBtn = (Button)findViewById(R.id.goToLoginBtn);
        goToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // 注册事件
        Button registerBtn = (Button)findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View registerPage = findViewById(R.id.registerPage);
                EditText usernameEditText = (EditText) registerPage.findViewById(R.id.username);
                EditText passwordEditText = (EditText) registerPage.findViewById(R.id.password);
                EditText passwordRepeatEditText = (EditText) registerPage.findViewById(R.id.passwordRepeat);

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String passwordRepeat = passwordRepeatEditText.getText().toString();

                // validate
                if(username.equals("") || password.equals("") || passwordRepeat.equals("")) {
                    Toast.makeText(RegisterActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if(!password.equals(passwordRepeat)) {
                    Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // register
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String res = UserApi.postUser(new User(username, password));
                            Message message = Message.obtain(handler);
                            message.what = 1;
                            message.obj = res;
                            message.sendToTarget();
                        }
                    }).start();
                }
            }
        });

    }

    /**
     * 进程消息事件
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 1) {
                // 普通用户登录
                Toast.makeText(RegisterActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                if(msg.obj.toString().equals("注册成功")) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        }
    };
}
