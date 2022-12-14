package com.wushubin.reggie_takeout_remake.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wushubin.reggie_takeout_remake.dto.SetmealDto;
import com.wushubin.reggie_takeout_remake.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    void removeWithDish(List<Long> ids);

    /**
     * 根据Id查询套餐信息
     * @param id
     * @return
     */
    SetmealDto getByIdWithDish(Long id);

    /**
     * 更新套餐信息
     * @param setmealDto
     */
    void updateWithDish(SetmealDto setmealDto);

}
