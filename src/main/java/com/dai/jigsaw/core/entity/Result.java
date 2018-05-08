package com.dai.jigsaw.core.entity;

import java.io.Serializable;

/**
 * Result : 响应的结果对象
 *
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 6288374846131788743L;

    /**
     * 信息
     */
    private String message;

    /**
     * 状态码
     */
    private int statusCode;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 数据
     */
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    
    
    public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Result() {

    }
}
