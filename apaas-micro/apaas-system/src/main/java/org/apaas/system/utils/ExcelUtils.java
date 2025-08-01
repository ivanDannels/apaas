package org.apaas.system.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
    public static <T> void exportExcel(HttpServletResponse response, List<T> data, Class<T> clazz, String fileName) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename*=UTF-8''" + encodedFileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), clazz)
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