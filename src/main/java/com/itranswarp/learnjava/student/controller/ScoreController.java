package com.itranswarp.learnjava.student.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itranswarp.learnjava.student.common.PageResult;
import com.itranswarp.learnjava.student.common.Result;
import com.itranswarp.learnjava.student.common.StudentScoreVO;
import com.itranswarp.learnjava.student.entity.Score;
import com.itranswarp.learnjava.student.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 成绩管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/api/scores")
@CrossOrigin(origins = "http://localhost:5173")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    /**
     * 录入成绩
     * POST /api/scores
     */
    @PostMapping
    public Result<Integer> createScore(@RequestBody Score score) {
        log.info("录入成绩：{}", score);
        try {
            int result = scoreService.addScore(score);
            if (result > 0) {
                return Result.success(result);
            }
            return Result.error("录入成绩失败");
        } catch (Exception e) {
            log.error("录入成绩异常：{}", e.getMessage(), e);
            return Result.error("录入成绩异常：" + e.getMessage());
        }
    }

    /**
     * 根据ID查询成绩
     * GET /api/scores/{id}
     */
    @GetMapping("/{id}")
    public Result<Score> getScoreById(@PathVariable Integer id) {
        log.info("查询成绩ID：{}", id);
        try {
            Score score = scoreService.getScoreById(id);
            if (score != null) {
                return Result.success(score);
            }
            return Result.error("成绩不存在");
        } catch (Exception e) {
            log.error("查询成绩异常：{}", e.getMessage(), e);
            return Result.error("查询成绩异常：" + e.getMessage());
        }
    }

    /**
     * 分页查询成绩
     * GET /api/scores?page=1&size=10&studentName=张&courseName=数学
     */
    @GetMapping
    public Result<PageResult<StudentScoreVO>> getScores(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String studentName,
            @RequestParam(required = false) String courseName) {
        log.info("分页查询成绩，page：{}，size：{}，studentName：{}，courseName：{}",
                page, size, studentName, courseName);
        try {
            IPage<StudentScoreVO> resultPage = scoreService.getScorePage(page, size, studentName, courseName);

            PageResult<StudentScoreVO> pageResult = new PageResult<>();
            pageResult.setTotal(resultPage.getTotal());
            pageResult.setPages(resultPage.getPages());
            pageResult.setCurrent((int) resultPage.getCurrent());
            pageResult.setSize((int) resultPage.getSize());
            pageResult.setRecords(resultPage.getRecords());

            return Result.success(pageResult);
        } catch (Exception e) {
            log.error("分页查询成绩异常：{}", e.getMessage(), e);
            return Result.error("分页查询成绩异常：" + e.getMessage());
        }
    }

    /**
     * 更新成绩
     * PUT /api/scores/{id}
     */
    @PutMapping("/{id}")
    public Result<Integer> updateScore(@PathVariable Integer id, @RequestBody Score score) {
        log.info("更新成绩ID：{}，信息：{}", id, score);
        try {
            score.setId(id);
            int result = scoreService.updateScore(score);
            if (result > 0) {
                return Result.success(result);
            }
            return Result.error("更新成绩失败");
        } catch (Exception e) {
            log.error("更新成绩异常：{}", e.getMessage(), e);
            return Result.error("更新成绩异常：" + e.getMessage());
        }
    }

    /**
     * 删除成绩
     * DELETE /api/scores/{id}
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteScore(@PathVariable Integer id) {
        log.info("删除成绩ID：{}", id);
        try {
            int result = scoreService.deleteScore(id);
            if (result > 0) {
                return Result.success(result);
            }
            return Result.error("删除成绩失败");
        } catch (Exception e) {
            log.error("删除成绩异常：{}", e.getMessage(), e);
            return Result.error("删除成绩异常：" + e.getMessage());
        }
    }

    /**
     * 根据学生ID查询成绩详情（多表查询）
     * GET /api/students/{studentId}/scores
     */
    @GetMapping("/students/{studentId}")
    public Result<List<StudentScoreVO>> getScoresByStudentId(@PathVariable Integer studentId) {
        log.info("查询学生ID {} 的所有成绩（多表查询）", studentId);
        try {
            List<StudentScoreVO> scores = scoreService.getScoresByStudentId(studentId);
            return Result.success(scores);
        } catch (Exception e) {
            log.error("查询学生成绩异常：{}", e.getMessage(), e);
            return Result.error("查询学生成绩异常：" + e.getMessage());
        }
    }

    /**
     * 根据课程ID查询成绩详情（多表查询）
     * GET /api/courses/{courseId}/scores
     */
    @GetMapping("/courses/{courseId}")
    public Result<List<StudentScoreVO>> getScoresByCourseId(@PathVariable Integer courseId) {
        log.info("查询课程ID {} 的所有成绩（多表查询）", courseId);
        try {
            List<StudentScoreVO> scores = scoreService.getScoresByCourseId(courseId);
            return Result.success(scores);
        } catch (Exception e) {
            log.error("查询课程成绩异常：{}", e.getMessage(), e);
            return Result.error("查询课程成绩异常：" + e.getMessage());
        }
    }
}
