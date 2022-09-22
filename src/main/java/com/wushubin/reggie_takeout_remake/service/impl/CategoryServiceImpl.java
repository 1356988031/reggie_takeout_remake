package com.wushubin.reggie_takeout_remake.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushubin.reggie_takeout_remake.common.CustomException;
import com.wushubin.reggie_takeout_remake.entity.Category;
import com.wushubin.reggie_takeout_remake.entity.Dish;
import com.wushubin.reggie_takeout_remake.entity.Setmeal;
import com.wushubin.reggie_takeout_remake.mapper.CategoryMapper;
import com.wushubin.reggie_takeout_remake.service.CategoryService;
import com.wushubin.reggie_takeout_remake.service.DishService;
import com.wushubin.reggie_takeout_remake.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: CategoryServiceImpl
 * @Description: TODO
 * @Version: 1.0
 * @Author: 吴曙镔
 * @Date: 2022/9/21 15:33
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 删除分类管理时，检查是否关联有菜品和套餐信息，如果有关联则不能删除，直接抛出异常
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(lqw1);
        if (count1 > 0)
            throw new CustomException("该类别有菜品关联，无法删除");

        LambdaQueryWrapper<Setmeal> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(lqw2);
        if (count2 > 0)
            throw new CustomException("该类别有套餐关联，无法删除");

        //正常删除
        super.removeById(id);

    }
}
