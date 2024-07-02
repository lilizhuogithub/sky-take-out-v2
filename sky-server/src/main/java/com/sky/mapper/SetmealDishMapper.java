package com.sky.mapper;


import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    //select setmeal_id from setmeal_dish where dish_id in (1, 2, 3, 4)
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);


    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBySetId(Long setmealId);

    @Select("select * from setmeal_dish where setmeal_id = #{setmealId};")
    List<SetmealDish> getBySetmealId(Long setmealId);

    void insertBatch(List<SetmealDish> setmealDishes);
}
