package com.baidu.order.service.impl;

import com.baidu.order.dao.OrderDao;
import com.baidu.order.model.Order;
import com.baidu.order.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by song on 2017/3/4.
 */
@Service
public class OrderServiceImpl implements OrderService{
    @Resource
    private OrderDao orderDao;

    /**
     * 查询所有订单
     * @return
     */
    public List<Order> getAll(){
        return orderDao.findAll();
    }
}
