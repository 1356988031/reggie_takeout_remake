package com.wushubin.reggie_takeout_remake.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wushubin.reggie_takeout_remake.entity.Category;

/**
 * @ClassName: CategoryService
 * @Description: TODO
 * @Version: 1.0
 * @Author: 吴曙镔
 * @Date: 2022/9/21 15:32
 */
public interface CategoryService extends IService<Category> {

    void remove(Long id);
}
