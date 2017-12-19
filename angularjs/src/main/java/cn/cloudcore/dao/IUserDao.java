package cn.cloudcore.dao;

import cn.cloudcore.model.User;
import org.apache.ibatis.annotations.Param;

public interface IUserDao {

    User findByUsername(@Param("username") String username);

    User findById(@Param("id") int findById);
}
