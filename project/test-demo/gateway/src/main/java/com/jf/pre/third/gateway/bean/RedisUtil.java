package com.jf.pre.third.gateway.bean;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/** 
 * redis cache 工具类 
 * @author Eric
 */
@Service
public final class RedisUtil {

    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    public RedisTemplate<Serializable, Object> redisTemplate;

    @Autowired
    @Qualifier("lockScript")
    private RedisScript<Integer> acquireLockWithTimeout;

    @Autowired
    @Qualifier("unLockScript")
    private RedisScript<Integer> releaseLock;

  /** 
   * 批量删除对应的value 
   * 
   * @param keys 
   */
  public void remove(final String... keys) { 
    for (String key : keys) { 
      remove(key); 
    } 
  } 
  
  /** 
   * 批量删除key 
   * @param pattern 正则表达式
   */
  public void removePattern(final String pattern) { 
    Set<Serializable> keys = redisTemplate.keys(pattern); 
    if (keys.size() > 0) {
        redisTemplate.delete(keys);
    }
  } 
  
  /** 
   * 删除对应的value 
   * 
   * @param key 
   */
  public void remove(final String key) { 
    if (exists(key)) { 
      redisTemplate.delete(key); 
    } 
  } 
  
  /** 
   * 判断缓存中是否有对应的value 
   * 
   * @param key 
   * @return 
   */
  public boolean exists(final String key) { 
    return redisTemplate.hasKey(key); 
  } 
  
  /** 
   * 读取缓存 
   * 
   * @param key 
   * @return 
   */
  public Object get(final String key) { 
    Object result = null; 
    ValueOperations<Serializable, Object> operations = redisTemplate
        .opsForValue(); 
    result = operations.get(key);
    return result;
  } 
  
  /** 
   * 写入缓存 
   * 
   * @param key 
   * @param value 
   * @return 
   */
  public boolean set(final String key, Object value) { 
    boolean result = false; 
    try { 
      ValueOperations<Serializable, Object> operations = redisTemplate
          .opsForValue(); 
      operations.set(key, value);
      
      result = true; 
    } catch (Exception e) { 
      e.printStackTrace(); 
    } 
    return result; 
  } 
  
  /**
   * 设置缓存超时时间，单位秒（s)
   * @param key
   * @param value
   * @param timeout
   * @return
   */
  public boolean set(final String key, Object value, long timeout) { 
	    boolean result = false; 
	    try { 
	      ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
	      operations.set(key, value, timeout, TimeUnit.SECONDS);
	      
	      result = true; 
	    } catch (Exception e) { 
	      e.printStackTrace(); 
	    } 
	    return result; 
	  }
  
  /** 
   * 写入缓存 
   * 
   * @param key 
   * @param value 
   * @return 
   */
  public boolean set(final String key, Object value, Long expireTime) { 
    boolean result = false; 
    try { 
      ValueOperations<Serializable, Object> operations = redisTemplate
          .opsForValue(); 
      operations.set(key, value); 
      redisTemplate.expire(key, expireTime, TimeUnit.SECONDS); 
      result = true; 
    } catch (Exception e) { 
      e.printStackTrace(); 
    } 
    return result; 
  } 
  
  /** 
   * 删除对应的value 
   * 
   */
  public Set keys(String pattern) { 
      return redisTemplate.keys(pattern);
  }
  
  public void setRedisTemplate( 
      RedisTemplate<Serializable, Object> redisTemplate) {
    this.redisTemplate = redisTemplate; 
  }
  
  /***
   * 分布式锁，加锁
   * @param key
   * @param value 当前时间+超时时间
   * @return 锁住返回true
   */
  public boolean lock(String key, String value) {
	  
      if(redisTemplate.opsForValue().setIfAbsent(key, value)) {
          return true;
      }
      
      //如果锁超时
      String currentValue = (String) redisTemplate.opsForValue().get(key);
      
      if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()){
          //获取上一个锁的时间
          String oldvalue  = (String) redisTemplate.opsForValue().getAndSet(key,value);
          if(!StringUtils.isEmpty(oldvalue) && oldvalue.equals(currentValue)){
              return true;
          }
      }
      
      return false;
  }
  
  /***
   * 分布式锁，解锁
   * @param key
   * @param value
   * @return
   */
  public void unlock(String key, String value){
      try {
          String currentValue = (String) redisTemplate.opsForValue().get(key);
          if(!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
        	  redisTemplate.opsForValue().getOperations().delete(key);
          }
      } catch (Exception e) {
          logger.error("解锁异常");
      }
  }

    public int tryLock(String lockKey, String value, int lockTime) {
        return redisTemplate.execute(acquireLockWithTimeout, Collections.singletonList(lockKey), lockTime, value);
    }

    public boolean releaseLock(String lockKey, String lockValue) {
        Integer releaseResult = (Integer) redisTemplate.execute(releaseLock, Collections.singletonList(lockKey), lockValue);
        if (releaseResult.equals(1)) {
            return true;
        }
        return false;
    }

}
