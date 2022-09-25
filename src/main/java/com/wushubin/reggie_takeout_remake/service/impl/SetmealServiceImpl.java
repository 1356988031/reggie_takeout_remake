package com.wushubin.reggie_takeout_remake.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushubin.reggie_takeout_remake.common.CustomException;
import com.wushubin.reggie_takeout_remake.dto.SetmealDto;
import com.wushubin.reggie_takeout_remake.entity.Setmeal;
import com.wushubin.reggie_takeout_remake.entity.SetmealDish;
import com.wushubin.reggie_takeout_remake.mapper.SetmealMapper;
import com.wushubin.reggie_takeout_remake.service.SetmealDishService;
import com.wushubin.reggie_takeout_remake.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息
        this.save(setmealDto);

        Long id = setmealDto.getId();
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(id);
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);

    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        for (Long id : ids){
            //如果套餐在售则不能删除
            Setmeal setmeal = this.getById(id);
            if (setmeal.getStatus() != 0)
                throw new CustomException("套餐正在售卖，无法删除！");
            //删除套餐基本信息
            this.removeById(id);
            //删除套餐包含的菜品信息
            LambdaQueryWrapper<SetmealDish> qw = new LambdaQueryWrapper<>();
            qw.eq(SetmealDish::getSetmealId,id);
            setmealDishService.remove(qw);
        }
    }


    @Override
    public SetmealDto getByIdWithDish(Long id) {
        SetmealDto setmealDto = new SetmealDto();
        //查出套餐的基本数据
        Setmeal setmeal = this.getById(id);
        BeanUtils.copyProperties(setmeal,setmealDto);

        LambdaQueryWrapper<SetmealDish> qw = new LambdaQueryWrapper<>();
        qw.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(qw);
        setmealDto.setSetmealDishes(list);

        return setmealDto;
    }

    @Transactional
    @Override
    public void updateWithDish(SetmealDto setmealDto) {
        //更新套餐基本信息
        this.updateById(setmealDto);
        //删除套餐中的所有菜品，然后再添加
        Long setmealId = setmealDto.getId();
        LambdaQueryWrapper<SetmealDish> qw = new LambdaQueryWrapper<>();
        qw.eq(SetmealDish::getSetmealId,setmealId);
        setmealDishService.remove(qw);

        List<SetmealDish> list = setmealDto.getSetmealDishes();
        list = list.stream().map((item ->{
            item.setSetmealId(setmealDto.getId());
            return item;
        })).collect(Collectors.toList());
        setmealDishService.saveBatch(list);

    }
}
