package com.ljd.aidl.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ljd.aidl.IUserManager;
import com.ljd.aidl.client.R;
import com.ljd.aidl.entity.Book;
import com.ljd.aidl.entity.User;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Demo5Activity extends AppCompatActivity {

    private final String TAG = "DEMO5";
    private boolean mIsBindService;
    private IUserManager mIUserManager;

    private TextView TvContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo5);
        TvContent = (TextView) findViewById(R.id.TvContent);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        unbindService();
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick({R.id.bind_demo4_btn, R.id.unbind_demo4_btn, R.id.get_btn, R.id.set_btn,R.id.update_btn})
    public void onClickButton(View v) {
        switch (v.getId()) {
            case R.id.bind_demo4_btn:
                bindService();
                TvContent.setText("已绑定");
                break;
            case R.id.unbind_demo4_btn:
                Toast.makeText(this, "unbind service success", Toast.LENGTH_SHORT).show();
                unbindService();
                TvContent.setText("未绑定");
                break;
            case R.id.get_btn:
                getData();
                break;
            case R.id.set_btn:
                User suser = new User();
                suser.setUname("uname from  client");
                suser.setUid("20000");
                try {
                    mIUserManager.setUser(suser);
                    TvContent.setText("设置数据成功："+suser.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.update_btn:

                try {
                    mIUserManager.setUserName("is update name");
                    TvContent.setText("修改用名称为：is update name");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void getData() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                User user = null;
                try {
                    user = mIUserManager.getUser();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                TvContent.setText(user.toString());
            }
        });

    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "bind success");
            Toast.makeText(Demo5Activity.this, "bind service success", Toast.LENGTH_SHORT).show();
            mIUserManager = IUserManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //重新绑定Service防止系统将服务进程杀死而产生的调用错误。
            bindService();
        }
    };

    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("com.ljd.aidl.action.USER_SERVICE");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        mIsBindService = true;
    }

    private void unbindService() {
        if (mIsBindService) {
            mIsBindService = false;
            unbindService(mConnection);
        }
    }
}
