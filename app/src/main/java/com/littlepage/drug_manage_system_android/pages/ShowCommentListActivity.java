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
import com.littlepage.drug_manage_system_android.entity.Comment;
import java.util.List;

public class ShowCommentListActivity extends Activity {
    ShowCommentListActivity thisActivity = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_comments_list);
        refreshCommentsData();
    }

    /**
     * refresh comments data
     */
    void refreshCommentsData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Comment> list = CommentApi.getAllComments();
                Message message = Message.obtain(handler);
                message.what = 1;
                message.obj = list;
                message.sendToTarget();
            }
        }).start();
    }


    /**
     * deleteCommentById
     * @param commentId
     */
    void deleteCommentById(Integer commentId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String res = CommentApi.deleteCommentById(commentId);
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
    class ShowCommentListAdapter extends ArrayAdapter<Comment> {
        Context context;

        List<Comment> commentData;

        public ShowCommentListAdapter(@NonNull Context context, List<Comment> commentData) {
            super(context, R.layout.drug_row, commentData);
            this.context = context;
            this.commentData = commentData;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.show_comments_list_row, parent, false);
            ImageView images = row.findViewById(R.id.comment_img);
            TextView commentUser = row.findViewById(R.id.comment_user);
            TextView commentStr = row.findViewById(R.id.comment_str);
            // set data
            Comment comment = commentData.get(position);
            images.setImageResource(R.drawable.comment);
            commentUser.setText(comment.getFrom().getUsername());
            commentStr.setText(comment.getMessage());

            Button deleteCommentBtn = row.findViewById(R.id.delete_comment_btn);

            // click handle
            deleteCommentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer commentId = comment.getId();
                    deleteCommentById(commentId);
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
                List<Comment> commentsData = (List<Comment>) msg.obj;
                ListView listView = findViewById(R.id.show_comments_list);
                listView.setAdapter(new ShowCommentListAdapter(thisActivity, commentsData));
            } else if(msg.what == 2) {
                Toast.makeText(ShowCommentListActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                refreshCommentsData();
            }
        }
    };

}
