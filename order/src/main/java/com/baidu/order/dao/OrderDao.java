package com.baidu.order.dao;

import com.baidu.order.model.Order;

import java.util.List;

/**
 * 订单dao层
 * Created by song on 2017/3/4.
 */
public interface OrderDao {
    List<Order>findAll();
}
