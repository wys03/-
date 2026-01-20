package com.itranswarp.learnjava.student.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itranswarp.learnjava.student.common.PageResult;
import com.itranswarp.learnjava.student.common.Result;
import com.itranswarp.learnjava.student.entity.Course;
import com.itranswarp.learnjava.student.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 创建课程
     * POST /api/courses
     */
    @PostMapping
    public Result<Integer> createCourse(@RequestBody Course course) {
        log.info("创建课程：{}", course);
        try {
            int result = courseService.addCourse(course);
            if (result > 0) {
                return Result.success(result);
            }
            return Result.error("创建课程失败");
        } catch (Exception e) {
            log.error("创建课程异常：{}", e.getMessage(), e);
            return Result.error("创建课程异常：" + e.getMessage());
        }
    }

    /**
     * 根据ID查询课程
     * GET /api/courses/{id}
     */
    @GetMapping("/{id}")
    public Result<Course> getCourseById(@PathVariable Integer id) {
        log.info("查询课程ID：{}", id);
        try {
            Course course = courseService.getCourseById(id);
            if (course != null) {
                return Result.success(course);
            }
            return Result.error("课程不存在");
        } catch (Exception e) {
            log.error("查询课程异常：{}", e.getMessage(), e);
            return Result.error("查询课程异常：" + e.getMessage());
        }
    }

    /**
     * 查询所有课程
     * GET /api/courses/all
     */
    @GetMapping("/all")
    public Result<List<Course>> getAllCourses() {
        log.info("查询所有课程");
        try {
            List<Course> courses = courseService.getAllCourses();
            return Result.success(courses);
        } catch (Exception e) {
            log.error("查询所有课程异常：{}", e.getMessage(), e);
            return Result.error("查询所有课程异常：" + e.getMessage());
        }
    }

    /**
     * 分页查询课程
     * GET /api/courses?page=1&size=10&courseName=数学
     */
    @GetMapping
    public Result<PageResult<Course>> getCourses(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String courseName) {
        log.info("分页查询课程，page：{}，size：{}，courseName：{}", page, size, courseName);
        try {
            IPage<Course> resultPage = courseService.getCoursePage(page, size, courseName);

            PageResult<Course> pageResult = new PageResult<>();
            pageResult.setTotal(resultPage.getTotal());
            pageResult.setPages(resultPage.getPages());
            pageResult.setCurrent((int) resultPage.getCurrent());
            pageResult.setSize((int) resultPage.getSize());
            pageResult.setRecords(resultPage.getRecords());

            return Result.success(pageResult);
        } catch (Exception e) {
            log.error("分页查询课程异常：{}", e.getMessage(), e);
            return Result.error("分页查询课程异常：" + e.getMessage());
        }
    }

    /**
     * 更新课程信息
     * PUT /api/courses/{id}
     */
    @PutMapping("/{id}")
    public Result<Integer> updateCourse(@PathVariable Integer id, @RequestBody Course course) {
        log.info("更新课程ID：{}，信息：{}", id, course);
        try {
            course.setId(id);
            int result = courseService.updateCourse(course);
            if (result > 0) {
                return Result.success(result);
            }
            return Result.error("更新课程失败");
        } catch (Exception e) {
            log.error("更新课程异常：{}", e.getMessage(), e);
            return Result.error("更新课程异常：" + e.getMessage());
        }
    }

    /**
     * 删除课程
     * DELETE /api/courses/{id}
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteCourse(@PathVariable Integer id) {
        log.info("删除课程ID：{}", id);
        try {
            int result = courseService.deleteCourse(id);
            if (result > 0) {
                return Result.success(result);
            }
            return Result.error("删除课程失败");
        } catch (Exception e) {
            log.error("删除课程异常：{}", e.getMessage(), e);
            return Result.error("删除课程异常：" + e.getMessage());
        }
    }
}
