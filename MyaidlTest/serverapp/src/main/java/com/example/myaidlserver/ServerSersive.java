package com.example.myaidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;


import com.example.liaoxiaomin.myaidltest.IMyAidlInterface;

import java.util.Random;

public class ServerSersive extends Service {

    private MyAidlBinder myAidlBinder;

    public class MyAidlBinder extends IMyAidlInterface.Stub{

        @Override
        public int add() throws RemoteException {
            return getab();
        }

        @Override
        public int returnMum(int ab) throws RemoteException {
            return ab(ab);
        }

    }

    public ServerSersive() {
    }
    public int getab(){
        Random rand = new Random();
        int a= rand.nextInt(100);
        int b= rand.nextInt(100);
        Log.i("xiao","  a="+a+"   b="+b);
        return a+b;
    }
    public int ab(int ab){

        return ab+1;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        myAidlBinder = new MyAidlBinder();
    }

    //onBind返回的IBinder对象要是IMyAidlService.Stub的子类的实例
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return  myAidlBinder;
    }
}
