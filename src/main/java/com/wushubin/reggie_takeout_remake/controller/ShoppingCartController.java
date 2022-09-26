package com.wushubin.reggie_takeout_remake.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wushubin.reggie_takeout_remake.common.BaseContext;
import com.wushubin.reggie_takeout_remake.common.R;
import com.wushubin.reggie_takeout_remake.entity.ShoppingCart;
import com.wushubin.reggie_takeout_remake.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: ShoppingCartController
 * @Description: TODO
 * @Version: 1.0
 * @Author: 吴曙镔
 * @Date: 2022/9/26 9:49
 */

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        //获取用户id
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        //获取菜品id
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();
        //判断加入购物车的是菜品还是套餐
        if (dishId != null){
            qw.eq(ShoppingCart::getDishId,dishId);
        }else {
            qw.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart one = shoppingCartService.getOne(qw);

        //判断购物车中是否已有该物品
        if (one != null){
            Integer num = one.getNumber();
            one.setNumber(num + 1);
            shoppingCartService.updateById(one);
        }else {
            shoppingCartService.save(shoppingCart);
            one = shoppingCart;
        }

        return R.success(one);
    }

    /**
     * 查询用户购物车数据
     * @return
     */
    @GetMapping("list")
    public R<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        queryWrapper.orderByDesc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        return R.success(list);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();
        qw.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartService.remove(qw);
        return R.success("购物车清空成功");
    }


}
