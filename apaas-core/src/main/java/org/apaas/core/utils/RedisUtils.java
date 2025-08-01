package org.apaas.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Slf4j
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置缓存
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存并设置过期时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间（秒）
     */
    public void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存
     *
     * @param key 键
     * @return 是否成功
     */
    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 批量删除缓存
     *
     * @param keys 键集合
     * @return 删除数量
     */
    public long delete(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 设置过期时间
     *
     * @param key     键
     * @param timeout 过期时间（秒）
     * @return 是否成功
     */
    public boolean expire(String key, long timeout) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeout, TimeUnit.SECONDS));
    }

    /**
     * 获取过期时间
     *
     * @param key 键
     * @return 过期时间（秒）
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 递增因子
     * @return 递增后的值
     */
    public long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 递减因子
     * @return 递减后的值
     */
    public long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * 获取Hash结构中的属性
     *
     * @param key     键
     * @param hashKey 属性键
     * @return 属性值
     */
    public Object hashGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 向Hash结构中放入一个属性
     *
     * @param key     键
     * @param hashKey 属性键
     * @param value   属性值
     */
    public void hashSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 向Hash结构中放入一个属性并设置过期时间
     *
     * @param key     键
     * @param hashKey 属性键
     * @param value   属性值
     * @param timeout 过期时间（秒）
     */
    public void hashSet(String key, String hashKey, Object value, long timeout) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        expire(key, timeout);
    }

    /**
     * 获取Hash结构中的所有属性
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hashGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除Hash结构中的属性
     *
     * @param key      键
     * @param hashKeys 属性键数组
     * @return 删除数量
     */
    public long hashDelete(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 判断Hash结构中是否有该属性
     *
     * @param key     键
     * @param hashKey 属性键
     * @return 是否存在
     */
    public boolean hashHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获取Set结构
     *
     * @param key 键
     * @return 值
     */
    public Set<Object> setMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 向Set结构中添加属性
     *
     * @param key    键
     * @param values 值
     * @return 添加数量
     */
    public long setAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 向Set结构中添加属性并设置过期时间
     *
     * @param key     键
     * @param timeout 过期时间（秒）
     * @param values  值
     * @return 添加数量
     */
    public long setAdd(String key, long timeout, Object... values) {
        long count = redisTemplate.opsForSet().add(key, values);
        expire(key, timeout);
        return count;
    }

    /**
     * 获取List结构中的属性
     *
     * @param key   键
     * @param start 开始
     * @param end   结束
     * @return 对应的多个键值
     */
    public List<Object> listRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取List结构的长度
     *
     * @param key 键
     * @return 长度
     */
    public long listSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 根据索引获取List中的属性
     *
     * @param key   键
     * @param index 索引
     * @return 对应的多个键值
     */
    public Object listIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 向List结构中添加属性
     *
     * @param key   键
     * @param value 值
     */
    public void listRightPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 向List结构中添加属性并设置过期时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间（秒）
     */
    public void listRightPush(String key, Object value, long timeout) {
        redisTemplate.opsForList().rightPush(key, value);
        expire(key, timeout);
    }

    /**
     * 向List结构中批量添加属性
     *
     * @param key    键
     * @param values 值
     * @return 添加数量
     */
    public long listRightPushAll(String key, List<Object> values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 向List结构中批量添加属性并设置过期时间
     *
     * @param key     键
     * @param values  值
     * @param timeout 过期时间（秒）
     * @return 添加数量
     */
    public long listRightPushAll(String key, List<Object> values, long timeout) {
        long count = redisTemplate.opsForList().rightPushAll(key, values);
        expire(key, timeout);
        return count;
    }

    /**
     * 获取并删除List结构中的第一个元素
     *
     * @param key 键
     * @return 删除的元素
     */
    public Object listLeftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间（秒）
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, String requestId, int expireTime) {
        String script = "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then redis.call('expire', KEYS[1], ARGV[2]) return 1 else return 0 end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), requestId, expireTime);
        return result != null && result.intValue() == 1;
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseLock(String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), requestId);
        return result != null && result.intValue() == 1;
    }
}