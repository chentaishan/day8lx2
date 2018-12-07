package com.example.wwwqq.day8lx2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import org.xutils.DbManager;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@SuppressLint("ValidFragment")
public class ConversationFragment extends Fragment
{
    private Context context;
    private ListView l1;
    private ArrayList<Bean> arrayList=new ArrayList<>();
    private DbManager db;
    private Home home=new Home();

    public ConversationFragment(Context context, DbManager db) {
        this.context = context;
        this.db=db;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1=LayoutInflater.from(context).inflate(R.layout.top1,container,false);
        l1=view1.findViewById(R.id.l1);

        final Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();

        cha(  conversations);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String messagId = conversations.get("mmm").getLastMessage().getMsgId();

                clearUnreadRedPoint( updateSingle(i),messagId,"mmm");

                Intent intent=new Intent(context,TalkingActivity.class);
                intent.putExtra("qqq1", arrayList.get(i).getName());
                intent.putExtra("qqq2",arrayList.get(i).getUri());
                startActivity(intent);
            }
        });

        return view1;
    }

    /**
     * 第一种方法 更新对应view的内容
     *
     * @param position 要更新的位置
     */
    private View updateSingle(int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = l1.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = l1.getLastVisiblePosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = l1.getChildAt(position - firstVisiblePosition);
           return view.findViewById(R.id.top1_yuan);
        }
        return  null;
    }


    private void cha(Map<String, EMConversation> conversations) {
        try {
            arrayList.clear();

            Iterator<Map.Entry<String, EMConversation>> entries = conversations.entrySet().iterator();

            while (entries.hasNext()) {

                Map.Entry<String, EMConversation> entry = entries.next();

                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                arrayList.add(new Bean(entry.getKey(), "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2215957843,1170124315&fm=27&gp=0.jpg"));

            }

            l1.setAdapter(home);
        } catch (Exception e) {
            e.printStackTrace();
            arrayList.add(new Bean("mmm","https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2215957843,1170124315&fm=27&gp=0.jpg"));
            l1.setAdapter(home);
        }
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

            View view1= LayoutInflater.from(context).inflate(R.layout.listview,null);
            ImageView i1=view1.findViewById(R.id.i1);
            TextView t1=view1.findViewById(R.id.t1);
            View circleView =view1.findViewById(R.id.top1_yuan);
            ImageOptions imageOptions=new ImageOptions.Builder().setCircular(true).build();
            x.image().bind(i1,arrayList.get(i).getUri(),imageOptions);
            t1.setText(arrayList.get(i).getName());
            circleView.setVisibility(View.INVISIBLE);
            try {
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation("mmm");
                int num =  conversation.getUnreadMsgCount();
                if (num>0){
                    circleView.setVisibility(View.VISIBLE);
                }else{
                    circleView.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                circleView.setVisibility(View.INVISIBLE);
            }

            return view1;
        }
    }

    public void clearUnreadRedPoint(View circleView,String messageId,String username){

        circleView.setVisibility(View.INVISIBLE);
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
//指定会话消息未读数清零
        conversation.markAllMessagesAsRead();
//把一条消息置为已读
        conversation.markMessageAsRead(messageId);
//所有未读消息数清零
        EMClient.getInstance().chatManager().markAllConversationsAsRead();
    }
}
