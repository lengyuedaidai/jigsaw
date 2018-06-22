package com.dai.jigsaw.core.shiro;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.dai.jigsaw.web.token.Token;

public class SatelessShiroToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String token;
	private long time = 60*60*5;
	public SatelessShiroToken(final String username, final String password, final Token tokenManger,final long time) {
		super(username, password);
		setTime(time);
		createToken(tokenManger);
	}
	private void createToken(Token tokenManger){
		if(tokenManger!=null){
			Map info = new HashMap<String, String>();
			info.put("userName", getUsername());
			setToken(tokenManger.create(info, time));
		}
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
}
