package com.example.wwwqq.day8lx2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class Liaotian extends AppCompatActivity implements EMMessageListener
{
    private Button b1;
    private EditText e1;
    private ListView l1;
    private String lalala1;
    private String lalala2;
    final Home home = new Home();
    private ArrayList<EMMessage> arrayList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b1=findViewById(R.id.b1);
        e1=findViewById(R.id.e1);
        l1=findViewById(R.id.l1);

        final Intent intent=getIntent();
        lalala1 = intent.getStringExtra("qqq1");
        lalala2 = intent.getStringExtra("qqq2");

        l1.setAdapter(home);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string=e1.getText().toString();
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(string, lalala1);
                message.setChatType(EMMessage.ChatType.Chat);
                //发送消息
                EMClient.getInstance().chatManager().sendMessage(message);

                message.setMessageStatusCallback(new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.i("qqq","头雷老谋消息发送成功");

                        }

                    @Override
                    public void onError(int i, String s) {
                        Log.i("qqq","消息发送失败"+i+","+s);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });

                arrayList.add(message);
                home.notifyDataSetChanged();
                e1.setText("");
            }
        });
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        for (final EMMessage message : list)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    arrayList.add(message);
                    home.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }

    class Home extends BaseAdapter
    {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return arrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            EMMessage emMessage = arrayList.get(i);
            if(emMessage.direct().ordinal()==1)
            {
                View view1= LayoutInflater.from(Liaotian.this).inflate(R.layout.zuo,null);
                ImageView i1=view1.findViewById(R.id.i1);
                TextView t1=view1.findViewById(R.id.t1);
                t1.setVisibility(View.VISIBLE);
                i1.setVisibility(View.VISIBLE);
                ImageOptions imageOptions=new ImageOptions.Builder().setCircular(true).build();
                x.image().bind(i1,lalala2,imageOptions);
                String message  = ((EMTextMessageBody)arrayList.get(i).getBody()).getMessage();
                t1.setText(message);

                return view1;

            }else
            {
                View view1= LayoutInflater.from(Liaotian.this).inflate(R.layout.you,null);
                ImageView i1=view1.findViewById(R.id.i1);
                TextView t1=view1.findViewById(R.id.t1);
                t1.setVisibility(View.VISIBLE);
                i1.setVisibility(View.VISIBLE);
                ImageOptions imageOptions=new ImageOptions.Builder().setCircular(true).build();
                x.image().bind(i1,"http://img0.imgtn.bdimg.com/it/u=3379456234,2805956052&fm=26&gp=0.jpg",imageOptions);
                String message  = ((EMTextMessageBody)arrayList.get(i).getBody()).getMessage();
                t1.setText(message);

                return view1;
            }

        }
    }
}
