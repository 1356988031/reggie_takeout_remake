package com.wushubin.reggie_takeout_remake.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wushubin.reggie_takeout_remake.common.R;
import com.wushubin.reggie_takeout_remake.entity.Category;
import com.wushubin.reggie_takeout_remake.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: CategoryController
 * @Description: TODO
 * @Version: 1.0
 * @Author: 吴曙镔
 * @Date: 2022/9/21 15:35
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * 分页查询分类管理数据
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        Page pageInfo = new Page(page,pageSize);
        categoryService.page(pageInfo);
        return R.success(pageInfo);
    }

    @PostMapping
    public R<String> save(@RequestBody Category category){
        LambdaQueryWrapper<Category> lqw =  new LambdaQueryWrapper<>();
        lqw.eq(Category::getName,category.getName());
        Category serviceOne = categoryService.getOne(lqw);
        if (serviceOne != null)
            return R.error("此分类已存在，请勿重复添加");

        categoryService.save(category);
        return R.success("添加成功");
    }

    /**
     * 修改分类数据
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改成功");
    }

    /**
     * 根据Id删除分类数据
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> deleteById(Long id){
        categoryService.removeById(id);
        return R.success("删除成功");
    }

}
