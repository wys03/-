package com.itranswarp.learnjava.student.common;

import lombok.Data;
import java.io.Serializable;

/**
 * 学生成绩VO
 */
@Data
public class StudentScoreVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;             //  主键
    private Integer studentId;      //  学生ID（关联student表）
    private String studentNo;      //  学生学号
    private String studentName;     //  学生姓名
    private Integer courseId;       //  课程ID（关联course表）
    private String courseName;      //  课程名称
    private Integer score;          //  成绩（0-100）
}
