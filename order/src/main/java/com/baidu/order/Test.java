package com.baidu.order;

/**
 * 测试类
 * Created by song on 2017/3/2.
 */
public class Test {
    @org.junit.Test
    public void fun(){
        Singleton singleton = Singleton.getTnstence();
        singleton.show();
    }
}
