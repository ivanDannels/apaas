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
package org.apaas.infrastructure.lock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

/**
 * DistributedLockService测试类
 * @author ivan
 */
class DistributedLockServiceTest {
    
    @Mock
    private RedissonClient redissonClient;
    
    @Mock
    private RLock rLock;
    
    private DistributedLockService distributedLockService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        distributedLockService = new DistributedLockService(redissonClient);
    }
    
    @Test
    void testTryLock() throws InterruptedException {
        // Given
        String lockKey = "testLock";
        when(redissonClient.getLock(lockKey)).thenReturn(rLock);
        when(rLock.tryLock(10L, 30L, TimeUnit.SECONDS)).thenReturn(true);
        
        // When
        Mono<Boolean> result = distributedLockService.tryLock(lockKey, 10L, 30L, TimeUnit.SECONDS);
        
        // Then
        StepVerifier.create(result).expectNext(true).verifyComplete();
        
        verify(redissonClient).getLock(lockKey);
        verify(rLock).tryLock(10L, 30L, TimeUnit.SECONDS);
    }
    
    @Test
    void testUnlock() {
        // Given
        String lockKey = "testLock";
        when(redissonClient.getLock(lockKey)).thenReturn(rLock);
        when(rLock.isHeldByCurrentThread()).thenReturn(true);
        
        // When
        Mono<Void> result = distributedLockService.unlock(lockKey);
        
        // Then
        StepVerifier.create(result).verifyComplete();
        
        verify(redissonClient).getLock(lockKey);
        verify(rLock).isHeldByCurrentThread();
        verify(rLock).unlock();
    }
    
    @Test
    void testUnlockWhenNotHeldByCurrentThread() {
        // Given
        String lockKey = "testLock";
        when(redissonClient.getLock(lockKey)).thenReturn(rLock);
        when(rLock.isHeldByCurrentThread()).thenReturn(false);
        
        // When
        Mono<Void> result = distributedLockService.unlock(lockKey);
        
        // Then
        StepVerifier.create(result).verifyComplete();
        
        verify(redissonClient).getLock(lockKey);
        verify(rLock).isHeldByCurrentThread();
        verify(rLock, never()).unlock();
    }
    
    @Test
    void testLock() {
        // Given
        String lockKey = "testLock";
        when(redissonClient.getLock(lockKey)).thenReturn(rLock);
        
        // When
        Mono<Void> result = distributedLockService.lock(lockKey);
        
        // Then
        StepVerifier.create(result).verifyComplete();
        
        verify(redissonClient).getLock(lockKey);
        verify(rLock).lock();
    }
    
    @Test
    void testLockWithLeaseTime() {
        // Given
        String lockKey = "testLock";
        when(redissonClient.getLock(lockKey)).thenReturn(rLock);
        
        // When
        Mono<Void> result = distributedLockService.lock(lockKey, 30L, TimeUnit.SECONDS);
        
        // Then
        StepVerifier.create(result).verifyComplete();
        
        verify(redissonClient).getLock(lockKey);
        verify(rLock).lock(30L, TimeUnit.SECONDS);
    }
}