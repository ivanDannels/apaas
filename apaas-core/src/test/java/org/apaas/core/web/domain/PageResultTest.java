package org.apaas.core.web.domain;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageResultTest {

    @Test
    void testBuildWithPage() {
        // 创建测试数据
        List<String> content = Arrays.asList("item1", "item2", "item3");
        PageResult<String> page = new PageImpl<>(content);
        
        // 测试build方法
        PageResult<List<String>> result = PageResult.build(page);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(3L, result.get("total"));
        assertEquals(content, result.get("rows"));
    }
}