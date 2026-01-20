package com.itranswarp.learnjava.student.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itranswarp.learnjava.student.entity.Course;

import java.util.List;

/**
 * 课程服务接口
 */
public interface CourseService {

    /**
     * 添加课程
     */
    int addCourse(Course course);

    /**
     * 根据ID查询课程
     */
    Course getCourseById(Integer id);

    /**
     * 查询所有课程
     */
    List<Course> getAllCourses();

    /**
     * 更新课程信息
     */
    int updateCourse(Course course);

    /**
     * 删除课程
     */
    int deleteCourse(Integer id);

    /**
     * 分页查询课程（带条件）
     */
    IPage<Course> getCoursePage(int current, int size, String courseName);
}
