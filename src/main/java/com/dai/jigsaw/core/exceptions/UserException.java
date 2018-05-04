package com.dai.jigsaw.core.exceptions;

/**
 * UserException : 用户自定义异常
 *
 */
public class UserException extends RuntimeException {
	
	public UserException(String message){
		super(message);
	}
	
    /**
     * 异常发生时间
     */
    private long date = System.currentTimeMillis();

    public long getDate() {
        return date;
    }
}
