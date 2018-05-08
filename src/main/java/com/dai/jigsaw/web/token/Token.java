package com.dai.jigsaw.web.token;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.dai.jigsaw.core.util.StringUtil;

/**
 * token生成管理类
 * @author daidai
 *
 */
public abstract class Token {
	public static String KEY_NAME = "token";
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
	
	/**
	 * 在request里获取token
	 * @param request
	 * @return
	 */
    public String getToken(HttpServletRequest request){
    	String token = request.getParameter(KEY_NAME);
        if(StringUtil.isBlank(token)){
        	token = request.getHeader(KEY_NAME);
        }
        if(StringUtil.isBlank(token)){
        	token = (String)request.getSession().getAttribute(KEY_NAME);
        }
        if(StringUtil.isBlank(token)){
        	Cookie[] cookies = request.getCookies();
        	for (Cookie cookie : cookies) {
        		if(cookie.getName().equalsIgnoreCase(KEY_NAME)){
        			token = cookie.getValue();
        		}
			}
        }
        return StringUtil.isBlank(token)?null:token;
    }
	
}
