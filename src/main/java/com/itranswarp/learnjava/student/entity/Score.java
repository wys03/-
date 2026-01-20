package com.itranswarp.learnjava.student.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 成绩实体类
 */
@Data
@TableName("score")
public class Score{
    private static final long serialVersionUID = 1L;
    private Integer id;             //  主键
    private Integer studentId;      //  学生ID（关联student表）
    private Integer courseId;       //  课程ID（关联course表）
    private Integer score;          //  成绩（0-100）
}
