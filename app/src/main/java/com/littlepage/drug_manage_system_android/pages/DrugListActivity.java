package com.littlepage.drug_manage_system_android.pages;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.littlepage.drug_manage_system_android.entity.Drug;

import java.time.LocalDateTime;
import java.util.List;

public class DrugListActivity extends Activity {
    DrugListActivity thisActivity = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drug_list);
        refreshDrugData();
    }

    /**
     * refresh drug data
     */
    void refreshDrugData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Drug> drugData = DrugApi.getAllDrugs();
                Message message = Message.obtain(handler);
                message.what = 1;
                message.obj = drugData;
                message.sendToTarget();
            }
        }).start();
    }

    /**
     * delete drug data by id
     * @param id
     */
    void deleteDrugDataById(Integer id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String res = DrugApi.deleteDrugById(id);
                Message message = Message.obtain(handler);
                message.what = 2;
                message.obj = res;
                message.sendToTarget();
            }
        }).start();
    }

    /**
     * drug row的适配器
     */
    class DrugListAdapter extends ArrayAdapter<Drug> {
        Context context;

        List<Drug> drugData;

        public DrugListAdapter(@NonNull Context context, List<Drug> drugData) {
            super(context, R.layout.drug_row, drugData);
            this.context = context;
            this.drugData = drugData;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.drug_row, parent, false);
            ImageView images = row.findViewById(R.id.drug_img);
            TextView title = row.findViewById(R.id.drug_name);
            TextView productTime = row.findViewById(R.id.product_time);
            TextView storageMonth = row.findViewById(R.id.storage_month);
            TextView subTitle = row.findViewById(R.id.drug_info);

            // set data
            Drug drug = drugData.get(position);
            images.setImageResource(R.drawable.pills);
            title.setText(drug.getId() + "." + drug.getName());
            subTitle.setText(drug.getInfo());
            LocalDateTime time = drug.getProductTime();
            productTime.setText("生产日期: " + time.getYear() + "-" + time.getMonthValue() + "-" + time.getDayOfMonth());
            storageMonth.setText("保质期: " + drug.getStorageMonth() + "月");


            Button drugDeleteBtn = row.findViewById(R.id.drug_delete_btn);
            drugDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteDrugDataById(drug.getId());
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
                List<Drug> drugData = (List<Drug>) msg.obj;
                ListView listView = findViewById(R.id.drug_list);
                listView.setAdapter(new DrugListAdapter(thisActivity, drugData));
            } else if(msg.what == 2) {
                if(msg.obj.toString().equals("删除成功")) {
                    Toast.makeText(DrugListActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DrugListActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
                refreshDrugData();
            }

        }
    };
}
