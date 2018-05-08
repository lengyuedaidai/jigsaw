package com.dai.jigsaw.core.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * ErrorResult : 用于响应错误的请求的对象
 *
 */
public class ErrorResult extends Result {
    private static final long serialVersionUID = 8567221653356186674L;
    public static int UNKOWN = 100;
    public static int TOKEN_UNKOWN = 200;
    public static int TOKEN_CREATE_FAILD = 201;
    public static int TOKEN_VERIFY_FAILD = 202;
    
    

    public ErrorResult() {
    	this(UNKOWN,"未知错误，请联系管理员");
    }
    public ErrorResult(String message) {
    	this(UNKOWN,message);
    }
    public ErrorResult(int statusCode) {
    	this(statusCode,"");
    }
    public ErrorResult(int statusCode,String message) {
    	super.setSuccess(false);
    	super.setMessage(message);
    	super.setStatusCode(statusCode);
    }
}
