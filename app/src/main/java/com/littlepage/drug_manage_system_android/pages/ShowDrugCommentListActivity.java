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
import com.littlepage.drug_manage_system_android.api.CommentApi;
import com.littlepage.drug_manage_system_android.api.DrugCommentApi;
import com.littlepage.drug_manage_system_android.entity.Comment;
import com.littlepage.drug_manage_system_android.entity.DrugComment;

import java.util.List;

public class ShowDrugCommentListActivity extends Activity {
    ShowDrugCommentListActivity thisActivity = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_comments_list);
        refreshDrugCommentsData();
    }

    /**
     * refresh drug comments data
     */
    private void refreshDrugCommentsData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<DrugComment> list = DrugCommentApi.getDrugComments();
                Message message = Message.obtain(handler);
                message.what = 1;
                message.obj = list;
                message.sendToTarget();
            }
        }).start();
    }

    /**
     * deleteDrugCommentById
     * @param commentId
     */
    void deleteDrugCommentById(Integer commentId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String res = DrugCommentApi.deleteDrugCommentById(commentId);
                Message message = Message.obtain(handler);
                message.what = 2;
                message.obj = res;
                message.sendToTarget();
            }
        }).start();
    }

    /**
     * drug comments row的适配器
     */
    class ShowDrugCommentListAdapter extends ArrayAdapter<DrugComment> {
        Context context;

        List<DrugComment> drugCommentData;

        public ShowDrugCommentListAdapter(@NonNull Context context, List<DrugComment> drugCommentData) {
            super(context, R.layout.show_drug_comments_list_row, drugCommentData);
            this.context = context;
            this.drugCommentData = drugCommentData;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.show_drug_comments_list_row, parent, false);
            ImageView images = row.findViewById(R.id.drug_comment_img);
            TextView commentUser = row.findViewById(R.id.drug_comment_user);
            TextView commentStr = row.findViewById(R.id.drug_comment_str);
            // set data
            DrugComment drugComment = drugCommentData.get(position);
            images.setImageResource(R.drawable.comment);
            commentUser.setText(drugComment.getFrom().getUsername());
            commentStr.setText(drugComment.getMessage());

            Button deleteCommentBtn = row.findViewById(R.id.delete_comment_btn);

            // click handle
            deleteCommentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer commentId = drugComment.getId();
                    deleteDrugCommentById(commentId);
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
                List<DrugComment> commentsData = (List<DrugComment>) msg.obj;
                ListView listView = findViewById(R.id.show_comments_list);
                listView.setAdapter(new ShowDrugCommentListAdapter(thisActivity, commentsData));
            } else if(msg.what == 2) {
                Toast.makeText(ShowDrugCommentListActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                refreshDrugCommentsData();
            }
        }
    };
}

