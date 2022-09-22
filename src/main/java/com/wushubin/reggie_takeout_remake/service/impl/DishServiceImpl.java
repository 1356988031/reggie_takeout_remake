package com.wushubin.reggie_takeout_remake.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushubin.reggie_takeout_remake.dto.DishDto;
import com.wushubin.reggie_takeout_remake.entity.Category;
import com.wushubin.reggie_takeout_remake.entity.Dish;
import com.wushubin.reggie_takeout_remake.entity.DishFlavor;
import com.wushubin.reggie_takeout_remake.mapper.DishMapper;
import com.wushubin.reggie_takeout_remake.service.CategoryService;
import com.wushubin.reggie_takeout_remake.service.DishFlavorService;
import com.wushubin.reggie_takeout_remake.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: DishServiceImpl
 * @Description: TODO
 * @Version: 1.0
 * @Author: 吴曙镔
 * @Date: 2022/9/21 18:20
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;

    @Transactional
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        //菜品口味
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);

    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        DishDto dishDto = new DishDto();
        Dish dish = this.getById(id);
        BeanUtils.copyProperties(dish, dishDto);

        LambdaQueryWrapper<DishFlavor> qw = new LambdaQueryWrapper<>();
        qw.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> dishFlavorList = dishFlavorService.list(qw);
        dishDto.setFlavors(dishFlavorList);

        return dishDto;
    }

    @Transactional
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.updateById(dishDto);

        //清理当前菜品对应口味数据---dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        //添加当前提交过来的口味数据---dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        //删除菜品基本信息
        this.removeById(id);

        //清理当前菜品对应口味数据---dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,id);

        dishFlavorService.remove(queryWrapper);
    }
}
