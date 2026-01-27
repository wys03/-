package com.itranswarp.learnjava.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itranswarp.learnjava.student.common.StudentScoreVO;
import com.itranswarp.learnjava.student.entity.Score;
import com.itranswarp.learnjava.student.mapper.ScoreMapper;
import com.itranswarp.learnjava.student.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 成绩服务实现类
 */
@Slf4j
@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    @CacheEvict(value = "scoreCache", allEntries = true)
    public int addScore(Score score) {
        log.info("添加成绩：{}", score);

        // 检查"学生+课程"组合是否已存在
        LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Score::getStudentId, score.getStudentId())
                .eq(Score::getCourseId, score.getCourseId());
        Long count = scoreMapper.selectCount(wrapper);
        if (count > 0) {
            throw new IllegalArgumentException("该学生已存在此课程的成绩记录，请勿重复添加");
        }

        return scoreMapper.insert(score);
    }


    @Override
    @Cacheable(value = "scoreCache", key = "#id")
    public Score getScoreById(Integer id) {
        log.info("查询成绩ID：{}", id);
        return scoreMapper.selectById(id);
    }

    @Override
    @CachePut(value = "scoreCache", key = "#score.id")
    @CacheEvict(value = "scoreListCache", allEntries = true)
    public int updateScore(Score score) {
        log.info("更新成绩：{}", score);
        return scoreMapper.updateById(score);
    }

    @Override
    @CacheEvict(value = {"scoreCache", "scoreListCache"}, key = "#id")
    public int deleteScore(Integer id) {
        log.info("删除成绩ID：{}", id);
        return scoreMapper.deleteById(id);
    }

    @Override
    public List<StudentScoreVO> getScoresByStudentId(Integer studentId) {
        log.info("查询学生ID {} 的所有成绩（多表查询）", studentId);
        return scoreMapper.selectScoreByStudentId(studentId);
    }

    @Override
    public List<StudentScoreVO> getScoresByCourseId(Integer courseId) {
        log.info("查询课程ID {} 的所有成绩（多表查询）", courseId);
        return scoreMapper.selectScoreByCourseId(courseId);
    }

    @Override
    public IPage<StudentScoreVO> getScorePage(int current, int size, String studentName, String courseName) {
        log.info("分页查询成绩，当前页：{}，每页条数：{}，学生姓名：{}，课程名称：{}",
                current, size, studentName, courseName);

        Page<StudentScoreVO> page = new Page<>(current, size);
        return scoreMapper.selectScorePage(page, studentName, courseName);
    }
}
