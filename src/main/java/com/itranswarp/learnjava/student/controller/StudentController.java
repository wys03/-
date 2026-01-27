package com.itranswarp.learnjava.student.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itranswarp.learnjava.student.common.PageResult;
import com.itranswarp.learnjava.student.common.Result;
import com.itranswarp.learnjava.student.entity.Student;
import com.itranswarp.learnjava.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 创建学生
     * POST /api/students
     */
    @PostMapping
    public Result<Integer> createSetudent(@RequestBody Student student) {
        log.info("创建学生：{}", student);
        try {
            int result = studentService.addStudent(student);
            if (result > 0) {
                return Result.success(result);
            }
            return Result.error("创建学生失败");
        } catch (Exception e) {
            log.error("创建学生异常：{}", e.getMessage(), e);
            return Result.error("创建学生异常：" + e.getMessage());
        }
    }

    /**
     * 根据ID查询学生
     * GET /api/students/{id}
     */
    @GetMapping("/{id}")
    public Result<Student> getStudentById(@PathVariable Integer id) {
        log.info("查询学生ID：{}", id);
        try {
            Student student = studentService.getStudentById(id);
            if (student != null) {
                return Result.success(student);
            }
            return Result.error("学生不存在");
        } catch (Exception e) {
            log.error("查询学生异常：{}", e.getMessage(), e);
            return Result.error("查询学生异常：" + e.getMessage());
        }
    }

    /**
     * 查询所有学生
     * GET /api/students/all
     */
    @GetMapping("/all")
    public Result<List<Student>> getAllStudents() {
        log.info("查询所有学生");
        try {
            List<Student> students = studentService.getAllStudents();
            return Result.success(students);
        } catch (Exception e) {
            log.error("查询所有学生异常：{}", e.getMessage(), e);
            return Result.error("查询所有学生异常：" + e.getMessage());
        }
    }

    /**
     * 分页查询学生
     * GET /api/students?page=1&size=10&name=张
     */
    @GetMapping
    public Result<PageResult<Student>> getStudents(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name) {
        log.info("分页查询学生，page：{}，size：{}，name：{}", page, size, name);
        try {
            IPage<Student> resultPage = studentService.getStudentPage(page, size, name);

            PageResult<Student> pageResult = new PageResult<>();
            pageResult.setTotal(resultPage.getTotal());
            pageResult.setPages(resultPage.getPages());
            pageResult.setCurrent((int) resultPage.getCurrent());
            pageResult.setSize((int) resultPage.getSize());
            pageResult.setRecords(resultPage.getRecords());

            return Result.success(pageResult);
        } catch (Exception e) {
            log.error("分页查询学生异常：{}", e.getMessage(), e);
            return Result.error("分页查询学生异常：" + e.getMessage());
        }
    }

    /**
     * 更新学生信息
     * PUT /api/students/{id}
     */
    @PutMapping("/{id}")
    public Result<Integer> updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        log.info("更新学生ID：{}，信息：{}", id, student);
        try {
            student.setId(id);
            int result = studentService.updateStudent(student);
            if (result > 0) {
                return Result.success(result);
            }
            return Result.error("更新学生失败");
        } catch (Exception e) {
            log.error("更新学生异常：{}", e.getMessage(), e);
            return Result.error("更新学生异常：" + e.getMessage());
        }
    }

    /**
     * 删除学生
     * DELETE /api/students/{id}
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteStudent(@PathVariable Integer id) {
        log.info("删除学生ID：{}", id);
        try {
            int result = studentService.deleteStudent(id);
            if (result > 0) {
                return Result.success(result);
            }
            return Result.error("删除学生失败");
        } catch (Exception e) {
            log.error("删除学生异常：{}", e.getMessage(), e);
            return Result.error("删除学生异常：" + e.getMessage());
        }
    }
}
