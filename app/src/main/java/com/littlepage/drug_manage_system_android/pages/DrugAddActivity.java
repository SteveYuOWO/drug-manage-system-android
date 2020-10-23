package com.littlepage.drug_manage_system_android.pages;

import android.app.Activity;
import android.content.Intent;
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
import com.littlepage.drug_manage_system_android.api.DrugApi;
import com.littlepage.drug_manage_system_android.entity.Drug;

import java.time.LocalDateTime;

public class DrugAddActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drug_add);
        handleClick();
    }

    /**
     * 点击事件
     */
    public void handleClick() {
        View addDrugView = findViewById(R.id.add_drug_page);

        /**
         * 返回点击事件
         */
        Button returnBackBtn = addDrugView.findViewById(R.id.return_back);
        returnBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DrugAddActivity.this, AdminMenuActivity.class);
                startActivity(intent);
            }
        });

        Button confirmAddDrug = addDrugView.findViewById(R.id.confirm_add_drug);
        confirmAddDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText drugNameText = addDrugView.findViewById(R.id.drug_name);
                EditText drugInfoText = addDrugView.findViewById(R.id.drug_info);
                EditText storageTimeText = addDrugView.findViewById(R.id.drug_storage_time);

                String drugName = drugNameText.getText().toString();
                String drugInfo = drugInfoText.getText().toString();
                String storageTime = storageTimeText.getText().toString();

                if(drugName.equals("") || drugInfo.equals("") || storageTime.equals("")) {
                    Toast.makeText(DrugAddActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Drug drug = new Drug(0, drugName, drugInfo , LocalDateTime.now(), Integer.parseInt(storageTime));
                    // 插入药品
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String res = DrugApi.postDrug(drug);
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
        if (msg.what == 1) {
            Toast.makeText(DrugAddActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DrugAddActivity.this, AdminMenuActivity.class);
            startActivity(intent);
        }
        }
    };
}
