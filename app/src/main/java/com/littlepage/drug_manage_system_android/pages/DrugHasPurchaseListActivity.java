package com.littlepage.drug_manage_system_android.pages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.littlepage.drug_manage_system_android.R;
import com.littlepage.drug_manage_system_android.api.DrugApi;
import com.littlepage.drug_manage_system_android.api.PurchaseApi;
import com.littlepage.drug_manage_system_android.entity.Drug;
import com.littlepage.drug_manage_system_android.entity.DrugComment;
import com.littlepage.drug_manage_system_android.entity.Purchase;
import com.littlepage.drug_manage_system_android.entity.PurchaseDto;

import java.time.LocalDateTime;
import java.util.List;

public class DrugHasPurchaseListActivity extends Activity {
    DrugHasPurchaseListActivity thisActivity = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drug_has_purchase_list);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", 0);
        refreshDrugData(userId);
    }

    /**
     * refresh drug data
     */
    void refreshDrugData(int userId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Purchase> drugData = PurchaseApi.getAllPurchasesByUserId(userId);
                Message message = Message.obtain(handler);
                message.what = 1;
                message.obj = drugData;
                message.sendToTarget();
            }
        }).start();
    }

    /**
     * drug row的适配器
     */
    class DrugHasPurchaseListAdapter extends ArrayAdapter<Purchase> {
        Context context;

        List<Purchase> purchaseData;

        public DrugHasPurchaseListAdapter(@NonNull Context context, List<Purchase> purchaseData) {
            super(context, R.layout.drug_row, purchaseData);
            this.context = context;
            this.purchaseData = purchaseData;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.drug_has_purchase_list_row, parent, false);
            ImageView images = row.findViewById(R.id.drug_img);
            TextView title = row.findViewById(R.id.drug_name);
            TextView productTime = row.findViewById(R.id.product_time);
            TextView storageMonth = row.findViewById(R.id.storage_month);
            TextView subTitle = row.findViewById(R.id.drug_info);

            // set data
            Drug drug = purchaseData.get(position).getDrug();
            images.setImageResource(R.drawable.pills);
            title.setText(drug.getId() + "." + drug.getName());
            subTitle.setText(drug.getInfo());
            LocalDateTime time = drug.getProductTime();
            productTime.setText("生产日期: " + time.getYear() + "-" + time.getMonthValue() + "-" + time.getDayOfMonth());
            storageMonth.setText("保质期: " + drug.getStorageMonth() + "月");


            Button drugPurchaseCommentBtn = row.findViewById(R.id.drug_purchase_comment);

            // get User Id
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
            int userId = sharedPreferences.getInt("userId", 0);



            // click handle
            drugPurchaseCommentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DrugHasPurchaseListActivity.this, DrugCommentActivity.class);
                    intent.putExtra("drugId", drug.getId());
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }
            });
            return row;
        }
    }

    /**
     * 进程消息事件
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 1) {
                List<Purchase> purchaseData = (List<Purchase>) msg.obj;
                ListView listView = findViewById(R.id.drug_list);
                listView.setAdapter(new DrugHasPurchaseListAdapter(thisActivity, purchaseData));
            }
        }
    };
}
