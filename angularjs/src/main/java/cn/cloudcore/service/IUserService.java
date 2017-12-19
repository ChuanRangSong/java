package cn.cloudcore.service;

import cn.cloudcore.model.User;

public interface IUserService {

    User findByUsername(String username);

    User findById(int id);
}
