package com.crazy.pss.common.utils;

public interface Constants {

	// 删除标记（0：正常；1：删除）
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	
	// 显示/隐藏
	public static final String SHOW = "1";
	public static final String HIDE = "0";
	
	// 是/否
	public static final String YES = "1";
	public static final String NO = "0";
	
	/**
	 * 设置管理端访问路径（ADMIN_PATH或FRONT_PATH可允许一个为空）
	 * 1. 修改本类 ADMIN_PATH 常量
	 * 2. 修改 application.properties 中的 adminPath 属性值
	 * 3. 如果指定了 FRONT_PATH 为主页，需要修改 application.properties 文件中  web.view.index 属性值
	 */
	public static final String ADMIN_PATH = "/z";
}
