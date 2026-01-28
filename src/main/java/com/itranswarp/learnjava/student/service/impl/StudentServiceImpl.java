package com.itranswarp.learnjava.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itranswarp.learnjava.student.entity.Student;
import com.itranswarp.learnjava.student.mapper.StudentMapper;
import com.itranswarp.learnjava.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生服务实现类
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;


    @Override
    @CacheEvict(value = "studentCache", allEntries = true)
    public int addStudent(Student student) {
        log.info("添加学生：{}", student);
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getStudentNo, student.getStudentNo());
        Long count = studentMapper.selectCount(wrapper);
        if (count > 0) {
            throw new IllegalArgumentException("该学号已存在：" + student.getStudentNo());
        }
        return studentMapper.insert(student);
    }

    @Override
    @Cacheable(value = "studentCache", key = "#id")
    public Student getStudentById(Integer id) {
        log.info("查询学生ID：{}", id);
        return studentMapper.selectById(id);
    }

    @Override
    @Cacheable(value = "studentListCache")
    public List<Student> getAllStudents() {
        log.info("查询所有学生");
        return studentMapper.selectList(null);
    }

    @Override
    @CachePut(value = "studentCache", key = "#student.id")
    @CacheEvict(value = "studentListCache", allEntries = true)
    public int updateStudent(Student student) {
        log.info("更新学生：{}", student);
        return studentMapper.updateById(student);
    }

    @Override
    @CacheEvict(value = {"studentCache", "studentListCache"}, key = "#id")
    public int deleteStudent(Integer id) {
        log.info("删除学生ID：{}", id);
        return studentMapper.deleteById(id);
    }

    @Override
    public IPage<Student> getStudentPage(int current, int size, String name) {
        log.info("分页查询学生，当前页：{}，每页条数：{}，姓名条件：{}", current, size, name);

        Page<Student> page = new Page<>(current, size);
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();

        if (name != null && !name.trim().isEmpty()) {
            wrapper.like(Student::getName, name);
        }

        return studentMapper.selectPage(page, wrapper);
    }

    @Override
    public Student getStudentByStudentNo(String studentNo) {
        log.info("根据学号查询学生：{}", studentNo);
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getStudentNo, studentNo);
        return studentMapper.selectOne(wrapper);
    }

}
