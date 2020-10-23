package com.littlepage.drug_manage_system_android.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.littlepage.drug_manage_system_android.R;

public class AdminMenuActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminmenu);
    }

    /**
     * 管理药品点击事件
     * @param view
     */
    public void manageDrugClick(View view) {
        Intent intent = new Intent(AdminMenuActivity.this, DrugListActivity.class);
        startActivity(intent);
    }

    /**
     * 用户点击事件
     * @param view
     */
    public void manageUserClick(View view) {
        Intent intent = new Intent(AdminMenuActivity.this, UserListActivity.class);
        startActivity(intent);
    }

    /**
     * 药品增加点击事件
     * @param view
     */
    public void drugAddClick(View view) {
        Intent intent = new Intent(AdminMenuActivity.this, DrugAddActivity.class);
        startActivity(intent);
    }

    /**
     * 网站留言显示
     * @param view
     */
    public void commentListClick(View view) {
        Intent intent = new Intent(AdminMenuActivity.this, ShowCommentListActivity.class);
        startActivity(intent);
    }

    /**
     * 药品留言
     * @param view
     */
    public void drugCommentClick(View view) {
        Intent intent = new Intent(AdminMenuActivity.this, ShowDrugCommentListActivity.class);
        startActivity(intent);
    }
}
