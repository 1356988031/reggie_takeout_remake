package com.wushubin.reggie_takeout_remake.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wushubin.reggie_takeout_remake.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {

}