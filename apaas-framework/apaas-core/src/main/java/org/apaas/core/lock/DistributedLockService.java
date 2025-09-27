/*
 * Copyright (c) 2012-2025, ivan (ivan.dannels@gmail.com).
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apaas.core.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁服务
 * @author ivan
 */
@Service
public class DistributedLockService {
    
    private final RedissonClient redissonClient;
    
    public DistributedLockService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
    
    /**
     * 获取分布式锁
     * 
     * @param lockKey 锁的键名
     * @param waitTime 等待时间
     * @param leaseTime 锁的持有时间
     * @param unit 时间单位
     * @return 是否获取到锁
     */
    public Mono<Boolean> tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        return Mono.fromCallable(() -> {
            RLock lock = redissonClient.getLock(lockKey);
            return lock.tryLock(waitTime, leaseTime, unit);
        });
    }
    
    /**
     * 释放分布式锁
     * 
     * @param lockKey 锁的键名
     */
    public Mono<Void> unlock(String lockKey) {
        return Mono.fromRunnable(() -> {
            RLock lock = redissonClient.getLock(lockKey);
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        });
    }
    
    /**
     * 获取分布式锁（阻塞式）
     * 
     * @param lockKey 锁的键名
     */
    public Mono<Void> lock(String lockKey) {
        return Mono.fromRunnable(() -> {
            RLock lock = redissonClient.getLock(lockKey);
            lock.lock();
        });
    }
    
    /**
     * 获取分布式锁（带超时时间的阻塞式）
     * 
     * @param lockKey 锁的键名
     * @param leaseTime 锁的持有时间
     * @param unit 时间单位
     */
    public Mono<Void> lock(String lockKey, long leaseTime, TimeUnit unit) {
        return Mono.fromRunnable(() -> {
            RLock lock = redissonClient.getLock(lockKey);
            lock.lock(leaseTime, unit);
        });
    }
}