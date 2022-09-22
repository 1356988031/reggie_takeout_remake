package com.wushubin.reggie_takeout_remake.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wushubin.reggie_takeout_remake.dto.DishDto;
import com.wushubin.reggie_takeout_remake.entity.Dish;

/**
 * @ClassName: DishService
 * @Description: TODO
 * @Version: 1.0
 * @Author: 吴曙镔
 * @Date: 2022/9/21 18:20
 */
public interface DishService extends IService<Dish> {

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    DishDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应的口味信息
    void updateWithFlavor(DishDto dishDto);

    //根据id删除菜品信息
    void deleteById(Long id);

}
