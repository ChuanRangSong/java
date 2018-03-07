package cn.cloudcore.zookeeper;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();    //注意这个地方

        new Thread(() -> insert(Thread.currentThread(), lock)).start();
        new Thread(() -> insert(Thread.currentThread(), lock)).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public static void insert(Thread thread, Lock lock) {

        if(lock.tryLock()){
            try {
                System.out.println(thread.getName() + "得到了锁");
                thread.sleep(1000);
            } catch (Exception e) {
                // TODO: handle exception
            }finally {
                System.out.println(thread.getName() + "释放了锁");
                lock.unlock();
            }
        } else {
            System.out.println(thread.getName() + "获取锁失败！");
        }
    }

}
