-- =============================================
-- 学生管理系统数据库建表脚本
-- 创建时间: 2026/01/23
-- 说明: 遵循智维云Java开发规范 - MySQL数据库建表规约
-- =============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `school_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `school_db`;

-- =============================================
-- 学生表
-- =============================================
DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(50) NOT NULL COMMENT '姓名',
  `age` INT UNSIGNED COMMENT '年龄',
  `gender` VARCHAR(10) COMMENT '性别',
  `birth_date` DATETIME COMMENT '生日',
  `major` VARCHAR(50) COMMENT '专业',
  `student_no` VARCHAR(50) COMMENT '学号',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1是/0否)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_no` (`student_no`),
  KEY `idx_name` (`name`),
  KEY `idx_major` (`major`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生表';

-- =============================================
-- 课程表
-- =============================================
DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `course_code` VARCHAR(50) NOT NULL COMMENT '课程编码',
  `course_name` VARCHAR(100) NOT NULL COMMENT '课程名称',
  `credit` INT UNSIGNED COMMENT '学分',
  `teacher` VARCHAR(50) COMMENT '授课老师',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1是/0否)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_course_code` (`course_code`),
  KEY `idx_course_name` (`course_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程表';

-- =============================================
-- 成绩表
-- =============================================
DROP TABLE IF EXISTS `score`;

CREATE TABLE `score` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `student_id` BIGINT UNSIGNED NOT NULL COMMENT '学生ID',
  `course_id` BIGINT UNSIGNED NOT NULL COMMENT '课程ID',
  `score` INT UNSIGNED COMMENT '成绩(0-100)',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除(1是/0否)',
  PRIMARY KEY (`id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_course_id` (`course_id`),
  UNIQUE KEY `uk_student_course` (`student_id`, `course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='成绩表';

-- =============================================
-- 插入测试数据
-- =============================================

-- 插入学生测试数据
INSERT INTO `student` (`name`, `age`, `gender`, `birth_date`, `major`, `student_no`) VALUES
('张三', 20, '男', '2004-01-15 00:00:00', '计算机科学与技术', '2023001'),
('李四', 21, '女', '2003-05-20 00:00:00', '计算机科学与技术', '2023002'),
('王五', 19, '男', '2005-03-10 00:00:00', '软件工程', '2023003'),
('赵六', 20, '女', '2004-08-25 00:00:00', '软件工程', '2023004'),
('钱七', 22, '男', '2002-12-01 00:00:00', '计算机科学与技术', '2023005');

-- 插入课程测试数据
INSERT INTO `course` (`course_code`, `course_name`, `credit`, `teacher`) VALUES
('CS101', '计算机基础', 3, '张教授'),
('CS102', '数据结构', 4, '李教授'),
('CS103', '算法分析', 4, '王教授'),
('CS104', '数据库原理', 3, '赵教授'),
('CS105', '操作系统', 4, '钱教授');

-- 插入成绩测试数据
INSERT INTO `score` (`student_id`, `course_id`, `score`) VALUES
(1, 1, 85),
(1, 2, 90),
(1, 3, 78),
(2, 1, 92),
(2, 2, 88),
(2, 4, 95),
(3, 1, 75),
(3, 3, 82),
(4, 2, 89),
(4, 4, 91),
(5, 3, 87),
(5, 5, 93);
