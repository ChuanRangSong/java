package cn.cloudcore.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class LockTest {

    private static ZooKeeper zooKeeper;

    private static String connectString = "47.92.34.189:2181,47.92.34.189:2182,47.92.34.189:2183";

    private static int sessionTimeout = 100;

    private static String rootPath = "/locks";

    private static int num = 100;

    public static void main(String[] args) {
//        initZooKeeper();
//
//        createLock();
//
//        doSomething();

        //closeZooKeeper();

        test();
    }

    public static void doSomething() {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                String lock = lock();
                int a = num;
                try {
                    Thread.sleep(100);
                    System.out.println(a);
                    num = a - 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                unLock(lock);
            }).start();
        }
    }

    public static String lock() {
        try {
            String value = zooKeeper.create(rootPath + "/lock", "a".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            List<String> locks = zooKeeper.getChildren(rootPath, false);
            Collections.sort(locks);
            int index = locks.indexOf(value.substring(rootPath.length() + 1));
            if (index != 0) {
                CountDownLatch latch = new CountDownLatch(1);

                Stat stat = zooKeeper.exists(rootPath + "/" + locks.get(index - 1), event -> latch.countDown());

                if (stat != null ) {
                    latch.await();
                }
            }
            return value;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void unLock(String lock) {
        try {
            zooKeeper.delete(lock, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createLock() {
        try {
            Stat stat = zooKeeper.exists(rootPath, false);
            if (null == stat) {
                zooKeeper.create(rootPath, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initZooKeeper() {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            zooKeeper = new ZooKeeper(connectString, sessionTimeout, event -> {
                System.out.println("获取连接");
                latch.countDown();
            });

            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeZooKeeper() {
        try {
           zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void test() {
        //创建zookeeper的客户端
        try {


            for (int i = 0; i < 20; i++) {
                new Thread(() -> {

                    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
                    CuratorFramework client = null;
                    try {
                        client = CuratorFrameworkFactory.newClient("47.92.34.189:2181,47.92.34.189:2182,47.92.34.189:2183", retryPolicy);
                        client.start();
                        //创建分布式锁, 锁空间的根节点路径为/curator/lock
                        InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock");
                        mutex.acquire();
                        int a = num;
                        Thread.sleep(100);
                        System.out.println(a);
                        num = a - 1;
                        mutex.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (null != client) {
                            client.close();
                        }
                    }

                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //关闭客户端
    }

}
