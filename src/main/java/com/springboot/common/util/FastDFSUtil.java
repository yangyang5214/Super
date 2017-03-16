package com.springboot.common.util;


import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;


@Component
public class FastDFSUtil {

    @Value("${image.save.position:}")
    private static String imagePosition;

    @Value("${image.url:}")
    private static String imageUrl;


    private static Logger loggerFast = LoggerFactory.getLogger(FastDFSUtil.class);

    public static String[] uploadFile(File file, String uploadFileName, long fileLength) throws IOException {
        byte[] fileBuff = getFileBuffer(new FileInputStream(file), fileLength);
        String[] files = null;
        String fileExtName = "";
        if (uploadFileName.contains(".")) {
            fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
        } else {
            loggerFast.info("Fail to upload file, because the format of filename is illegal.");
        }

        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient client = new StorageClient(trackerServer, storageServer);

        NameValuePair[] metaList = new NameValuePair[3];
        metaList[0] = new NameValuePair("fileName", uploadFileName);
        metaList[1] = new NameValuePair("fileExtName", fileExtName);
        metaList[2] = new NameValuePair("fileLength", String.valueOf(fileLength));

        try {
            files = client.upload_file(fileBuff, fileExtName, metaList);
        } catch (Exception e) {
            loggerFast.error("Upload file \"" + uploadFileName + "\"fails");
        }
        trackerServer.close();
        return files;
    }

    private static byte[] getFileBuffer(InputStream inStream, long fileLength) throws IOException {

        byte[] buffer = new byte[256 * 1024];
        byte[] fileBuffer = new byte[(int) fileLength];

        int count = 0;
        int length = 0;

        while ((length = inStream.read(buffer)) != -1) {
            for (int i = 0; i < length; ++i) {
                fileBuffer[count + i] = buffer[i];
            }
            count += length;
        }
        if (inStream != null) {
            inStream.close();
        }
        return fileBuffer;
    }

    public static void savePic(InputStream inputStream, String fileName, String path) {
        OutputStream os = null;
        try {
            byte[] bs = new byte[1024];
            int len;
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            loggerFast.error("Upload file savePic", e);
        } finally {
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 按宽度高度压缩图片文件<br> 先保存原文件，再压缩、上传
     *
     * @param oldFile 要进行压缩的文件全路径
     * @param newFile 新文件
     * @param width   宽度
     * @param height  高度
     * @param quality 质量
     * @return 返回压缩后的文件的全路径
     */
    public static String zipWidthHeightImageFile(File oldFile, File newFile, int width, int height,
                                                 float quality) {
        if (oldFile == null) {
            return null;
        }
        String newImage = null;
        try {
            /** 对服务器上的临时文件进行处理 */
            Image srcFile = ImageIO.read(oldFile);
            int w = srcFile.getWidth(null);
            //  System.out.println(w);
            int h = srcFile.getHeight(null);
            //  System.out.println(h);

            /** 宽,高设定 */
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
            //String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
            /** 压缩后的文件名 */
            //newImage = filePrex + smallIcon+ oldFile.substring(filePrex.length());

            /** 压缩之后临时存放位置 */
            FileOutputStream out = new FileOutputStream(newFile);

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
            /** 压缩质量 */
            jep.setQuality(quality, true);
            encoder.encode(tag, jep);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newImage;
    }

    /**
     * 等比例压缩算法：
     * 算法思想：根据压缩基数和压缩比来压缩原图，生产一张图片效果最接近原图的缩略图
     *
     * @param srcURL  原图地址
     * @param deskURL 缩略图地址
     * @param comBase 压缩基数
     * @param scale   压缩限制(宽/高)比例  一般用1：
     *                当scale>=1,缩略图height=comBase,width按原图宽高比例;若scale<1,缩略图width=comBase,height按原图宽高比例
     * @throws Exception
     * @author shenbin
     * @createTime 2014-12-16
     * @lastModifyTime 2014-12-16
     */
    public static void zipImageFile(String srcURL, String deskURL, double comBase,
                                    double scale) {
        File srcFile = new File(srcURL);
        Image src = null;
        try {
            src = ImageIO.read(srcFile);
            int srcHeight = src.getHeight(null);
            int srcWidth = src.getWidth(null);
            int deskHeight = 0;// 缩略图高
            int deskWidth = 0;// 缩略图宽
            double srcScale = (double) srcHeight / srcWidth;
            /**缩略图宽高算法*/
            if ((double) srcHeight > comBase || (double) srcWidth > comBase) {
                if (srcScale >= scale || 1 / srcScale > scale) {
                    if (srcScale >= scale) {
                        deskHeight = (int) comBase;
                        deskWidth = srcWidth * deskHeight / srcHeight;
                    } else {
                        deskWidth = (int) comBase;
                        deskHeight = srcHeight * deskWidth / srcWidth;
                    }
                } else {
                    if ((double) srcHeight > comBase) {
                        deskHeight = (int) comBase;
                        deskWidth = srcWidth * deskHeight / srcHeight;
                    } else {
                        deskWidth = (int) comBase;
                        deskHeight = srcHeight * deskWidth / srcWidth;
                    }
                }
            } else {
                deskHeight = srcHeight;
                deskWidth = srcWidth;
            }
            BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
            tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null); //绘制缩小后的图
            FileOutputStream deskImage = new FileOutputStream(deskURL); //输出到文件流
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
            encoder.encode(tag); //近JPEG编码
            deskImage.close();
            new File(srcURL).delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String saveImage(InputStream fileInputStream) {
        String imgId = UUID.randomUUID().toString();
        String fileName = imgId + ".png";
        String filePath = imagePosition + "/" + fileName;
        FastDFSUtil.savePic(fileInputStream, fileName, imagePosition);
        double imageSize = new File(filePath).length()/1024.0/1024.0;
        if (imageSize > 1){   //文件大于1M进行压缩
            String zipFileName = imgId + "-1.png";
            String zipFilePath = imagePosition + "/" + zipFileName;
            FastDFSUtil.zipImageFile(filePath,zipFilePath,1000,1);
            return imageUrl + zipFileName;
        }
        return imageUrl + fileName;
    }


}

