package com.xczhihui.bxg.common.util;

import java.io.*;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件操作相关类
 */
public class FileUtil {

    private static final int BUFFER_SIZE = 1024 * 10;

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 将数据写入文件，如果目录或文件不存在就创建。
     *
     * @param filePathName 文件绝对路径
     * @param byteArray    文件内容
     */
    public static void writeToFile(String filePathName, byte[] byteArray) {
        OutputStream os = null;
        try {
            String path = extractPath(filePathName);
            mkdir(path);
            os = new BufferedOutputStream(new FileOutputStream(new File(filePathName)));
            os.write(byteArray);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    private static String extractPath(String filename) {
        int i = filename.lastIndexOf("/");
        if (i > -1) {
            return filename.substring(0, i);
        }
        return filename;
    }

    /**
     * 从输入流中读取数据。
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] readFromStream(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = is.read(b, 0, BUFFER_SIZE)) != -1) {
                baos.write(b, 0, len);
            }
            baos.flush();
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    /**
     * 读取文本文件
     *
     * @param file 文件绝对路径
     * @return
     */
    public static String readTextFile(String file) {
        byte[] ret = readFromFile(file);
        return new String(ret);
    }

    /**
     * 读取二进制文件
     *
     * @param file 文件绝对路径
     * @return
     */
    public static byte[] readFromFile(String file) {
        File f = new File(file);
        return readFromFile(f);
    }

    /**
     * 读取文件，转换为byte[]
     *
     * @param file 文件绝对路径
     * @return
     */
    public static byte[] readFromFile(File file) {
        InputStream is = null;
        byte[] ret = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            ret = readFromStream(is);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return ret;
    }

    /**
     * 删除一个文件或空目录
     *
     * @param absFilePath
     * @return
     */
    public static boolean deleteFile(String absFilePath) {
        File file = new File(absFilePath);
        boolean b = false;
        if (file.exists()) {
            b = file.delete();
        }
        return b;
    }

    /**
     * 将文件写入rootPath下，并加入日期作为子目录；文件名改为uuid加给定文件的扩展名。
     *
     * @param rootPath
     * @param content
     * @param fileName 文件扩展名
     * @return yyyy/MM/dd/HH/uuid.extname
     */
    public static String writeGenFileNameDatePath(String rootPath, byte[] content, String fileName) {
        String suffix = getExtName(fileName);
        String finalFileName = getUUIDFileName(suffix);
        return writeFileDatePath(rootPath, content, finalFileName);
    }

    /**
     * 将文件写入rootPath下，并加入日期作为子目录(yyyy/MM/dd/HH)
     *
     * @param rootPath
     * @param content
     * @param fileName
     * @return yyyy/MM/dd/HH/fileName
     */
    private static String writeFileDatePath(String rootPath, byte[] content, String fileName) {
        String path = getYearMonthDayHourPath();
        mkdir(rootPath + "/" + path);
        String pathFile = path + "/" + fileName;
        String absPath = rootPath + "/" + pathFile;
        writeToFile(absPath, content);
        return pathFile;
    }

    private static String getExtName(String fileName) {
        int s = fileName.lastIndexOf(".");
        if (s < 0) {
            return fileName;
        }
        return fileName.substring(s + 1);
    }

    private static void mkdir(String path) {
        File f = new File(path);
        if (f.exists()) {
            return;
        } else {
            f.mkdirs();
        }
    }

    /**
     * 生成年月日的路径结构yyyy/MM/dd/HH
     *
     * @return String
     */
    public static String getYearMonthDayHourPath() {
        return DateUtil.formatDate(new Date(), "yyyy/MM/dd/HH");
    }

    /**
     * @param suffix
     * @return UUID.suffix
     */
    public static String getUUIDFileName(String suffix) {
        return CodeUtil.getRandomUUID() + "." + suffix;
    }
}
