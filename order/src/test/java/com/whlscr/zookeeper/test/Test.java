package com.whlscr.zookeeper.test;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class Test {
	@org.junit.Test
	public void fun() throws Exception{
		String connectString = "47.92.34.189:2181,47.92.34.189:2182,47.92.34.189:2183";
		int connectionTimeOut = 10000;
		 // 创建一个与服务器的连接
		 final ZooKeeper zk = new ZooKeeper(connectString, connectionTimeOut, 
				 new Watcher() { 
		            // 监控所有被触发的事件
		            public void process(WatchedEvent event) { 
		                System.out.println(event.getPath() +"路径已经触发了" + event.getType() + "事件！");
		            }
		        }); 
		 
		 // 创建一个目录节点
		 zk.create("/testRootPath", "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE,
		   CreateMode.PERSISTENT); 
		 // 创建一个子目录节点
		 zk.create("/testRootPath/testChildPathOne", "testChildDataOne".getBytes(),
		   Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
		 System.out.println(new String(zk.getData("/testRootPath",false,null))); 
		 // 取出子目录节点列表
		 System.out.println(zk.getChildren("/testRootPath",true)); 
		 // 修改子目录节点数据
		 zk.setData("/testRootPath/testChildPathOne","modifyChildDataOne".getBytes(),-1); 
		 System.out.println("目录节点状态：["+zk.exists("/testRootPath",true)+"]"); 
		 // 创建另外一个子目录节点
		 zk.create("/testRootPath/testChildPathTwo", "testChildDataTwo".getBytes(), 
		   Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
		 System.out.println(new String(zk.getData("/testRootPath/testChildPathTwo",true,null))); 
		 // 删除子目录节点
		 zk.delete("/testRootPath/testChildPathTwo",-1); 
		 zk.delete("/testRootPath/testChildPathOne",-1); 
		 // 删除父目录节点
		 zk.delete("/testRootPath",-1); 
		 // 关闭连接
		 
		 zk.close();
	}
	@org.junit.Test
	public void fun1(){
		String fileName = "ljd$fla$jd$fl$";
		
		StringBuffer buffer = new StringBuffer();
		StringBuffer key = new StringBuffer();
		boolean began = false;
		boolean end = false;
		
		for (int i = 0; i < fileName.length(); i++) {
			char c = fileName.charAt(i);
			if (c == '$') {
				if(!began){
					began = true;
					end = false;
				} else {
					began = false;
					end = true;
					buffer.append("++" + key.toString() + "++");
					key.setLength(0);
				}
			}else if (began && !end) {
				key.append(c);
			} else {
				buffer.append(c);
			}
		}
		System.out.println(buffer.toString());
	}
}
