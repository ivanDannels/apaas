package org.apaas.utils;

import lombok.experimental.UtilityClass;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * 类工具类
 * @author ivan
 */
@UtilityClass
public class ClassUtils {

    /**
     * 获取指定位置的泛型参数类型
     * @param clazz 需要获取泛型类型的类
     * @param index 泛型参数位置索引（从0开始）
     * @return 指定位置的泛型参数类型，如果无法获取则返回null
     */
    public <T> Class<T> getGenericType(Class<?> clazz, int index) {
        Class<?>[] genericTypes = getAllGenericTypes(clazz);
        return index >= 0 && index < genericTypes.length ? (Class<T>) genericTypes[index] : null;
    }


    /**
     * 获取所有泛型参数类型
     * @param clazz 需要获取泛型类型的类
     * @return 泛型参数类型数组，如果没有泛型参数则返回空数组
     */
    public Class<?>[] getAllGenericTypes(Class<?> clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType parameterizedType) {
            return Arrays.stream(parameterizedType.getActualTypeArguments()).map(type -> {
                    if (type instanceof Class) {
                        return (Class<?>) type;
                    } else if (type instanceof ParameterizedType) {
                        return (Class<?>) ((ParameterizedType) type).getRawType();
                    }
                    return null;
            }).toArray(Class<?>[]::new);
        }
        return new Class<?>[0];
    }

}
