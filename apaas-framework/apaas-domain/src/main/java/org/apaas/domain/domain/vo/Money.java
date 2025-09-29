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
package org.apaas.domain.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * 金额值对象
 * 表示一个具体的金额，包含数值和货币单位
 * 值对象是不可变的，通过属性值判断相等性
 *
 * @author ivan
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Money extends BaseValueObject {
    
    /**
     * 金额数值
     */
    private final BigDecimal amount;
    
    /**
     * 货币单位
     */
    private final Currency currency;
    
    /**
     * 构造函数
     *
     * @param amount 金额数值
     * @param currency 货币单位
     */
    public Money(BigDecimal amount, Currency currency) {
        if (amount == null) {
            throw new IllegalArgumentException("金额数值不能为空");
        }
        if (currency == null) {
            throw new IllegalArgumentException("货币单位不能为空");
        }
        this.amount = amount;
        this.currency = currency;
    }
    
    /**
     * 构造函数
     *
     * @param amount 金额数值
     * @param currencyCode 货币代码
     */
    public Money(BigDecimal amount, String currencyCode) {
        this(amount, Currency.getInstance(currencyCode));
    }
    
    /**
     * 加法运算
     *
     * @param other 另一个金额对象
     * @return 计算结果
     */
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("货币单位不一致，无法进行加法运算");
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }
    
    /**
     * 减法运算
     *
     * @param other 另一个金额对象
     * @return 计算结果
     */
    public Money subtract(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("货币单位不一致，无法进行减法运算");
        }
        return new Money(this.amount.subtract(other.amount), this.currency);
    }
    
    /**
     * 乘法运算
     *
     * @param multiplier 乘数
     * @return 计算结果
     */
    public Money multiply(BigDecimal multiplier) {
        return new Money(this.amount.multiply(multiplier), this.currency);
    }
    
    /**
     * 判断金额是否大于另一个金额
     *
     * @param other 另一个金额对象
     * @return 是否大于
     */
    public boolean isGreaterThan(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("货币单位不一致，无法比较大小");
        }
        return this.amount.compareTo(other.amount) > 0;
    }
    
    /**
     * 判断金额是否小于另一个金额
     *
     * @param other 另一个金额对象
     * @return 是否小于
     */
    public boolean isLessThan(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("货币单位不一致，无法比较大小");
        }
        return this.amount.compareTo(other.amount) < 0;
    }
    
    /**
     * 判断金额是否等于另一个金额
     *
     * @param other 另一个金额对象
     * @return 是否等于
     */
    public boolean isEqualTo(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("货币单位不一致，无法比较大小");
        }
        return this.amount.compareTo(other.amount) == 0;
    }
    
    @Override
    protected boolean equalsImpl(BaseValueObject other) {
        if (!(other instanceof Money)) {
            return false;
        }
        Money that = (Money) other;
        return this.amount.equals(that.amount) && this.currency.equals(that.currency);
    }
    
    @Override
    public int hashCode() {
        return amount.hashCode() * 31 + currency.hashCode();
    }
    
    @Override
    public String toString() {
        return amount + " " + currency.getCurrencyCode();
    }
}