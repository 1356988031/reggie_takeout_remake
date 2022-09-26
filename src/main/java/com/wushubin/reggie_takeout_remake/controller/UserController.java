package com.wushubin.reggie_takeout_remake.controller;

import com.alibaba.druid.sql.visitor.functions.If;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wushubin.reggie_takeout_remake.common.R;
import com.wushubin.reggie_takeout_remake.entity.User;
import com.wushubin.reggie_takeout_remake.service.UserService;
import com.wushubin.reggie_takeout_remake.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName: UserController
 * @Description: TODO
 * @Version: 1.0
 * @Author: 吴曙镔
 * @Date: 2022/9/25 13:51
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 发送验证码
     * @param user
     * @param httpSession
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sengMsg(@RequestBody User user, HttpSession httpSession){
        if (StringUtils.isNotEmpty(user.getPhone())){
            int code = ValidateCodeUtils.generateValidateCode(4);
            log.info("验证码：" + code);
            httpSession.setAttribute(user.getPhone(),code);
            return R.success("验证码发送成功");
        }
        return R.error("验证码发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession httpSession){
        //获取前端传来的手机和验证码信息
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();

        String codeInSession = httpSession.getAttribute(phone).toString();

        //验证码正确
        if (codeInSession.equals(code)){
            //查看系统中是否有该用户的信息 即手机号
            LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
            qw.eq(User::getPhone,phone);
            User user = userService.getOne(qw);
            if (user == null) {
                //新用户直接注册
                user = new User();
                user.setPhone(phone);
                userService.save(user);
            }
            httpSession.setAttribute("user",user.getId());
            return R.success(user);
        }

        return R.error("登录失败");
    }


}
