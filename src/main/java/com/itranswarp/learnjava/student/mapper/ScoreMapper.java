package com.itranswarp.learnjava.student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itranswarp.learnjava.student.common.StudentScoreVO;
import com.itranswarp.learnjava.student.entity.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 成绩Mapper接口
 */
@Mapper
public interface ScoreMapper extends BaseMapper<Score> {

    /**
     * 根据学生ID查询成绩详情（关联学生和课程信息）
     * @param studentId 学生ID
     * @return 学生成绩列表
     */
    List<StudentScoreVO> selectScoreByStudentId(@Param("studentId") Integer studentId);

    /**
     * 根据课程ID查询成绩详情（关联学生信息）
     * @param courseId 课程ID
     * @return 学生成绩列表
     */
    List<StudentScoreVO> selectScoreByCourseId(@Param("courseId") Integer courseId);

    /**
     * 分页查询学生成绩详情（带条件）
     * @param page 分页对象
     * @param studentName 学生姓名（可选）
     * @param courseName 课程名称（可选）
     * @return 学生成绩分页结果
     */
    IPage<StudentScoreVO> selectScorePage(
            Page<StudentScoreVO> page,
            @Param("studentName") String studentName,
            @Param("courseName") String courseName
    );
}