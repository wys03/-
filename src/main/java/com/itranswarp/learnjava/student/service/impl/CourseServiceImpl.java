package com.itranswarp.learnjava.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itranswarp.learnjava.student.entity.Course;
import com.itranswarp.learnjava.student.mapper.CourseMapper;
import com.itranswarp.learnjava.student.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程服务实现类
 */
@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    @CacheEvict(value = "courseCache", allEntries = true)
    public int addCourse(Course course) {
        log.info("添加课程：{}", course);

        // 检查课程名是否已存在
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getCourseName, course.getCourseName());
        Long count = courseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new IllegalArgumentException("课程名称已存在：" + course.getCourseName());
        }

        return courseMapper.insert(course);
    }


    @Override
    @Cacheable(value = "courseCache", key = "#id")
    public Course getCourseById(Integer id) {
        log.info("查询课程ID：{}", id);
        return courseMapper.selectById(id);
    }

    @Override
    @Cacheable(value = "courseListCache")
    public List<Course> getAllCourses() {
        log.info("查询所有课程");
        return courseMapper.selectList(null);
    }

    @Override
    @CachePut(value = "courseCache", key = "#course.id")
    @CacheEvict(value = "courseListCache", allEntries = true)
    public int updateCourse(Course course) {
        log.info("更新课程：{}", course);
        return courseMapper.updateById(course);
    }

    @Override
    @CacheEvict(value = {"courseCache", "courseListCache"}, key = "#id")
    public int deleteCourse(Integer id) {
        log.info("删除课程ID：{}", id);
        return courseMapper.deleteById(id);
    }

    @Override
    public IPage<Course> getCoursePage(int current, int size, String courseName) {
        log.info("分页查询课程，当前页：{}，每页条数：{}，课程名称条件：{}", current, size, courseName);

        Page<Course> page = new Page<>(current, size);
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();

        if (courseName != null && !courseName.trim().isEmpty()) {
            wrapper.like(Course::getCourseName, courseName);
        }

        return courseMapper.selectPage(page, wrapper);
    }
}
