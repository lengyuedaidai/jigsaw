package com.dai.jigsaw.web.token;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.dai.jigsaw.core.exceptions.TokenException;
import com.dai.jigsaw.core.feature.cache.redis.RedisUtil;

public class RedisToken extends Token {

	@Autowired
	private RedisUtil redisUtil;
	private boolean isRenew;
	
	public RedisToken(){
		this(true);
	}
	public RedisToken(boolean isRenew){
		this.isRenew = isRenew;
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
		Map<String,Object> map = new HashMap<String, Object>();
		map.putAll(info);
		if(!redisUtil.hmset(token, map,time)){
			throw new TokenException("生成token失败，请检查redis是否正常");
		}
		return token;
	}

	@Override
	public boolean validate(String token) {
		if(redisUtil.hasKey(token)){
			if(isRenew){
				redisUtil.expire(token, redisUtil.getExpire(token));	
			}
			return true;
		}
		return false;
	}

	@Override
	public Map<String, String> getInfo(String token) {
		if(redisUtil.hasKey(token)){
			Map<String, String> result = new HashMap<String, String>();
			Map<Object, Object> map = redisUtil.hmget(token);
			for(Object key : map.keySet()){
				result.put(key.toString(), map.get(key)==null?null:map.get(key).toString());
			}
			return result;
		}
		return null;
	}

}
