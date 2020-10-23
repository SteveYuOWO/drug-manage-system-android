package com.littlepage.drug_manage_system_android.pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.littlepage.drug_manage_system_android.R;

public class CommonMenuActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commonmenu);
    }

    /**
     * 购买点击事件
     * @param view
     */
    public void purchaseDrugClick(View view) {
        Intent intent = new Intent(CommonMenuActivity.this, DrugPurchaseListActivity.class);
        startActivity(intent);
    }

    /**
     * 我的购买点击事件
     * @param view
     */
    public void myPurchaseClick(View view) {
        Intent intent = new Intent(CommonMenuActivity.this, DrugHasPurchaseListActivity.class);
        startActivity(intent);
    }

    /**
     * 留言板跳转点击事件
     * @param view
     */
    public void jumpCommentClick(View view) {
        Intent intent = new Intent(CommonMenuActivity.this, CommentActivity.class);
        startActivity(intent);
    }
}
