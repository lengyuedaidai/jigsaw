package com.dai.daobuild.test.redis;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.dai.jigsaw.core.feature.cache.redis.RedisUtil;
import com.dai.jigsaw.core.feature.orm.mybatis.Page;
import com.dai.jigsaw.core.feature.orm.mybatis.WhereParam;
import com.dai.jigsaw.core.feature.test.TestSupport;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisServiceTest extends TestSupport {

	@Autowired
	 private RedisUtil redisUtil;

	    @Test
	    public void basicTest(){
	        //存值
	        redisUtil.set("user.name", "aaa");
	        redisUtil.set("user.pass", "123");
	         
	        //取值
	        String name = redisUtil.get("user.name").toString();
	        String pass = redisUtil.get("user.pass").toString();
	        System.out.println(name+"---"+pass);
	        //断言
	        Assert.assertEquals("aaa", name);
	        //Assert.assertEquals("1234", pass);//错误
	        Assert.assertEquals("123", pass);
	         
	        redisUtil.del("user.name");
	        Boolean result = redisUtil.hasKey("user.name");
	        Assert.assertEquals(false, result);
	         
	        result = redisUtil.hasKey("user.pass");
	        Assert.assertEquals(true, result);
	    }

}
