package com.crazy.pss.core.image;

import java.awt.Graphics2D;

public abstract class ContentPlaceHolder {

	/**
	 * X坐标
	 */
	protected int left;

	/**
	 * Y坐标
	 */
	protected int top;

	public ContentPlaceHolder(int left, int top) {
		super();
		this.left = left;
		this.top = top;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public abstract void drawUse(Graphics2D g) throws Exception;
}
