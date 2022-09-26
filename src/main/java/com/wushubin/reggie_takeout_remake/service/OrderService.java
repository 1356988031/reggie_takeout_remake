package com.wushubin.reggie_takeout_remake.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wushubin.reggie_takeout_remake.entity.Orders;


public interface OrderService extends IService<Orders> {

    /**
     * 用户下单
     * @param orders
     */
    public void submit(Orders orders);
}
