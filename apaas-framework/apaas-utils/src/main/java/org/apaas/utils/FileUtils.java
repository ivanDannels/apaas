package org.apaas.utils;

import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author ivan
 */
@UtilityClass
public class FileUtils {

    /**
     * 临时目录
     */
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    /**
     * 临时目录路径
     */
    private static final Path TEMP_PATH = Paths.get(TEMP_DIR);


    /**
     * 临时目录路径
     * @return 临时目录路径
     */
    public Path getTempPath() {
        return TEMP_PATH;
    }

    /**
     * 创建临时文件
     * @param prefix 文件前缀
     * @param suffix 文件后缀
     * @return 文件路径
     */
    public Path createTempFile(String prefix, String suffix) throws Exception {
        return Files.createTempFile(TEMP_PATH, prefix, suffix);
    }

    /**
     * 检查文件是否存在
     * @param string 文件路径
     * @return 文件是否存在
     */
    public boolean exists(String string) {
        return Files.exists(Paths.get(string));
    }

     /**
      * 删除文件
      * @param path 文件路径
      * @return 是否删除成功
      */
     public boolean delete(Path path) throws Exception {
         return Files.deleteIfExists(path);
     }

     /**
      * 删除文件
      * @param string 文件路径
      * @return 是否删除成功
      */
     public boolean delete(String string) throws Exception {
         return Files.deleteIfExists(Paths.get(string));
     }

     /**
      * 创建目录
      * @param string 目录路径
      * @return 是否创建成功
      */
     public boolean createDir(String string) throws Exception {
         return Files.createDirectories(Paths.get(string)).isAbsolute();
     }


     /**
      * 文件转换成流
      * @param path 文件路径
      * @return 文件流
      */
     public InputStream toInputStream(Path path) throws Exception {
         return Files.newInputStream(path);
     }
}
