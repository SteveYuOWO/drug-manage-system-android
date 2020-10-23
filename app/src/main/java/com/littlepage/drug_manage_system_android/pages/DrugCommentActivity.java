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
import com.littlepage.drug_manage_system_android.api.DrugCommentApi;
import com.littlepage.drug_manage_system_android.entity.DrugComment;
import com.littlepage.drug_manage_system_android.entity.DrugCommentDto;

public class DrugCommentActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drug_comment);
        View view = findViewById(R.id.drug_comment);
        Button btn = view.findViewById(R.id.drugCommentBtn);
        EditText commentContentEditText = view.findViewById(R.id.drugCommentEditText);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                Integer drugId = bundle.getInt("drugId");
                Integer userId = bundle.getInt("userId");
                String commentContent = commentContentEditText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String res = DrugCommentApi.postDrugComment(new DrugCommentDto(userId, drugId, commentContent));
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
                Toast.makeText(DrugCommentActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DrugCommentActivity.this, DrugHasPurchaseListActivity.class);
                startActivity(intent);
            }
        }
    };
}
