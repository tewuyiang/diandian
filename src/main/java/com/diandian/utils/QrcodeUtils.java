package com.diandian.utils;

import com.swetake.util.Qrcode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class QrcodeUtils {


    /**
     * 生成二维码图片
     *
     * @param content 二维码存储的内容
     * @param version 二维码的版本信息（版本越大，二维码尺寸越大，可存储的信息越多）
     */
    public static BufferedImage createQrcode(String content, int version) throws UnsupportedEncodingException {

        // 创建一个二维码对象
        Qrcode qrcode = new Qrcode();
        // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，
        // 排错率越高可存储的信息越少，但对二维码清晰度的要求越小
        qrcode.setQrcodeErrorCorrect('M');
        // 设置存储的内容，N代表仅数字、A代表仅a-z和A-Z、B代表其他字符，包括中文
        qrcode.setQrcodeEncodeMode('B');
        // 设置二维码版本号
        qrcode.setQrcodeVersion(version);

        //固定公式：计算二维码的宽和高
        int width = 67 + 12 * (version - 1);
        int height = 67 + 12 * (version - 1);

        // 创建图片生成对象
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics = bufferedImage.createGraphics();
        // 设置背景颜色
        graphics.setBackground(Color.white);
        // 设置前图颜色
        graphics.setColor(Color.black);
        // 清除画板内容
        graphics.clearRect(0, 0, width, height);

        // 将要存储的内容转为字节数组
        byte[] bytes = content.getBytes("utf-8");

        //偏移量
        int pixoff = 2;
        /**
         * 容易踩坑的地方
         * 1.注意for循环里面的i，j的顺序，
         *   s[j][i]二维数组的j，i的顺序要与这个方法中的 gs.fillRect(j*3+pixoff,i*3+pixoff, 3, 3);
         *   顺序匹配，否则会出现解析图片是一串数字
         * 2.注意此判断if (d.length > 0 && d.length < 120)
         *   是否会引起字符串长度大于120导致生成代码不执行，二维码空白
         *   根据自己的字符串大小来设置此配置
         */
        if (bytes.length > 0 && bytes.length < 120) {
            // 将字节数组转为boolean数组，0为false，1为true
            boolean[][] b = qrcode.calQrcode(bytes);

            // 绘制二维码
            for (int i = 0, len = b.length; i < len; ++i) {
                for (int j = 0; j < len; ++j) {
                    // 注意：此处j在前，i在后
                    if (b[j][i]) {
                        // 二维码一个黑点占3个像素
                        graphics.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                    }
                }
            }
        } else {
            throw new RuntimeException("qrcode image's size error!");
        }

        // 释放资源
        graphics.dispose();
        // 刷新缓冲区
        bufferedImage.flush();
        return bufferedImage;
    }


    /**
     * 生成并保存二维码到指定路径
     *
     * @param content 内容
     * @param dir     保存路径
     */
    public static String createAndSaveQrcode(String content, File dir, String filename) throws IOException {
        BufferedImage bufferedImage = createQrcode(content, 7);

        // 文件存储的目录不存在， 则创建
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 判断是否有重复文件
        File imageFile = new File(dir, filename);
        ImageIO.write(bufferedImage, "png", imageFile);
        System.out.println("success");

        return imageFile.getPath();
    }

    public static String createAndSaveQrcode(String content, String dir, String filename) throws IOException {
        return createAndSaveQrcode(content, new File(dir), filename);
    }

}