package com.springboot.common.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhanglong on 2017/5/22.
 */
public class ImageUtil {

    //   http://blog.csdn.net/a19881029/article/details/68925751   图片工具包

    //图片格式转换
    public static void imageFormatChange(File file, String filePath,String imageFormat) throws IOException {
        Thumbnails.of(file)
                .scale(1)
                .outputFormat(imageFormat)
                .toFile(new File(filePath));
    }

    //图片大小改变
    public static void imageSizeChange(File file, String filePath,double size) throws IOException {
        Thumbnails.of(file)
                .scale(size)
                .toFile(new File(filePath));
    }


}
