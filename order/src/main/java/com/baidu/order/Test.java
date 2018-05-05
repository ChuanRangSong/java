package com.baidu.order;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试类
 * Created by song on 2017/3/2.
 */
public class Test {

    private static JedisCluster jedisCluster=null;
    private static Set<HostAndPort> hostAndPorts=null;

    @org.junit.Test
    public void fun(){
        jedisCluster = getJedisCluster();
        System.out.println(jedisCluster.get("name"));
    }

    public static  Set<HostAndPort> getHostAndPort(String hostAndPort){
        Set<HostAndPort> hap = new HashSet();
        String[] hosts = hostAndPort.split(",");
        String[] hs;
        for(String host:hosts){
            hs=host.split(":");
            hap.add(new HostAndPort(hs[0], Integer.parseInt(hs[1])));
        }
        return hap;
    }

    public static JedisCluster getJedisCluster(){
        GenericObjectPoolConfig gopc = new GenericObjectPoolConfig();
        gopc.setMaxTotal(32);
        gopc.setMaxIdle(4);
        gopc.setMaxWaitMillis(6000);
        hostAndPorts = getHostAndPort("47.52.97.135:9001");
        jedisCluster = new JedisCluster(hostAndPorts, 2000, 2000, 3,null,gopc);
        return jedisCluster;
    }
}
