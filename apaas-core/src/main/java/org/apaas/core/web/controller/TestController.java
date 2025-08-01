package org.apaas.core.web.controller;

import org.apaas.core.web.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试控制器
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    /**
     * 测试返回字符串
     */
    @GetMapping("/string")
    public String testString() {
        return "Hello, World!";
    }

    /**
     * 测试返回对象
     */
    @GetMapping("/object")
    public Map<String, Object> testObject() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 25);
        return map;
    }

    /**
     * 测试返回AjaxResult
     */
    @GetMapping("/ajaxResult")
    public AjaxResult testAjaxResult() {
        return success("操作成功");
    }

    /**
     * 测试返回自定义数据
     */
    @GetMapping("/data")
    public Object testData() {
        Map<String, Object> data = new HashMap<>();
        data.put("id", 1);
        data.put("name", "测试数据");
        return data;
    }
}