package org.apaas.system.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.server.reactive.ServerHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * Excel工具类
 */
public class ExcelUtils {

    /**
     * 导出Excel
     *
     * @param response 响应
     * @param data     数据列表
     * @param clazz    实体类
     * @param fileName 文件名
     * @throws IOException IO异常
     */
    public static <T> void exportExcel(ServerHttpResponse response, List<T> data, Class<T> clazz, String fileName) throws IOException {
        response.getHeaders().add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.getHeaders().add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        
        // 将数据写入响应
        EasyExcel.write(response.getBody(), clazz)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("数据")
                .doWrite(data);
    }

    /**
     * 导入Excel
     *
     * @param file  文件
     * @param clazz 实体类
     * @return 数据列表
     * @throws IOException IO异常
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> clazz) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            return EasyExcel.read(inputStream, clazz, null)
                    .sheet()
                    .doReadSync();
        }
    }
}