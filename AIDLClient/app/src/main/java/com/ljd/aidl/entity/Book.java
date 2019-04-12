package com.ljd.aidl.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    private String name;
    private int price;

    public Book() {

    }

    public Book(Parcel in) {
        name = in.readString();
        price = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    @Override
    public int describeContents() {
        // 默认返回0 即可
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // ：默认生成的模板类的对象只支持为 in 的定向 tag 。
        //为什么呢？因为默认生成的类里面只有 writeToParcel() 方法，而如果要支持为 out 或者 inout 的定向 tag 的话，
        //还需要实现 readFromParcel() 方法

        //把数据写进parcel中， 后面的读值顺序应当是和writeToParcel()方法中一致的
        dest.writeString(name);
        dest.writeInt(price);
    }

    /**
     * 自己定义的readFromParcel方法, 这样才支持 定向tag out
     *
     * @param dest
     */
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        name = dest.readString();
        price = dest.readInt();
    }


    /**
     * 还必须要有Creator, 这是实现parcel接口 所必须的
     */
    public static final Creator<Book> CREATOR = new Creator<Book>() {

        @Override
        public Book[] newArray(int size) {
            // 创建一个 Book数组
            return new Book[size]; //创建
        }

        @Override
        public Book createFromParcel(Parcel source) {
            // 根据传来的数据 创建Book
            return new Book(source);
        }
    };

    @Override
    public String toString() {
        return String.format("[price:%s, name:%s]", price, name);

    }
}