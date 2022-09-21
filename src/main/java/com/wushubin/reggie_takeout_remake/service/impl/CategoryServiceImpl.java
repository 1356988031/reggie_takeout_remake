package com.wushubin.reggie_takeout_remake.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wushubin.reggie_takeout_remake.entity.Category;
import com.wushubin.reggie_takeout_remake.mapper.CategoryMapper;
import com.wushubin.reggie_takeout_remake.service.CategoryService;
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
}
