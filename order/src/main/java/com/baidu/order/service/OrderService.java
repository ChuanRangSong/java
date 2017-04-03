package com.baidu.order.service;

import com.baidu.order.model.Order;

import java.util.List;

/**
 * Created by song on 2017/3/4.
 */
public interface OrderService {
    List<Order> getAll();
}
