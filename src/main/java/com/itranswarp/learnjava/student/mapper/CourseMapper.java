package com.itranswarp.learnjava.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itranswarp.learnjava.student.entity.Course;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程Mapper接口
 * 继承MyBatis-Plus的BaseMapper，自动拥有单表CRUD方法
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

}