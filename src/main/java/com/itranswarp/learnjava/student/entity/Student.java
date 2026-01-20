package com.itranswarp.learnjava.student.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 学生实体类
 */
@Data
@TableName("student")
public class Student {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;             //  主键
    private String name;            //  姓名
    private Integer age;            //  年龄
    private String gender;          //  性别
    private Date birthDate;         //  生日
    private String major;           //  专业
    private String studentNo;       //  学号
}
