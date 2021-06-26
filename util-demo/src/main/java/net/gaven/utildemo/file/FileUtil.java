package net.gaven.utildemo.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description:文件相关操作工具
 * @author: yhj
 * @create: 2020-04-22
 **/
@Slf4j
public class FileUtil {

    // 缓存文件头信息-文件头信息
    private static final HashMap<String, String> mFileTypes = new HashMap<String, String>();

    static {
        // images
        mFileTypes.put("FFD8FF", ".jpg");
        mFileTypes.put("89504E47", ".png");
        mFileTypes.put("47494638", ".gif");
        mFileTypes.put("49492A00", ".tif");
        mFileTypes.put("424D", ".bmp");
        mFileTypes.put("41433130", ".dwg"); // CAD
        mFileTypes.put("38425053", ".psd");
        mFileTypes.put("7B5C727466", ".rtf"); // 日记本
        mFileTypes.put("3C3F786D6C", ".xml");
        mFileTypes.put("68746D6C3E", ".html");
        mFileTypes.put("D0CF11E0", ".doc");
        mFileTypes.put("255044462D312E", ".pdf");
        mFileTypes.put("504B0304", ".docx");
        mFileTypes.put("52617221", ".rar");
        mFileTypes.put("57415645", ".wav");
        mFileTypes.put("41564920", ".avi");
        mFileTypes.put("2E524D46", ".rm");
        mFileTypes.put("000001BA", ".mpg");
        mFileTypes.put("000001B3", ".mpg");
        mFileTypes.put("6D6F6F76", ".mov");
        mFileTypes.put("3026B2758E66CF11", ".asf");
        mFileTypes.put("4D546864", ".mid");
        mFileTypes.put("1F8B08", ".gz");
    }


    /**
     * 检查文件后缀合法性
     *
     * @param fileSuffix
     * @return
     */
    public static boolean checkFileSuffix(String fileSuffix) {
        return mFileTypes.containsValue(fileSuffix);
    }

    /**
     * 获取文件byte
     *
     * @param file
     * @return
     */
    public static byte[] getIFileStream(File file) throws Exception {
        byte[] buffer;
        FileInputStream fis = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            bos.close();
            buffer = bos.toByteArray();
            if (file.exists()) {
                file.delete();
            }
        } catch (FileNotFoundException e) {
            throw new Exception(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error("fis关闭流错误{}", e);
                }
            }
        }
        return buffer;
    }

    /**
     * 获取网络图片进行Base64编码
     *
     * @param url 网络图片路径
     * @return base64 编码g
     * @throws Exception
     */
    public static String getFileBase64(String url) {
        if (url == null || "".equals(url.trim())) {
            return null;
        }
        InputStream inStream = null;
        try {
            URL u = new URL(url);
            // 打开图片路径
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            // 设置请求方式为GET
            conn.setRequestMethod("GET");
            // 设置超时响应时间为30秒
            conn.setConnectTimeout(1000*30);
            // 通过输入流获取图片数据
            inStream = conn.getInputStream();
            // 读取图片字节数组
            byte[] bytes = IOUtils.toByteArray(inStream);
            // 对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
            // 返回Base64编码过的字节数组字符串
            return encoder.encode(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    log.error("inStream关闭流错误{}", e);
                }
            }
        }
        return null;
    }

    /**
     * 获取网络图片的字节数组
     *
     * @param url 网络图片路径
     * @return base64 编码g
     * @throws Exception
     */
    public static byte[] getFileBytes(String url) {
        if (url == null || "".equals(url.trim())) {
            return null;
        }
        InputStream inStream = null;
        try {
            URL u = new URL(url);
            // 打开图片路径
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            // 设置请求方式为GET
            conn.setRequestMethod("GET");
            // 设置超时响应时间为5秒
            conn.setConnectTimeout(5000);
            // 通过输入流获取图片数据
            inStream = conn.getInputStream();
            // 读取图片字节数组
            byte[] bytes = IOUtils.toByteArray(inStream);
            return bytes;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    log.error("inStream关闭流错误{}", e);
                }
            }
        }
        return null;
    }


    /**
     * 二进制流转换为base64
     *
     * @param bytes
     * @return
     */
    public static String getFileBase64(byte[] bytes) {
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(bytes);

    }


    /**
     * 二进制流转换为base64
     *
     * @param bytes
     * @return
     */
    public static String getJDKFileBase64(byte[] bytes) {
        // 对字节数组Base64编码
        Base64.Encoder encoder = Base64.getEncoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encodeToString(bytes);

    }


    /**
     * 文件流strBase64数组转 InputStream
     *
     * @param strBase64 文件流strBase64数组
     * @return
     */
    public static InputStream base64InputStream(String strBase64) throws Exception {
        String string = strBase64;
        try {
            // 解码，然后将字节转换为文件，将字符串转换为byte数组
            byte[] bytes = new BASE64Decoder().decodeBuffer(string);
            return new ByteArrayInputStream(bytes);
        } catch (IOException ioe) {
            throw new Exception(ioe);
        }
    }

    public static byte[] base64Byte(String strBase64) throws Exception{
        try {
            byte[] bytes = new BASE64Decoder().decodeBuffer(strBase64);
            return bytes;
        } catch (IOException ioe) {
            throw new Exception(ioe);
        }
    }


    public static String getTempDir() {
        StringBuilder dir = new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        try {
            dir.append(System.getProperty("user.dir")).append(File.separator)
                    .append(formatter.format(new Date())).append(File.separator);
        } catch (Exception e) {
            log.error("", e);
        }
        return dir.toString();

    }

    /**
     * 获取文件扩展名: .pdf
     *
     * @Author: yhj
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot);
            }
        }
        return filename;
    }

    /**
     * 设置File文件权限
     *
     * @param file 文件
     */
    public static void setFilePermission(File file) {
        boolean b1 = file.setReadable(true, true);
        boolean b2 = file.setWritable(false, true);
        boolean b3 = file.setExecutable(false, false);
    }

    /**
     * 设置Path文件权限
     *
     * @param path 路径
     */
    public static void setFilePermission(Path path) {
        try {
            Set<PosixFilePermission> perms = new HashSet<>();
            //add owners permission
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            //add group permissions
            perms.add(PosixFilePermission.GROUP_READ);
            //add others permissions
            perms.add(PosixFilePermission.OTHERS_READ);
            Files.setPosixFilePermissions(path, perms);
        } catch (Exception e) {
//            if (e instanceof UnsupportedOperationException) {
                log.error("文件权限设置失败!!! -->{}", path);
//            } else {
//                throw new BusinessException("文件权限设置失败!!!", e);
//            }
        }
    }

    /**
     * 代替Paths.get()方法使用，添加了设置文件权限
     *
     * @param filePath 文件路径
     * @return Path
     */
    public static Path get(String... filePath) {
        Path path = Paths.get(Arrays.toString(filePath));
        setFilePermission(path);
        return path;
    }

    /**
     * 代替Paths.get()方法使用，添加了设置文件权限
     *
     * @param filePath 文件路径
     * @return Path
     */
    public static Path get(String filePath) {
        Path path = Paths.get(filePath);
        setFilePermission(path);
        return path;
    }
}
