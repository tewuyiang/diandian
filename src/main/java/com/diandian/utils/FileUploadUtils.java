package com.diandian.utils;

import java.util.UUID;

/**
 * 文件上传工具类
 */
public class FileUploadUtils {

    /**
     * 上传获取到的文件名称可能是路径+名称
     * 使用下面的方法将路径删除，获取到文件的真实名称
     * @param filename
     * @return
     */
    public static String getRealName(String filename){
        int index = filename.lastIndexOf('\\') + 1;
        return filename.substring(index);
    }


    /**
     * 创建一个文件的随机名（使用uuid）
     * @param filename
     * @return
     */
    public static String getUuidName(String filename) {
        int index = filename.lastIndexOf('.');
        // 判断文件是否有后缀名
        if (index > -1) {
            return UUID.randomUUID().toString() + filename.substring(index);
        }
        return UUID.randomUUID().toString();
    }


    /**
     * 获取文件应该存储的hash路径
     * @param fileName
     * @return
     */
    public static String getRandomDirectory(String fileName){
        // 获取hashcode
        int hashcode = fileName.hashCode();

        // 记录路径
        String path = "";
        // 计算hashcode的位数
        int count = 0;
        while (hashcode != 0) {
            // 获取hashcode的末四位（表示一个16进制数，也就是一个目录名称）
            int temp = hashcode & 0xf;
            // 将其拼入路径中
            path += Integer.toHexString(temp) + "/";
            // 移除已经获取的末尾16进制，也就是4位二进制
            // 使用>>>符号，防止hashcode是负数而导致的问题
            hashcode = hashcode >>> 4;
            // 计算位数
            count++;
        }
        // 不足8位的补零
        for (int i = 8 - count; i > 0; --i) {
            path += "0/";
        }

        // 上面的方法产生的目录是倒的，所以颠倒过来
        return new StringBuffer(path).reverse().toString();
    }

    public static void main(String[] args) {
        String path = getRandomDirectory("是.txt");
        System.out.println(path);
    }

}
