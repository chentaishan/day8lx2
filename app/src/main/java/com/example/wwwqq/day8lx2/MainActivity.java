package com.example.wwwqq.day8lx2;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

public class MainActivity extends AppCompatActivity {

    private EditText zhang,mi;
    private Button b1,b2;
    DbManager db;
    String trim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        zhang=findViewById(R.id.zhang);
        mi=findViewById(R.id.mi);
        b1=findViewById(R.id.b1);
        b2=findViewById(R.id.b2);

        DbManager.DaoConfig daoConfig=new DbManager.DaoConfig();
        daoConfig.setDbName("lalala.db")
                .setDbVersion(1);
        db=x.getDb(daoConfig);

        EMClient.getInstance().chatManager().loadAllConversations();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EMClient.getInstance().login(zhang.getText().toString().trim(),mi.getText().toString().trim(), new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Intent intent=new Intent(MainActivity.this,Main_Two.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().createAccount(zhang.getText().toString().trim(),mi.getText().toString().trim());
                                Menu_tt menu_tt=new Menu_tt();
                                menu_tt.setName(zhang.getText().toString());
                                db.save(menu_tt);
                                Log.i("qqq","存储成功");
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }
}
