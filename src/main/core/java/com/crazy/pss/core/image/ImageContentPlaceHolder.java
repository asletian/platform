package com.crazy.pss.core.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageContentPlaceHolder extends ContentPlaceHolder {

	private InputStream imgIs;

	public ImageContentPlaceHolder(int left, int top, InputStream imgIs) {
		super(left, top);
		this.imgIs = imgIs;
	}

	public InputStream getImgInputStream() {
		return imgIs;
	}

	public void setImgInputStream(InputStream imgIs) {
		this.imgIs = imgIs;
	}

	@Override
	public void drawUse(Graphics2D g) throws Exception {
		BufferedImage img = ImageIO.read(imgIs);
		//合并大小图片
		g.drawImage(img, left, top, null);
	}
}
