package cn.com.dhcc.server.common.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseEntity<T> implements Serializable {

    private boolean success;

    private String message;

    private T data;

    public ResponseEntity() {
    }

    public ResponseEntity(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseEntity(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
