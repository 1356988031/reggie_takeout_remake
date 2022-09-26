package com.wushubin.reggie_takeout_remake.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushubin.reggie_takeout_remake.entity.OrderDetail;
import com.wushubin.reggie_takeout_remake.mapper.OrderDetailMapper;
import com.wushubin.reggie_takeout_remake.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}