package cn.cloudcore.zookeeper;


import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Test {

    private static String connectString = "47.92.34.189:2181,47.92.34.189:2182,47.92.34.189:2183";

    private static int sessionTimeout = 100;

    private static String root = "/Master";

    private static ZooKeeper zookeeper;

    public static void main(String[] args) {

        try {

            CountDownLatch latch = new CountDownLatch(1);

            zookeeper = new ZooKeeper(connectString, sessionTimeout, event -> {
                System.out.println("获取连接");
                latch.countDown();
            });

            latch.await();

            for (int i = 0; i < 1000; i++) {

                String path = lock(zookeeper);

                System.out.println("执行代码！-----------" + i);

                unLock(zookeeper, path);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String lock(ZooKeeper zookeeper) throws KeeperException, InterruptedException {
        String path = zookeeper.create("/Master/master", "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        getLock(zookeeper, path);
        return path;
    }

    private static void unLock(ZooKeeper zookeeper, String path) throws KeeperException, InterruptedException {
        zookeeper.delete(path, -1);
    }

    private static void getLock(ZooKeeper zookeeper, String path) throws KeeperException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        List<String> children = zookeeper.getChildren("/Master", false);
        Collections.sort(children);

        if (!("/Master/" + children.get(0)).equals(path)) {
            Stat stat = zookeeper.exists("/Master/" + children.get(0), event -> latch.countDown());
            if (null == stat) {
                getLock(zookeeper, path);
            } else {
                latch.await();
                getLock(zookeeper, path);
            }
        }
    }
/*
    void getLock() throws KeeperException, InterruptedException{
        List<String> children = zookeeper.getChildren(root, false);
        Collections.sort(children);
        if(myZnode.equals(root+"/"+nodes[0])){
            doAction();
        } else{
            waitForLock(nodes[0]);
        }
    }
    void waitForLock(String lower) throws InterruptedException, KeeperException {
        Stat stat = zookeeper.exists(root + "/" + lower,true);
        if(stat != null){
            mutex.wait();
        }
        else{
            getLock();
        }
    }
*/
}
