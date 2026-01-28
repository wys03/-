package com.itranswarp.learnjava.student.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itranswarp.learnjava.student.common.StudentScoreVO;
import com.itranswarp.learnjava.student.entity.Score;

import java.util.List;

/**
 * 成绩服务接口
 */
public interface ScoreService {

    /**
     * 添加成绩
     */
    int addScore(Score score);

    /**
     * 根据ID查询成绩
     */
    Score getScoreById(Integer id);

    /**
     * 更新成绩
     */
    int updateScore(Score score);

    /**
     * 删除成绩
     */
    int deleteScore(Integer id);

    /**
     * 根据学生ID查询成绩详情（多表查询）
     */
    List<StudentScoreVO> getScoresByStudentId(Integer studentId);

    /**
     * 根据课程ID查询成绩详情（多表查询）
     */
    List<StudentScoreVO> getScoresByCourseId(Integer courseId);

    /**
     * 分页查询成绩详情（带条件）
     */
    IPage<StudentScoreVO> getScorePage(int current, int size, String studentNo, String courseName);
}
