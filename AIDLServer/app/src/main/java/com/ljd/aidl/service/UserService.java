package com.ljd.aidl.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.ljd.aidl.IUserManager;
import com.ljd.aidl.entity.User;

public class UserService extends Service {

    private User mUser;

    /**
     * 根据AIDL文件 编译器为我们生成的BookManager
     */
    private final IUserManager.Stub mUserManager = new IUserManager.Stub() {


        @Override
        public User getUser() throws RemoteException {
            return mUser;
        }

        @Override
        public void setUser(User user) throws RemoteException {
            mUser = user;
        }

        @Override
        public void setUserName(String name) throws RemoteException {
            if (mUser != null) {
                mUser.setUname(name);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        User user = new User();
        user.setUid("10000");
        user.setUname("测试10000");
        try {
            if (mUserManager != null) {
                mUserManager.setUser(user);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mUserManager;
    }
}
