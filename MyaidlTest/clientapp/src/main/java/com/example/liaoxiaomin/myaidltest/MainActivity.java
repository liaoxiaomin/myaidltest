package com.example.liaoxiaomin.myaidltest;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Loader;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static String TAG="MainActivity:";
    private IMyAidlInterface myAidlServive;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myAidlServive = IMyAidlInterface.Stub.asInterface(service);
            if(myAidlServive ==null) {
                Log.i("lxm","myAidlServive==null");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myAidlServive = null;
        }
    };

    private Button button;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.bt);
        textView = (TextView)findViewById(R.id.tv);

        //创建所需要绑定的Service的Intent
        Intent intent = new Intent();
        intent.setAction("com.example.myaidlserver.service");
        intent.setPackage("com.example.myaidlserver");
        //绑定远程服务
        bindService(intent,connection, Service.BIND_AUTO_CREATE);

        //点击进行Binder通信，获取值
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int ss=myAidlServive.add();
                    Log.i("xiao","  ss= "+ss);
                    button.setText(String.valueOf(ss));
                    int ab=myAidlServive.returnMum(ss);
                    Log.i("xiao","  ab="+ab);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 创建指定数量的随机字符串
     * @param numberFlag 是否是数字
     * @param length
     * @return
     */
    public static String createRandom(boolean numberFlag, int length){
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unbindService(connection);
    }
}
