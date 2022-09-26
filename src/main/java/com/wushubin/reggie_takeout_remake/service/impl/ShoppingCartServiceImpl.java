package com.wushubin.reggie_takeout_remake.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushubin.reggie_takeout_remake.entity.ShoppingCart;
import com.wushubin.reggie_takeout_remake.mapper.ShoppingCartMapper;
import com.wushubin.reggie_takeout_remake.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
