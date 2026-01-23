package com.itranswarp.learnjava.student.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itranswarp.learnjava.student.common.StudentScoreVO;
import com.itranswarp.learnjava.student.entity.Score;
import com.itranswarp.learnjava.student.mapper.ScoreMapper;
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
 * ScoreServiceImpl 单元测试
 * 遵循 AIR 原则：自动化、独立性、可重复
 */
@ExtendWith(MockitoExtension.class)
class ScoreServiceImplTest {

    @Mock
    private ScoreMapper scoreMapper;

    @InjectMocks
    private ScoreServiceImpl scoreService;

    private Score sampleScore;
    private StudentScoreVO sampleStudentScoreVO;

    @BeforeEach
    void setUp() {
        sampleScore = new Score();
        sampleScore.setId(1);
        sampleScore.setStudentId(100);
        sampleScore.setCourseId(200);
        sampleScore.setScore(85);

        sampleStudentScoreVO = new StudentScoreVO();
        sampleStudentScoreVO.setId(1);
        sampleStudentScoreVO.setStudentId(100);
        sampleStudentScoreVO.setStudentName("张三");
        sampleStudentScoreVO.setCourseId(200);
        sampleStudentScoreVO.setCourseName("Spring Boot");
        sampleStudentScoreVO.setScore(85);
    }

    @Test
    void testAddScore() {
        // 准备
        when(scoreMapper.insert(any(Score.class))).thenReturn(1);

        // 执行
        int result = scoreService.addScore(sampleScore);

        // 验证
        assertThat(result).isEqualTo(1);
        verify(scoreMapper, times(1)).insert(sampleScore);
    }

