package com.yichen.cook.food.mapper;

import com.yichen.cook.food.model.foodCookSteps;
import com.yichen.cook.food.model.foodCookStepsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface foodCookStepsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_cook_steps
     *
     * @mbggenerated Thu May 20 15:32:15 CST 2021
     */
    int countByExample(foodCookStepsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_cook_steps
     *
     * @mbggenerated Thu May 20 15:32:15 CST 2021
     */
    int deleteByExample(foodCookStepsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_cook_steps
     *
     * @mbggenerated Thu May 20 15:32:15 CST 2021
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_cook_steps
     *
     * @mbggenerated Thu May 20 15:32:15 CST 2021
     */
    int insert(foodCookSteps record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_cook_steps
     *
     * @mbggenerated Thu May 20 15:32:15 CST 2021
     */
    int insertSelective(foodCookSteps record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_cook_steps
     *
     * @mbggenerated Thu May 20 15:32:15 CST 2021
     */
    List<foodCookSteps> selectByExampleWithBLOBs(foodCookStepsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_cook_steps
     *
     * @mbggenerated Thu May 20 15:32:15 CST 2021
     */
    List<foodCookSteps> selectByExample(foodCookStepsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_cook_steps
     *
     * @mbggenerated Thu May 20 15:32:15 CST 2021
     */
    foodCookSteps selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_cook_steps
     *
     * @mbggenerated Thu May 20 15:32:15 CST 2021
     */
    int updateByExampleSelective(@Param("record") foodCookSteps record, @Param("example") foodCookStepsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_cook_steps
     *
     * @mbggenerated Thu May 20 15:32:15 CST 2021
     */
    int updateByExampleWithBLOBs(@Param("record") foodCookSteps record, @Param("example") foodCookStepsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_cook_steps
     *
     * @mbggenerated Thu May 20 15:32:15 CST 2021
     */
    int updateByExample(@Param("record") foodCookSteps record, @Param("example") foodCookStepsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_cook_steps
     *
     * @mbggenerated Thu May 20 15:32:15 CST 2021
     */
    int updateByPrimaryKeySelective(foodCookSteps record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_cook_steps
     *
     * @mbggenerated Thu May 20 15:32:15 CST 2021
     */
    int updateByPrimaryKeyWithBLOBs(foodCookSteps record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_cook_steps
     *
     * @mbggenerated Thu May 20 15:32:15 CST 2021
     */
    int updateByPrimaryKey(foodCookSteps record);
}