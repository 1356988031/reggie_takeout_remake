package com.wushubin.reggie_takeout_remake.dto;


import com.wushubin.reggie_takeout_remake.entity.Setmeal;
import com.wushubin.reggie_takeout_remake.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
