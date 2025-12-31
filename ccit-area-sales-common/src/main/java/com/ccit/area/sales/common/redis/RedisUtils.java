package com.ccit.area.sales.common.redis;

import com.alibaba.fastjson.JSON;
import com.ccit.area.sales.common.constants.GlobalConstants;
import com.ccit.area.sales.common.exception.BusinessException;
import com.ccit.common.utils.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *  * 描述 : 缓存工具类
 *  * 创建人 : yn
 *  * 创建时间 : 2024年7月8日 上午11:07:19
 *  * 版本 : 1.0
 *  * 包名 : com.ccit.area.sales.common.redis
 *  * 类名 : RedisUtils
 */
@Component
@SuppressWarnings({"all"})
@Slf4j
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    public String getDict(String prefix, String key) {

        try {
            Object dictDecrypt = redisTemplate.opsForValue().get("dictionary:dictionaryName");
            String DictStr = AESUtil.decrypt(dictDecrypt.toString(), GlobalConstants.AES_PARAM_KEY);
            Map<String, String> map = JSON.parseObject(DictStr, Map.class);
            return map.get(prefix + key.toLowerCase());
        } catch (Exception e) {
            log.error("获取字典值失败:{}", e.getMessage());
            return key;
        }

    }

    public Map<String, String> getDept(String key) {
        try {
            Object dictDecrypt = redisTemplate.opsForValue().get(key);
            String DeptStr = AESUtil.decrypt(dictDecrypt.toString(), GlobalConstants.AES_PARAM_KEY);
            Map<String, String> map = JSON.parseObject(DeptStr, Map.class);
            return map;
        } catch (Exception e) {
            log.error("获取字典值失败:{}", e.getMessage());
            throw new BusinessException("获取字典值失败!");
        }

    }


    public boolean sethash(final String key, String hashHey, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForHash().put(key, hashHey, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deletehash(final String key, String hashHey) {
        boolean result = false;
        try {
            redisTemplate.opsForHash().delete(key, hashHey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月8日 上午11:08:16
     * 描述 : 写入缓存
     * 包名 : com.ccit.area.sales.common.redis
     * 方法名 : set
     * boolean
     *
     * @throws
     */
    public Map<String, String> gethash(final String key) {
        Map<String, String> entries = new HashMap<>();
        try {
            entries = redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entries;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月8日 上午11:08:16
     * 描述 : 写入缓存
     * 包名 : com.ccit.area.sales.common.redis
     * 方法名 : set
     * boolean
     *
     * @throws
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月8日 上午11:08:21
     * 描述 : 写入缓存设置时效时间
     * 包名 : com.ccit.area.sales.common.redis
     * 方法名 : set
     * boolean
     *
     * @throws
     */
    public boolean set(final String key, Object value, String expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, Long.parseLong(expireTime), timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月8日 上午11:08:26
     * 描述 : 读取缓存
     * 包名 : com.ccit.area.sales.common.redis
     * 方法名 : get
     * Object
     *
     * @throws
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月8日 上午11:08:31
     * 描述 : 删除对应的值
     * 包名 : com.ccit.area.sales.common.redis
     * 方法名 : remove
     * void
     *
     * @throws
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月8日 上午11:08:41
     * 描述 : 批量删除对应的值
     * 包名 : com.ccit.area.sales.common.redis
     * 方法名 : remove
     * void
     *
     * @throws
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 创建人 : yn
     * 创建时间 : 2024年7月8日 上午11:08:56
     * 描述 : 判断缓存中是否有对应的值
     * 包名 : com.ccit.area.sales.common.redis
     * 方法名 : exists
     * boolean
     *
     * @throws
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * @author Boy_is_Chen
     * @param key
     * @param value
     * @param expireTime
     * @param timeUnit
     * @return
     */
    public boolean setNX(String key, String value, Long expireTime, TimeUnit timeUnit) {
        try {
            Boolean isLocked = redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, timeUnit);
            return isLocked;
        } catch (Exception e) {
            log.error("新增分布式锁失败 error:{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
