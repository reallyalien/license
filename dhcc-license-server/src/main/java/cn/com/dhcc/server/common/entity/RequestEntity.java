package cn.com.dhcc.server.common.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RequestEntity<T> implements Serializable {

    //当前页
    private int pageCurrent = 1;

    //每页显示数量
    private int pageSize = 10;

    //查询条件
    private T query;

}
