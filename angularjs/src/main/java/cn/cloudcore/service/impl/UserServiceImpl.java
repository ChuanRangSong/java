package cn.cloudcore.service.impl;

import cn.cloudcore.dao.IUserDao;
import cn.cloudcore.model.User;
import cn.cloudcore.service.IUserService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Client;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户
     */
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User findById(int id) {
        return userDao.findById(id);
    }
}
