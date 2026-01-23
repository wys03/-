package com.itranswarp.learnjava.student.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itranswarp.learnjava.student.entity.Student;
import com.itranswarp.learnjava.student.mapper.StudentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * StudentServiceImpl 单元测试
 * 遵循 AIR 原则：自动化、独立性、可重复
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student sampleStudent;

    @BeforeEach
    void setUp() {
        sampleStudent = new Student();
        sampleStudent.setId(1);
        sampleStudent.setName("张三");
        sampleStudent.setAge(20);
        sampleStudent.setGender("男");
        sampleStudent.setBirthDate(new Date());
        sampleStudent.setMajor("计算机科学");
        sampleStudent.setStudentNo("20230001");
    }

    @Test
    void testAddStudent() {
        // 准备
        when(studentMapper.insert(any(Student.class))).thenReturn(1);

        // 执行
        int result = studentService.addStudent(sampleStudent);

        // 验证
        assertThat(result).isEqualTo(1);
        verify(studentMapper, times(1)).insert(sampleStudent);
    }

    @Test
    void testGetStudentById() {
        // 准备
        when(studentMapper.selectById(1)).thenReturn(sampleStudent);

        // 执行
        Student result = studentService.getStudentById(1);

        // 验证
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("张三");
        verify(studentMapper, times(1)).selectById(1);
    }

    @Test
    void testGetStudentById_NotFound() {
        // 准备
        when(studentMapper.selectById(999)).thenReturn(null);

        // 执行
        Student result = studentService.getStudentById(999);

        // 验证
        assertThat(result).isNull();
        verify(studentMapper, times(1)).selectById(999);
    }

    @Test
    void testGetAllStudents() {
        // 准备
        List<Student> studentList = Arrays.asList(
                createStudent(1, "张三", "20230001"),
                createStudent(2, "李四", "20230002")
        );
        when(studentMapper.selectList(null)).thenReturn(studentList);

        // 执行
        List<Student> result = studentService.getAllStudents();

        // 验证
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("张三");
        assertThat(result.get(1).getName()).isEqualTo("李四");
        verify(studentMapper, times(1)).selectList(null);
    }

    @Test
    void testUpdateStudent() {
        // 准备
        when(studentMapper.updateById(any(Student.class))).thenReturn(1);

        // 执行
        int result = studentService.updateStudent(sampleStudent);

        // 验证
        assertThat(result).isEqualTo(1);
        verify(studentMapper, times(1)).updateById(sampleStudent);
    }

    @Test
    void testDeleteStudent() {
        // 准备
        when(studentMapper.deleteById(1)).thenReturn(1);

        // 执行
        int result = studentService.deleteStudent(1);

        // 验证
        assertThat(result).isEqualTo(1);
        verify(studentMapper, times(1)).deleteById(1);
    }

    @Test
    void testGetStudentPage_WithoutCondition() {
        // 准备
        Page<Student> page = new Page<>(1, 10);
        page.setRecords(Arrays.asList(
                createStudent(1, "张三", "20230001"),
                createStudent(2, "李四", "20230002")
        ));
        page.setTotal(2);
        when(studentMapper.selectPage(any(Page.class), any())).thenReturn(page);

        // 执行
        IPage<Student> result = studentService.getStudentPage(1, 10, null);

        // 验证
        assertThat(result).isNotNull();
        assertThat(result.getRecords()).hasSize(2);
        assertThat(result.getTotal()).isEqualTo(2);
        verify(studentMapper, times(1)).selectPage(any(Page.class), any());
    }

    @Test
    void testGetStudentPage_WithNameCondition() {
        // 准备
        Page<Student> page = new Page<>(1, 10);
        page.setRecords(Collections.singletonList(
                createStudent(1, "张三", "20230001")
        ));
        page.setTotal(1);
        when(studentMapper.selectPage(any(Page.class), any())).thenReturn(page);

        // 执行
        IPage<Student> result = studentService.getStudentPage(1, 10, "张");

        // 验证
        assertThat(result).isNotNull();
        assertThat(result.getRecords()).hasSize(1);
        assertThat(result.getTotal()).isEqualTo(1);
        verify(studentMapper, times(1)).selectPage(any(Page.class), any());
    }

    @Test
    void testGetStudentPage_WithEmptyNameCondition() {
        // 准备
        Page<Student> page = new Page<>(1, 10);
        page.setRecords(Arrays.asList(
                createStudent(1, "张三", "20230001"),
                createStudent(2, "李四", "20230002")
        ));
        page.setTotal(2);
        when(studentMapper.selectPage(any(Page.class), any())).thenReturn(page);

        // 执行
        IPage<Student> result = studentService.getStudentPage(1, 10, "");

        // 验证
        assertThat(result).isNotNull();
        assertThat(result.getRecords()).hasSize(2);
        assertThat(result.getTotal()).isEqualTo(2);
        verify(studentMapper, times(1)).selectPage(any(Page.class), any());
    }

    private Student createStudent(Integer id, String name, String studentNo) {
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(20);
        student.setGender("男");
        student.setBirthDate(new Date());
        student.setMajor("计算机科学");
        student.setStudentNo(studentNo);
        return student;
    }
}