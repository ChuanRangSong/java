package com.baidu.order;

/**
 * 单例模式
 * Created by song on 2017/3/2.
 */
public class Singleton {
    private Singleton(){
    }
    private static Singleton singleton = new Singleton();
    public static Singleton getTnstence(){
        return singleton;
    }
    public void show(){
        System.out.println(".......................");
    }
}
