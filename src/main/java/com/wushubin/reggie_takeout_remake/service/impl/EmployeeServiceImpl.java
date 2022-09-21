package com.wushubin.reggie_takeout_remake.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wushubin.reggie_takeout_remake.entity.Employee;
import com.wushubin.reggie_takeout_remake.mapper.EmployeeMapper;
import com.wushubin.reggie_takeout_remake.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
