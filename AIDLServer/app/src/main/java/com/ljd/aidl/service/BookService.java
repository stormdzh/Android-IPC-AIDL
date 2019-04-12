package com.ljd.aidl.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.ljd.aidl.BookManager;
import com.ljd.aidl.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class BookService extends Service {
    private String TAG = BookService.class.getSimpleName();

    //包含Book对象的list
    private List<Book> mBooks = new ArrayList<Book>();

    /**
     * 根据AIDL文件 编译器为我们生成的BookManager
     */
    private final BookManager.Stub mBookManager = new BookManager.Stub() {

        @Override
        public List<Book> getBooks() throws RemoteException {
            //客户端来获取数据时， 返回服务端的数据
            synchronized (this) {
                Log.e(TAG, "invoking getBooks() method , now the list is : " + mBooks.toString());
                if (mBooks != null)
                    return mBooks;

                return new ArrayList<Book>();
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            //客户端发来修改数据的请求， 服务端添加对应数据
            synchronized (this) {
                if (mBooks == null) {
                    mBooks = new ArrayList<Book>();
                }

                if (book == null) {
                    Log.e(TAG, "service add book: book is null");
                    return;
                }
                mBooks.add(book);
                //打印mBooks列表，观察客户端传过来的值
                Log.e(TAG, "invoking addBooks() method , now the list is : " + mBooks.toString());
            }

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        //启动时就去 加一本书
        Book book = new Book();
        book.setName("服务端书籍");
        book.setPrice(100);
        try {
            mBookManager.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(getClass().getSimpleName(), String.format("on bind,intent = %s", intent.toString()));
        return mBookManager;
    }

}
