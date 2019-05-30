package com.disaster.jm.core.utils;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class FileUtil {

    /**
     * 输入流保存文件到指定目录
     * @param in    输入
     * @param descFilePath  保存目录
     * @param bufferSize    缓冲大小
     * @return
     */
    public static boolean saveFile(InputStream in,String descFilePath,int bufferSize) {
        byte[] b= new byte[bufferSize==0?1024:bufferSize];
        int len;
        FileOutputStream os=null;
        OutputStream out = null;
        try {
            File descFile = new File(descFilePath);
            if(descFile.exists()) {throw new Exception("文件已存在!");}
            os = new FileOutputStream(descFile);
            out = new BufferedOutputStream(os);
            while((len=in.read(b))!=-1) {
                out.write(b,0,len);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                out.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * base64转图片保存到指定目录
     * @param base64    base64
     * @param folder    目录 格式:最后 '/' 结尾
     * @param picName   文件名
     * @param suffix    后缀 例:png
     * @return
     * @throws IOException
     */
    public static boolean saveBase64ToPic(String base64,String folder,String picName,String suffix) throws IOException {
        byte[] b = Base64.decodeBase64(base64);
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        BufferedImage image = ImageIO.read(in);
        File file = new File(folder+picName+"."+suffix);
        ImageIO.write(image, suffix, file);
        return true;
    }
}