    @Test
    void testGetScoreById() {
        // 准备
        when(scoreMapper.selectById(1)).thenReturn(sampleScore);

        // 执行
        Score result = scoreService.getScoreById(1);

        // 验证
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getScore()).isEqualTo(85);
        verify(scoreMapper, times(1)).selectById(1);
    }

    @Test
    void testGetScoreById_NotFound() {
        // 准备
        when(scoreMapper.selectById(999)).thenReturn(null);

        // 执行
        Score result = scoreService.getScoreById(999);

        // 验证
        assertThat(result).isNull();
        verify(scoreMapper, times(1)).selectById(999);
    }

    @Test
    void testUpdateScore() {
        // 准备
        when(scoreMapper.updateById(any(Score.class))).thenReturn(1);

        // 执行
        int result = scoreService.updateScore(sampleScore);

        // 验证
        assertThat(result).isEqualTo(1);
        verify(scoreMapper, times(1)).updateById(sampleScore);
    }

    @Test
    void testDeleteScore() {
        // 准备
        when(scoreMapper.deleteById(1)).thenReturn(1);

        // 执行
        int result = scoreService.deleteScore(1);

        // 验证
        assertThat(result).isEqualTo(1);
        verify(scoreMapper, times(1)).deleteById(1);
    }

    @Test
    void testGetScoresByStudentId() {
        // 准备
        List<StudentScoreVO> scoreList = Arrays.asList(
                createStudentScoreVO(1, 100, "张三", 200, "Spring Boot", 85),
                createStudentScoreVO(2, 100, "张三", 201, "MyBatis Plus", 90)
        );
        when(scoreMapper.selectScoreByStudentId(100)).thenReturn(scoreList);

        // 执行
        List<StudentScoreVO> result = scoreService.getScoresByStudentId(100);

        // 验证
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCourseName()).isEqualTo("Spring Boot");
        assertThat(result.get(1).getCourseName()).isEqualTo("MyBatis Plus");
        verify(scoreMapper, times(1)).selectScoreByStudentId(100);
    }

    @Test
    void testGetScoresByStudentId_EmptyResult() {
        // 准备
        when(scoreMapper.selectScoreByStudentId(999)).thenReturn(Collections.emptyList());

        // 执行
        List<StudentScoreVO> result = scoreService.getScoresByStudentId(999);

        // 验证
        assertThat(result).isEmpty();
        verify(scoreMapper, times(1)).selectScoreByStudentId(999);
    }

    @Test
    void testGetScoresByCourseId() {
        // 准备
        List<StudentScoreVO> scoreList = Arrays.asList(
                createStudentScoreVO(1, 100, "张三", 200, "Spring Boot", 85),
                createStudentScoreVO(2, 101, "李四", 200, "Spring Boot", 88)
        );
        when(scoreMapper.selectScoreByCourseId(200)).thenReturn(scoreList);

        // 执行
        List<StudentScoreVO> result = scoreService.getScoresByCourseId(200);

        // 验证
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getStudentName()).isEqualTo("张三");
        assertThat(result.get(1).getStudentName()).isEqualTo("李四");
        verify(scoreMapper, times(1)).selectScoreByCourseId(200);
    }

    @Test
    void testGetScorePage_WithoutConditions() {
        // 准备
        Page<StudentScoreVO> page = new Page<>(1, 10);
        page.setRecords(Arrays.asList(
                createStudentScoreVO(1, 100, "张三", 200, "Spring Boot", 85),
                createStudentScoreVO(2, 101, "李四", 201, "MyBatis Plus", 90)
        ));
        page.setTotal(2);
        when(scoreMapper.selectScorePage(any(Page.class), eq(null), eq(null))).thenReturn(page);

        // 执行
        IPage<StudentScoreVO> result = scoreService.getScorePage(1, 10, null, null);

        // 验证
        assertThat(result).isNotNull();
        assertThat(result.getRecords()).hasSize(2);
        assertThat(result.getTotal()).isEqualTo(2);
        verify(scoreMapper, times(1)).selectScorePage(any(Page.class), eq(null), eq(null));
    }

    @Test
    void testGetScorePage_WithStudentNameCondition() {
        // 准备
        Page<StudentScoreVO> page = new Page<>(1, 10);
        page.setRecords(Collections.singletonList(
                createStudentScoreVO(1, 100, "张三", 200, "Spring Boot", 85)
        ));
        page.setTotal(1);
        when(scoreMapper.selectScorePage(any(Page.class), eq("张"), eq(null))).thenReturn(page);

        // 执行
        IPage<StudentScoreVO> result = scoreService.getScorePage(1, 10, "张", null);

        // 验证
        assertThat(result).isNotNull();
        assertThat(result.getRecords()).hasSize(1);
        assertThat(result.getTotal()).isEqualTo(1);
        verify(scoreMapper, times(1)).selectScorePage(any(Page.class), eq("张"), eq(null));
    }

    @Test
    void testGetScorePage_WithCourseNameCondition() {
        // 准备
        Page<StudentScoreVO> page = new Page<>(1, 10);
        page.setRecords(Collections.singletonList(
                createStudentScoreVO(1, 100, "张三", 200, "Spring Boot", 85)
        ));
        page.setTotal(1);
        when(scoreMapper.selectScorePage(any(Page.class), eq(null), eq("Spring"))).thenReturn(page);

        // 执行
        IPage<StudentScoreVO> result = scoreService.getScorePage(1, 10, null, "Spring");

        // 验证
        assertThat(result).isNotNull();
        assertThat(result.getRecords()).hasSize(1);
        assertThat(result.getTotal()).isEqualTo(1);
        verify(scoreMapper, times(1)).selectScorePage(any(Page.class), eq(null), eq("Spring"));
    }

    @Test
    void testGetScorePage_WithBothConditions() {
        // 准备
        Page<StudentScoreVO> page = new Page<>(1, 10);
        page.setRecords(Collections.singletonList(
                createStudentScoreVO(1, 100, "张三", 200, "Spring Boot", 85)
        ));
        page.setTotal(1);
        when(scoreMapper.selectScorePage(any(Page.class), eq("张三"), eq("Spring Boot"))).thenReturn(page);

        // 执行
        IPage<StudentScoreVO> result = scoreService.getScorePage(1, 10, "张三", "Spring Boot");

        // 验证
        assertThat(result).isNotNull();
        assertThat(result.getRecords()).hasSize(1);
        assertThat(result.getTotal()).isEqualTo(1);
        verify(scoreMapper, times(1)).selectScorePage(any(Page.class), eq("张三"), eq("Spring Boot"));
    }

    private StudentScoreVO createStudentScoreVO(Integer id, Integer studentId, String studentName,
                                                Integer courseId, String courseName, Integer score) {
        StudentScoreVO vo = new StudentScoreVO();
        vo.setId(id);
        vo.setStudentId(studentId);
        vo.setStudentName(studentName);
        vo.setCourseId(courseId);
        vo.setCourseName(courseName);
        vo.setScore(score);
        return vo;
    }
}