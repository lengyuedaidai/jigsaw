package com.dai.jigsaw.web.token;

import java.util.Map;
/**
 * token生成管理类
 * @author daidai
 *
 */
public abstract class Token {
	/**
	 * 生成token
	 * @param info token携带的相关信息
	 * @param time token有效时间(秒)
	 * @return token
	 */
	public abstract String create(Map<String,String> info,long time);
	/**
	 * 验证token有效性
	 * @param token
	 * @return 有效为true
	 */
	public abstract boolean validate(String token);
	/**
	 * 通过token获取token携带信息
	 * @param token
	 * @return 
	 */
	public abstract Map<String,String> getInfo(String token);
}
