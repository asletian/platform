package com.crazy.pss.core.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class ImageTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		try {
//			InputStream is = new FileInputStream(new File("E:/photo/kaer.bmp"));
//			OutputStream os = new FileOutputStream(new File("E:/photo/kaer2.bmp"));
//			ImageUtil.getTarget(is, os);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("ssssssssssss");
		try {
			ImageUtil.cut("E:/photo/kaer.bmp", "E:/photo/kaer.bmp",0,0,174,174);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /** 
     * 将图片分成九块 
     *  
     * @param srcImageFile 
     * @throws IOException 
     */  
    public static void cut(String srcImageFile) throws IOException {  
        Image img;  
        ImageFilter cropFilter;  
        String dir = null;  
        // 读取源图像  
        BufferedImage src = ImageIO.read(new File(srcImageFile));  
        int destWidth = src.getWidth() / 3;  
        int destHeight = src.getHeight() / 3;  
        // 循环  
        for (int i = 0; i < 3; i++) {  
            for (int j = 0; j < 3; j++) {  
                // 四个参数分别为图像起点坐标和宽高  
                cropFilter = new CropImageFilter(j * destWidth, i * destHeight, destWidth, destHeight);  
                System.out.println(j * destWidth);
                System.out.println(i * destHeight);
                System.out.println(destHeight);
                System.out.println(destHeight);
                img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(src.getSource(), cropFilter));  
                BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);  
                Graphics g = tag.getGraphics();  
                g.drawImage(img, 0, 0, null); // 绘制小图  
                g.dispose();  
                // 输出为文件  
                dir = "E:/photo/cut_image_" + i + "_" + j + ".jpg";  
                File f = new File(dir);  
                ImageIO.write(tag, "JPEG", f);  
            }  
        }  
    }  

}
