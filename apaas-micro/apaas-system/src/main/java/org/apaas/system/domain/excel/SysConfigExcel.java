package org.apaas.system.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
 import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.metadata.data.WriteCellData;
import lombok.Data;

/**
 * 参数配置Excel导出实体类
 */
@Data
public class SysConfigExcel {
    /**
     * 参数名称
     */
    @ExcelProperty(value = "参数名称", index = 0)
    private String name;

    /**
     * 参数键
     */
    @ExcelProperty(value = "参数键", index = 1)
    private String configKey;

    /**
     * 参数编码
     */
    @ExcelProperty(value = "参数编码", index = 2)
    private String code;

    /**
     * 参数值
     */
    @ExcelProperty(value = "参数值", index = 3)
    private String value;

    /**
     * 参数类型
     */
    @ExcelProperty(value = "参数类型", index = 4, converter = ConfigTypeConverter.class)
    private Integer type;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", index = 5, converter = StatusConverter.class)
    private Integer status;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述", index = 6)
    private String description;

    /**
     * 参数类型转换器
     */
    public static class ConfigTypeConverter implements Converter<Integer> {
        @Override
        public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) {
            Integer type = context.getValue();
            if (type == null) {
                return new WriteCellData<>("");
            }
            return new WriteCellData<>(type == 0 ? "系统参数" : "业务参数");
        }

        @Override
        public Integer convertToJavaData(ReadConverterContext<?> context) {
            String value = context.getReadCellData().getStringValue();
            if (value == null) {
                return null;
            }
            return "系统参数".equals(value) ? 0 : 1;
        }
    }

    /**
     * 状态转换器
     */
    public static class StatusConverter implements Converter<Integer> {
        @Override
        public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) {
            Integer status = context.getValue();
            if (status == null) {
                return new WriteCellData<>("");
            }
            return new WriteCellData<>(status == 0 ? "正常" : "停用");
        }

        @Override
        public Integer convertToJavaData(ReadConverterContext<?> context) {
            String value = context.getReadCellData().getStringValue();
            if (value == null) {
                return null;
            }
            return "正常".equals(value) ? 0 : 1;
        }
    }
}