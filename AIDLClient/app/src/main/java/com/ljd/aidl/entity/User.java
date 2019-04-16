package com.ljd.aidl.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户实体类
 */
public class User implements Parcelable {

    private String uid;
    private String uname;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUname() {
        return uname;
    }


    public User() {
    }

    protected User(Parcel in) {
        uid = in.readString();
        uname = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(uname);
    }

    /**
     * 自己定义的readFromParcel方法, 这样才支持 定向tag out
     *
     * @param dest
     */
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        uid = dest.readString();
        uname = dest.readString();
    }

    @Override
    public String toString() {
        return String.format("[uid:%s, uname:%s]", uid, uname);
    }
}
