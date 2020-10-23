package com.littlepage.drug_manage_system_android.pages;

import android.app.Activity;
import android.content.Context;
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
import com.littlepage.drug_manage_system_android.api.UserApi;
import com.littlepage.drug_manage_system_android.entity.User;

import java.util.List;

public class UserListActivity extends Activity {
    UserListActivity thisActivity = this;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 1) {
                List<User> userData = (List<User>) msg.obj;
                ListView listView = findViewById(R.id.user_list);
                listView.setAdapter(new UserListAdapter(thisActivity, userData));
            } else if(msg.what == 2) {
                if(msg.obj.toString().equals("删除成功")) {
                    Toast.makeText(UserListActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserListActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
                refreshUserData();
            }
        }
    };



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);
        refreshUserData();
    }

    /**
     * refresh user data
     */
    private void refreshUserData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> userData = UserApi.getAllUsers();
                Message message = Message.obtain(handler);
                message.what = 1;
                message.obj = userData;
                message.sendToTarget();
            }
        }).start();
    }

    /**
     * delete user data by id
     * @param id
     */
    private void deleteUserDataById(Integer id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String res = UserApi.deleteUserById(id);
                Message message = Message.obtain(handler);
                message.what = 2;
                message.obj = res;
                message.sendToTarget();
            }
        }).start();
    }

    class UserListAdapter extends ArrayAdapter<User> {
        Context context;

        List<User> userData;

        public UserListAdapter(@NonNull Context context, List<User> userData) {
            super(context, R.layout.user_row, userData);
            this.context = context;
            this.userData = userData;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.user_row, parent, false);
            ImageView images = row.findViewById(R.id.user_img);
            TextView user_name = row.findViewById(R.id.user_name);

            // set data
            User user = userData.get(position);
            images.setImageResource(R.drawable.user);
            user_name.setText(user.getId() + "." + user.getUsername());

            Button userDeleteBtn = row.findViewById(R.id.user_delete_btn);
            userDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteUserDataById(user.getId());
                }
            });
            return row;
        }
    }


}
