package cn.cloudcore.redis;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class LockTest {

    private static JedisPool pool = null;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(200);
        config.setMaxIdle(50);
        config.setMinIdle(8);//设置最小空闲数
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
//Idle时进行连接扫描
        config.setTestWhileIdle(true);
//表示idle object evitor两次扫描之间要sleep的毫秒数
        config.setTimeBetweenEvictionRunsMillis(30000);
//表示idle object evitor每次扫描的最多的对象数
        config.setNumTestsPerEvictionRun(10);
//表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
        config.setMinEvictableIdleTimeMillis(60000);
        pool = new JedisPool(config, "47.92.34.189", 6379, 30000);
    }

    private static Lock lock = new Lock(pool);

    private static int n = 100;

    public static void seckill(){
        String identifier = lock.lock("resource", 5000, 10000);
        if (null != identifier) {
            System.out.println(Thread.currentThread().getName() + "-----获得锁----" + identifier);
            int num = n;
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(n);
            n = num - 1;
            lock.unLock("resource", identifier);
        } else {
            //System.out.println(Thread.currentThread().getName() + "--超时");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            new Thread(() -> {
                while (n > 0) {
                    seckill();
                }
            }).start();
        }
    }
}

class Lock {

    private JedisPool jedisPool;

    public Lock(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String lock(String lockName, long acquireTimeOut, long timeOut) {

        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();


            String identifier = UUID.randomUUID().toString();

            long timeEnd = System.currentTimeMillis() + acquireTimeOut;
            while (System.currentTimeMillis() < timeEnd) {

                if ("OK".equals(jedis.set(lockName, identifier, "NX", "PX", timeOut))) {
                    return identifier;
                }
                /*if (jedis.setnx(lockName, identifier) == 1) {
                    jedis.expire(lockName, lockExpire);
                    return identifier;
                }

                if (jedis.ttl(lockName) == -1) {
                    jedis.expire(lockName, lockExpire);
                }*/
//                System.out.println("过程。。。");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        return null;
    }

    public void unLock(String lockName, String identifier) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockName), Collections.singletonList(identifier));
            if (new Long(1).equals(result)) {
                System.out.println(Thread.currentThread().getName() + "释放锁++++" + identifier);
            } else {
                System.out.println(Thread.currentThread().getName() + "释放锁【【【【" + identifier);
            }

            /*if (identifier.equals(jedis.get(lockName))) {
                jedis.del(lockName);
            }*/
//            while (true) {
//                jedis.watch(lockName);
//                if (identifier.equals(jedis.get(lockName))) {
//                    Transaction transaction = jedis.multi();
//                    transaction.del(lockName);
//                    List<Object> results = transaction.exec();
//                    if (results == null) {
//                        continue;
//                    }
//                }
//                jedis.unwatch();
//                break;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }
}
