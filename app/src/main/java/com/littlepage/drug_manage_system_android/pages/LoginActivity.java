package com.littlepage.drug_manage_system_android.pages;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.littlepage.drug_manage_system_android.R;
import com.littlepage.drug_manage_system_android.api.LoginApi;
import com.littlepage.drug_manage_system_android.entity.Admin;
import com.littlepage.drug_manage_system_android.entity.LoginDto;
import com.littlepage.drug_manage_system_android.entity.User;

public class LoginActivity extends Activity {
    LoginActivity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // 前往注册页面
        Button goToRegisterBtn = (Button)findViewById(R.id.goToRegisterBtn);
        goToRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // 登录事件
        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View loginView = findViewById(R.id.login_form);
                TextView usernameTextView = (TextView) loginView.findViewById(R.id.username);
                TextView passwordTextView = (TextView) loginView.findViewById(R.id.password);
                String username = usernameTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                // empty judge
                if(username.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Api request
                if(((RadioButton)findViewById(R.id.commonType)).isChecked()) {
                    // 普通用户登录
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            LoginDto<User> userLoginDto = LoginApi.commonLogin(new User(username, password));
                            Message message = Message.obtain(handler);
                            message.what = 1;
                            message.obj = userLoginDto;
                            message.arg1 = 1;
                            message.sendToTarget();
                        }
                    }).start();
                } else {
                    // 管理员登录
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            LoginDto<Admin> adminLoginDto = LoginApi.adminLogin(new Admin(username, password));
                            Message message = Message.obtain(handler);
                            message.what = 1;
                            message.obj = adminLoginDto;
                            message.arg1 = 2;
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
                if(msg.arg1 == 1) {
                    // 普通用户登录
                    LoginDto<User> userLoginDto = (LoginDto<User>) msg.obj;
                    Toast.makeText(LoginActivity.this, userLoginDto.getMessage(), Toast.LENGTH_SHORT).show();
                    if(userLoginDto.getMessage().equals("登录成功")) {
                        // store information
                        SharedPreferences saveSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = saveSharedPreferences.edit();
                        editor.putString("type", userLoginDto.getLoginType());
                        editor.putInt("userId", userLoginDto.getUser().getId());
                        editor.putString("username", userLoginDto.getUser().getUsername());
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, CommonMenuActivity.class);
                        startActivity(intent);
                    }
                } else if(msg.arg1 == 2) {
                    // 管理员登录
                    LoginDto<Admin> adminLoginDto = (LoginDto<Admin>) msg.obj;
                    Toast.makeText(LoginActivity.this, adminLoginDto.getMessage(), Toast.LENGTH_SHORT).show();
                    if(adminLoginDto.getMessage().toString().equals("登录成功")) {
                        // store information
                        SharedPreferences saveSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = saveSharedPreferences.edit();
                        editor.putString("type", adminLoginDto.getLoginType());
                        editor.putInt("userId", adminLoginDto.getUser().getId());
                        editor.putString("username", adminLoginDto.getUser().getUsername());
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, AdminMenuActivity.class);
                        startActivity(intent);
                    }
                }

            }
        }
    };
}