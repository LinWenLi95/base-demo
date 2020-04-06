package com.lwl.base.code.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页实体
 * @author lwl
 * @param <T>
 */
@Data
public class Page<T> implements Serializable {

    private List<T> records;
    private long total;
    private long limit;
    private long current;

    private Page() {}

    public Page(long current, long limit, long total) {
        this.current = current;
        this.total = total;
        this.limit = limit;
    }
}
