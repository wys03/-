package com.itranswarp.learnjava.student.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itranswarp.learnjava.student.entity.Student;

import java.util.List;

/**
 * 学生服务接口
 */
public interface StudentService {

    /**
     * 添加学生
     */
    int addStudent(Student student);

    /**
     * 根据ID查询学生
     */
    Student getStudentById(Integer id);

    /**
     * 查询所有学生
     */
    List<Student> getAllStudents();

    /**
     * 更新学生信息
     */
    int updateStudent(Student student);

    /**
     * 删除学生
     */
    int deleteStudent(Integer id);

    /**
     * 分页查询学生（带条件）
     */
    IPage<Student> getStudentPage(int current, int size, String name);

    /**
     * 根据学号查询学生
     */
    Student getStudentByStudentNo(String studentNo);
}
