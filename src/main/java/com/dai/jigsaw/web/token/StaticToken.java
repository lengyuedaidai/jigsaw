package com.dai.jigsaw.web.token;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import com.dai.jigsaw.core.exceptions.TokenException;

public class StaticToken extends Token {
	private static Map<String, String> cache = new HashMap<String, String>();

	public JwtToken jwtToken;
	private long clearTime = 5*60*10000;//五分钟清理一次
	
	public StaticToken(){
		jwtToken = new JwtToken();
		startClearTimer();
	}
	private void startClearTimer(){
		new Timer().schedule(new ClearCacheTask(jwtToken), clearTime,clearTime);
	}
	
	@Override
	public String create(Map<String, String> info, long time) {
		if(time<=0){
			throw new TokenException("token有效时长必须大于0！");
		}
		if(info==null){
			throw new TokenException("info不能为空！");
		}
		String token = UUID.randomUUID().toString();
		String tokenInfo = jwtToken.create(info, time);
		cache.put(token, tokenInfo);
		return token;
	}

	@Override
	public boolean validate(String token) {
		if(cache.containsKey(token)){
			if(!jwtToken.validate(cache.get(token))){
				synchronized (StaticToken.this) {
					cache.remove(token);
				}
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public Map<String, String> getInfo(String token) {
		if(validate(token)){
			return jwtToken.getInfo(cache.get(token));
		}
		return null;
	}
	/**
	 * 定时清除无效token
	 * @author daidai
	 *
	 */
	class ClearCacheTask extends TimerTask{
		private JwtToken jwtToken;
		ClearCacheTask(JwtToken jwtToken){
			this.jwtToken = jwtToken;
		}
		@Override
		public void run() {
			for(String key:cache.keySet()){
				if(!jwtToken.validate(cache.get(key))){
					synchronized (StaticToken.this) {
						cache.remove(key);
					}
				}
			}
			
		}
		
	}
}
