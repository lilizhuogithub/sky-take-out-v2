package com.sky.service;

import com.sky.dto.SetmealDTO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;


public interface SetmealService {
    void save(SetmealDTO setmealDTO);
}
