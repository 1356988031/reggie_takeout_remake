package com.wushubin.reggie_takeout_remake.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wushubin.reggie_takeout_remake.common.R;
import com.wushubin.reggie_takeout_remake.dto.DishDto;
import com.wushubin.reggie_takeout_remake.entity.Category;
import com.wushubin.reggie_takeout_remake.entity.Dish;
import com.wushubin.reggie_takeout_remake.service.CategoryService;
import com.wushubin.reggie_takeout_remake.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: DishController
 * @Description: TODO
 * @Version: 1.0
 * @Author: 吴曙镔
 * @Date: 2022/9/21 18:25
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询菜品数据
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoInfo = new Page<>(page,pageSize);

        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(name),Dish::getName,name);
        dishService.page(pageInfo,lqw);

        BeanUtils.copyProperties(pageInfo,dishDtoInfo,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long id =item.getCategoryId();
            Category category = categoryService.getById(id);
            if (category != null){
                dishDto.setCategoryName(category.getName());
            }
            return dishDto;

        }).collect(Collectors.toList());
        dishDtoInfo.setRecords(list);

        return R.success(dishDtoInfo);
    }

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);

        return R.success("保存成功");
    }

    /**
     * 根据Id获取菜品的属性和口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);

    }

    /**
     * 修改菜品信息
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }

    /**
     * 批量或单个修改菜品状态
     * @param state
     * @param ids
     * @return
     */
    @PostMapping("/status/{state}")
    public R<String> updateStatus(@PathVariable Integer state,  @RequestParam List<Long> ids){
        for (Long id : ids){
            LambdaUpdateWrapper<Dish> qw = new LambdaUpdateWrapper<>();
            qw.eq(Dish::getId,id).set(Dish::getStatus,state);
            dishService.update(qw);
        }
        return R.success("修改成功");
    }

    /**
     * 批量或单个删除菜品
     * @return
     */
    @DeleteMapping
    public R<String> deleteByIds(@RequestParam List<Long> ids){
        for(Long id : ids)
            dishService.deleteById(id);
        return R.success("删除成功");
    }

}
