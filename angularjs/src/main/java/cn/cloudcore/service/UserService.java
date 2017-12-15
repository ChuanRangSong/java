package cn.cloudcore.service;

import cn.cloudcore.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User findByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("123456");
        return user;
    }
}
