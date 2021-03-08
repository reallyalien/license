package cn.com.dhcc.server.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryResult<T> implements Serializable {

    //查询结果总数
    private long total;

    //查询结果
    private List<T> list;
}
