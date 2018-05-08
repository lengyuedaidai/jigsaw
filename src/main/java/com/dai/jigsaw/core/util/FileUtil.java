package com.dai.jigsaw.core.util;

import java.io.File;

/**
 * @author Dai
 *
 */
public class FileUtil {
	/**
	 * 
	 * 功能说明:判断目录是否存在，不存在则创建目录
	 * 
	 * @title
	 * @param f
	 * @return
	 */
	public static boolean createDirectory(File f) {
		if (!f.exists() && !f.isDirectory()) {
			return f.mkdir();
		}
		return f.isDirectory() && f.exists();
	}

	/**
	 * 
	 * 功能说明:判断目录是否存在，不存在则创建目录
	 * 
	 * @title
	 * @param path
	 * @return
	 */
	public static boolean createDirectory(String path) {
		File f = new File(path);
		return createDirectory(f);
	}
}
