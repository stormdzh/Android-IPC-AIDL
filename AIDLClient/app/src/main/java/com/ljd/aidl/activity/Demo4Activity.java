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
import android.widget.Toast;

import com.ljd.aidl.BookManager;
import com.ljd.aidl.client.R;
import com.ljd.aidl.entity.Book;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Demo4Activity extends AppCompatActivity {

//    @Bind(R.id.bind_demo4_btn)
//    LinearLayout mShowLinear;

    private final String TAG = "DEMO4";
    private boolean mIsBindService;
    private BookManager mCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo4);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        unbindService();
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick({R.id.bind_demo4_btn, R.id.unbind_demo4_btn, R.id.get_btn, R.id.set_btn})
    public void onClickButton(View v) {
        switch (v.getId()) {
            case R.id.bind_demo4_btn:
                bindService();
                break;
            case R.id.unbind_demo4_btn:
                Toast.makeText(this, "unbind service success", Toast.LENGTH_SHORT).show();
                unbindService();
                break;
            case R.id.get_btn:

                getData();
                break;
            case R.id.set_btn:
                Book book = new Book();
                book.setName("11111111111111111");
                Log.i("test", "添加数据：" + book.toString());
                book.setPrice(33333);
                try {
                    mCalculate.addBook(book);

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void getData() {
        try {
            List<Book> books = mCalculate.getBooks();
            if (books != null) {
                String str = "";
                for (Book b : books) {
                    str = str + b.toString();
                    str += "\n";
                }

                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                Log.i("test", "获得数据条数++>" + books.size());
                Log.i("test", "获得数据：" + str);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "bind success");
            Toast.makeText(Demo4Activity.this, "bind service success", Toast.LENGTH_SHORT).show();
            mCalculate = BookManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //重新绑定Service防止系统将服务进程杀死而产生的调用错误。
            bindService();
        }
    };

    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("com.ljd.aidl.action.BOOK_SERVICE");
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
