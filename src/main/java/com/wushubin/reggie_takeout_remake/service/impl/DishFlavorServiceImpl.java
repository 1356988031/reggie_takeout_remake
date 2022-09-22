package com.wushubin.reggie_takeout_remake.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushubin.reggie_takeout_remake.entity.DishFlavor;
import com.wushubin.reggie_takeout_remake.mapper.DishFlavorMapper;
import com.wushubin.reggie_takeout_remake.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
