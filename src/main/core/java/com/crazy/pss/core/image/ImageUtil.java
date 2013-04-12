package com.crazy.pss.core.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

public class ImageUtil {

	public static final int RESIZE_TYPE_FIX = 0;

	public static final int RESIZE_TYPE_PROPORTION = 1;

	public static final int RESIZE_TYPE_FIX_WIDTH = 2;

	public static final int RESIZE_TYPE_FIX_HEIGHT = 3;

	/**
	 * 除了jpeg，还支持bmp,png,jpg,gif等
	 */
	public static final String DEFAULT_FORMAT_NAME = "jpeg";

	public static final String GIF_FORMAT_NAME = "gif";

	private static final Font AUTH_FONT = new Font("Times New Roman",
			Font.PLAIN, 18);

	public static void cut(String srcImageFilePath,
			String targetImageFilePath, int startX, int startY, int destWidth, int destHeight){
		Image img;  
        ImageFilter cropFilter;  
        String dir = null;  
		try {
				BufferedImage src = ImageIO.read(new File(srcImageFilePath));
			 cropFilter = new CropImageFilter(startX, startY, destWidth, destHeight);  
             img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(src.getSource(), cropFilter));  
             BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);  
             Graphics g = tag.getGraphics();  
             g.drawImage(img, 0, 0, null); // 绘制小图  
             g.dispose();  
             // 输出为文件  
             ImageIO.write(tag, "JPEG", new File(targetImageFilePath));  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param is
	 *            输入流
	 * @param os
	 *            输出流
	 * @param resize_type
	 *            RESIZE类型(必须)
	 * @param <b>width/height</b> 宽或高(必须)，取决于RESIZE_TYPE:<br/>
	 *        RESIZE_TYPE_PROPORTION与RESIZE_TYPE_FIX_WIDTH 时为 <b>宽</b>,
	 *        RESIZE_TYPE_FIX_HEIGHT 时为 <b>高</b>
	 * @param height
	 *            高(可选),<b>RESIZE_TYPE_FIX与RESIZE_TYPE_PROPORTION时必须设置</b>
	 * @param formatName
	 *            非正式格式名称(可选，默认jpeg)
	 * @return
	 * @throws IOException
	 *             该方法会自动关闭输入、输出流。 <b>示例：</b><br/>
	 *             <code>
	 * 	compressImage(new FileInputStream("/xxx.jpg"), new FileOutStream("/yyy.jpg"), RESIZE_TYPE_FIX, 240, 320, ["jpeg"(default)|"tiff"|"..."]); <br/>
	 *  compressImage(new FileInputStream("/xxx.jpg"), new FileOutStream("/yyy.jpg"), RESIZE_TYPE_PROPORTION, 240, 320, ["jpeg"(default)|"tiff"|"..."]);
	 *  compressImage(new FileInputStream("/xxx.jpg"), new FileOutStream("/yyy.jpg"), RESIZE_TYPE_FIX_WIDTH, 240, ["jpeg"(default)|"tiff"|"..."]);
	 *  compressImage(new FileInputStream("/xxx.jpg"), new FileOutStream("/yyy.jpg"), RESIZE_TYPE_FIX_HEIGHT, 320, ["jpeg"(default)|"tiff"|"..."]);
	 * </code>
	 */
	public static boolean compressImage(InputStream is, OutputStream os,
			Object... objects) throws IOException {

		if (is == null || os == null) {
			return false;
		}

		BufferedImage image = ImageIO.read(is);
		if (image.getWidth(null) == -1) {
			return false;
		}

		int newWidth = ((Integer) objects[1]).intValue();
		int newHeight = 0;
		String formatName = DEFAULT_FORMAT_NAME;
		if (objects != null) {
			if (objects.length > 2) {
				if (objects[2] instanceof Integer)
					newHeight = ((Integer) objects[2]).intValue();
				else if (objects[2] instanceof String)
					formatName = (String) objects[2];
				if (objects.length == 4 && objects[3] instanceof String)
					formatName = (String) objects[3];
			}
		}

		switch (((Integer) objects[0])) {
		case RESIZE_TYPE_FIX:
			break;
		case RESIZE_TYPE_PROPORTION:
			int rate1 = image.getWidth(null) / newWidth;
			int rate2 = image.getHeight(null) / newHeight;
			int rate = rate1 < rate2 ? rate1 : rate2;
			newWidth = image.getWidth(null) / rate;
			newHeight = image.getHeight(null) / rate;
			break;
		case RESIZE_TYPE_FIX_HEIGHT:
			newHeight = newWidth;
			int rate3 = image.getHeight(null) / newHeight;
			newWidth = image.getWidth(null) / rate3;
			break;
		case RESIZE_TYPE_FIX_WIDTH:
			int rate4 = image.getWidth(null) / newWidth;
			newHeight = image.getHeight(null) / rate4;
			break;
		}

		BufferedImage bufferedImage = new BufferedImage(newWidth, newHeight,
				BufferedImage.TYPE_INT_RGB);
		
		bufferedImage.createGraphics()
				.drawImage(
						image.getScaledInstance(newWidth, newHeight,
								image.SCALE_SMOOTH), 0, 0, null);

		ImageIO.write(bufferedImage, formatName, os);
		is.close();
		os.close();

		return true;
	}

	/**
	 * 图片、文字合并，该方法会自动关闭输入、输出流。
	 * 
	 * @param bgIs
	 *            背景图片输入流
	 * @param mergeOs
	 *            合并输出流
	 * @param placeHolders
	 *            占位说明
	 * @param formatName
	 *            非正式格式名称(可选，默认jpeg)
	 * @return
	 * @throws Exception
	 */
	public static boolean mergeImage(InputStream bgIs, OutputStream mergeOs,
			List<ContentPlaceHolder> placeHolders, Object... objects)
			throws Exception {

		if (bgIs == null || mergeOs == null || placeHolders == null
				|| placeHolders.size() == 0)
			return false;

		// 读入背景图片
		BufferedImage bgImg = ImageIO.read(bgIs);
		// 操作开始,用大图片生成对象
		Graphics2D g = bgImg.createGraphics();
		// 执行合并
		for (ContentPlaceHolder cph : placeHolders) {
			cph.drawUse(g);
		}
		g.dispose();
		// 输出出文件
		String formatName = (objects == null || objects.length == 0) ? DEFAULT_FORMAT_NAME
				: ((String) objects[0]);
		ImageIO.write(bgImg, formatName, mergeOs);
		bgIs.close();
		mergeOs.close();
		return true;
	}

	/**
	 * 生成4位认证码图片，通常WEB项目使用，该方法自动关闭流
	 * 
	 * @param authCode
	 *            4位认证码
	 * @param os
	 *            输出流
	 * @return
	 * @throws IOException
	 */
	public static boolean genAuthImage(String authCode, OutputStream os)
			throws IOException {
		if (os == null)
			return false;

		int width = 60, height = 20;

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g = image.createGraphics();
		Random random = new Random();

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		g.setFont(AUTH_FONT);

		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		char c;
		for (int i = 0; i < authCode.length(); i++) {
			c = authCode.charAt(i);
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110))); // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(String.valueOf(c), 13 * i + 6, 16);
		}

		ImageIO.write(image, GIF_FORMAT_NAME, os);
		os.close();

		return true;
	}

	private static Color getRandColor(int fc, int bc) { // 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 通过16进制形式获取指定颜色
	 * 
	 * @param hexColor
	 * @return
	 */
	public static Color getColor(String hexColor) {
		return Color.decode("0x" + hexColor);
	}
}
