package com.itranswarp.learnjava.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 课程实体类
 */
@Data
@TableName("course")
public class Course{
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;             //  主键
    private String courseName;      //  课程名称
    private Integer credit;         //  学分
}
