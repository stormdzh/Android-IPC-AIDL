package com.ljd.aidl;

//导入所需要使用的非默认支持数据类型的包， 和java类似
import com.ljd.aidl.entity.User;
//接口定义语言， 当然要定义接口啦， 和java接口类似
interface IUserManager {

    //所有的返回值前都不需要加任何东西，不管是什么数据类型
    User getUser();

    //传参时除了Java基本类型以及String，CharSequence之外的类型
    //都需要在前面加上定向tag，具体加什么量需而定
    void setUser(in User user);

    //修改用户的姓名
    void setUserName(String name);

}