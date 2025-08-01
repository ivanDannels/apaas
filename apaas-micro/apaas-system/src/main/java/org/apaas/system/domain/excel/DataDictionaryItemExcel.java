package org.apaas.system.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.ExcelPropertyFormatProperty;
import com.alibaba.excel.read.converter.ReadConverterContext;
import com.alibaba.excel.write.converter.WriteConverterContext;
import com.alibaba.excel.write.metadata.WriteCellData;
import lombok.Data;

/**
 * 数据字典项Excel导出实体类
 */
@Data
public class DataDictionaryItemExcel {

    @ExcelProperty("字典项编码")
    private String code;

    @ExcelProperty("字典项名称")
    private String name;

    @ExcelProperty("字典项值")
    private String value;

    @ExcelProperty("排序")
    private Integer sort;

    @ExcelProperty(value = "状态", converter = StatusConverter.class)
    private Integer status;

    @ExcelProperty("描述")
    private String description;

    /**
     * 状态转换器
     */
    public static class StatusConverter implements Converter<Integer> {

        @Override
        public Class<?> supportJavaTypeKey() {
            return Integer.class;
        }

        @Override
        public ExcelPropertyFormatProperty supportExcelTypeKey() {
            return null;
        }

        @Override
        public Integer convertToJavaData(ReadConverterContext<?> context) {
            String value = context.getReadCellData().getStringValue();
            if ("正常".equals(value)) {
                return 0;
            } else if ("停用".equals(value)) {
                return 1;
            }
            return null;
        }

        @Override
        public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) {
            Integer value = context.getValue();
            if (value == 0) {
                return new WriteCellData<>("正常");
            } else if (value == 1) {
                return new WriteCellData<>("停用");
            }
            return new WriteCellData<>("");
        }
    }
}