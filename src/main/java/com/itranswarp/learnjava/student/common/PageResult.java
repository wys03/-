package com.itranswarp.learnjava.student.common;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 */
@Data
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long total;        // 总记录数
    private Long pages;        // 总页数
    private Integer current;   // 当前页
    private Integer size;      // 每页条数
    private List<T> records;   // 数据列表

    public PageResult() {}

    /**
     * 分页结果
     * @param total     总记录数
     * @param pages     总页数
     * @param current   当前页
     * @param size      每页条数
     * @param records   数据列表
     */
    public PageResult(Long total, Long pages, Integer current, Integer size, List<T> records) {
        this.total = total;
        this.pages = pages;
        this.current = current;
        this.size = size;
        this.records = records;
    }
}
