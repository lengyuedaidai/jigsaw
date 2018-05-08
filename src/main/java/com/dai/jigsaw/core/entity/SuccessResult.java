package com.dai.jigsaw.core.entity;


public class SuccessResult<T> extends Result {

    private static final long serialVersionUID = 7880907731807860636L;

    /**
     * 自定义返回的结果
     *
     * @param data
     * @param message
     * @param success
     */
    public SuccessResult(T data) {
    	this(data,null);
    }

    /**
     * 成功返回数据和消息
     *
     * @param data
     * @param message
     */
    public SuccessResult(T data, String message) {
    	super.setData(data);
        super.setMessage(message);
        super.setSuccess(true);
    }

}