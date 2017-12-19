package cn.cloudcore.service;

import cn.cloudcore.model.User;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Client;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource(name="client")
    private Client client;

    public User findByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("123456");
        client.set("key", "value");
        return user;
    }
}
