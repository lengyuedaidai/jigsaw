package com.dai.jigsaw.core.feature.cache.redis;

import java.lang.reflect.Method;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 缓存机制说明：所有的查询结果都放进了缓存，也就是把MySQL查询的结果放到了redis中去，
 * 然后第二次发起该条查询时就可以从redis中去读取查询的结果，从而不与MySQL交互，从而达到优化的效果，
 * redis的查询速度之于MySQL的查询速度相当于 内存读写速度 /硬盘读写速度 
 * @Cacheable("a")注解的意义就是把该方法的查询结果放到redis中去，下一次再发起查询就去redis中去取，存在redis中的数据的key就是a；
 * @CacheEvict(value={"a","b"},allEntries=true) 的意思就是执行该方法后要清除redis中key名称为a,b的数据；
 */
public class RedisCacheConfig extends CachingConfigurerSupport {
	 private volatile JedisConnectionFactory jedisConnectionFactory;
	    private volatile RedisTemplate<String, String> redisTemplate;
	    private volatile RedisCacheManager redisCacheManager;

	    public RedisCacheConfig() {
	        super();
	    }

	    /**
	     * 带参数的构造方法 初始化所有的成员变量
	     * 
	     * @param jedisConnectionFactory
	     * @param redisTemplate
	     * @param redisCacheManager
	     */
	    public RedisCacheConfig(JedisConnectionFactory jedisConnectionFactory, RedisTemplate<String, String> redisTemplate,
	            RedisCacheManager redisCacheManager) {
	        this.jedisConnectionFactory = jedisConnectionFactory;
	        this.redisTemplate = redisTemplate;
	        this.redisCacheManager = redisCacheManager;
	    }

	    public JedisConnectionFactory getJedisConnecionFactory() {
	        return jedisConnectionFactory;
	    }

	    public RedisTemplate<String, String> getRedisTemplate() {
	        return redisTemplate;
	    }

	    public RedisCacheManager getRedisCacheManager() {
	        return redisCacheManager;
	    }

	    @Bean
	    public KeyGenerator customKeyGenerator() {
	        return new KeyGenerator() {
	            @Override
	            public Object generate(Object target, Method method, Object... objects) {
	                StringBuilder sb = new StringBuilder();
	                sb.append(target.getClass().getName());
	                sb.append(method.getName());
	                for (Object obj : objects) {
	                    sb.append(obj.toString());
	                }
	                return sb.toString();
	            }
	        };
	    }
}
