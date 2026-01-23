package com.itranswarp.learnjava.student.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itranswarp.learnjava.student.entity.Course;
import com.itranswarp.learnjava.student.mapper.CourseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * CourseServiceImpl 单元测试
 * 遵循 AIR 原则：自动化、独立性、可重复
 */
@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course sampleCourse;

    @BeforeEach
    void setUp() {
        sampleCourse = new Course();
        sampleCourse.setId(1);
        sampleCourse.setCourseName("Spring Boot");
        sampleCourse.setCredit(3);
    }

    @Test
    void testAddCourse() {
        // 准备
        when(courseMapper.insert(any(Course.class))).thenReturn(1);

        // 执行
        int result = courseService.addCourse(sampleCourse);

        // 验证
        assertThat(result).isEqualTo(1);
        verify(courseMapper, times(1)).insert(sampleCourse);
    }

    @Test
    void testGetCourseById() {
        // 准备
        when(courseMapper.selectById(1)).thenReturn(sampleCourse);

        // 执行
        Course result = courseService.getCourseById(1);

        // 验证
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getCourseName()).isEqualTo("Spring Boot");
        verify(courseMapper, times(1)).selectById(1);
    }

    @Test
    void testGetCourseById_NotFound() {
        // 准备
        when(courseMapper.selectById(999)).thenReturn(null);

        // 执行
        Course result = courseService.getCourseById(999);

        // 验证
        assertThat(result).isNull();
        verify(courseMapper, times(1)).selectById(999);
    }

    @Test
    void testGetAllCourses() {
        // 准备
        List<Course> courseList = Arrays.asList(
                createCourse(1, "Spring Boot", 3),
                createCourse(2, "MyBatis Plus", 2)
        );
        when(courseMapper.selectList(null)).thenReturn(courseList);

        // 执行
        List<Course> result = courseService.getAllCourses();

        // 验证
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCourseName()).isEqualTo("Spring Boot");
        assertThat(result.get(1).getCourseName()).isEqualTo("MyBatis Plus");
        verify(courseMapper, times(1)).selectList(null);
    }

    @Test
    void testUpdateCourse() {
        // 准备
        when(courseMapper.updateById(any(Course.class))).thenReturn(1);

        // 执行
        int result = courseService.updateCourse(sampleCourse);

        // 验证
        assertThat(result).isEqualTo(1);
        verify(courseMapper, times(1)).updateById(sampleCourse);
    }

    @Test
    void testDeleteCourse() {
        // 准备
        when(courseMapper.deleteById(1)).thenReturn(1);

        // 执行
        int result = courseService.deleteCourse(1);

        // 验证
        assertThat(result).isEqualTo(1);
        verify(courseMapper, times(1)).deleteById(1);
    }

    @Test
    void testGetCoursePage_WithoutCondition() {
        // 准备
        Page<Course> page = new Page<>(1, 10);
        page.setRecords(Arrays.asList(
                createCourse(1, "Spring Boot", 3),
                createCourse(2, "MyBatis Plus", 2)
        ));
        page.setTotal(2);
        when(courseMapper.selectPage(any(Page.class), any())).thenReturn(page);

        // 执行
        IPage<Course> result = courseService.getCoursePage(1, 10, null);

        // 验证
        assertThat(result).isNotNull();
        assertThat(result.getRecords()).hasSize(2);
        assertThat(result.getTotal()).isEqualTo(2);
        verify(courseMapper, times(1)).selectPage(any(Page.class), any());
    }

    @Test
    void testGetCoursePage_WithCourseNameCondition() {
        // 准备
        Page<Course> page = new Page<>(1, 10);
        page.setRecords(Collections.singletonList(
                createCourse(1, "Spring Boot", 3)
        ));
        page.setTotal(1);
        when(courseMapper.selectPage(any(Page.class), any())).thenReturn(page);

        // 执行
        IPage<Course> result = courseService.getCoursePage(1, 10, "Spring");

        // 验证
        assertThat(result).isNotNull();
        assertThat(result.getRecords()).hasSize(1);
        assertThat(result.getTotal()).isEqualTo(1);
        verify(courseMapper, times(1)).selectPage(any(Page.class), any());
    }

    @Test
    void testGetCoursePage_WithEmptyCourseNameCondition() {
        // 准备
        Page<Course> page = new Page<>(1, 10);
        page.setRecords(Arrays.asList(
                createCourse(1, "Spring Boot", 3),
                createCourse(2, "MyBatis Plus", 2)
        ));
        page.setTotal(2);
        when(courseMapper.selectPage(any(Page.class), any())).thenReturn(page);

        // 执行
        IPage<Course> result = courseService.getCoursePage(1, 10, "");

        // 验证
        assertThat(result).isNotNull();
        assertThat(result.getRecords()).hasSize(2);
        assertThat(result.getTotal()).isEqualTo(2);
        verify(courseMapper, times(1)).selectPage(any(Page.class), any());
    }

    private Course createCourse(Integer id, String courseName, Integer credit) {
        Course course = new Course();
        course.setId(id);
        course.setCourseName(courseName);
        course.setCredit(credit);
        return course;
    }
}