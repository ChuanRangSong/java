package com.baidu.order.controller;

import com.baidu.order.model.Order;
import com.baidu.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单控制层
 * Created by song on 2017/3/3.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    /**
     * 获取订单列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getList")
    public ModelAndView getList(HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView = new ModelAndView("orderList");
        List<Order> orders = orderService.getAll();
        request.setAttribute("orders", orders);
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }
}
