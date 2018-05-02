/**
 * 
 */
package com.dai.daobuild.test.build.main;

import com.dai.daobuild.core.util.PropertiesUtil;

/**
 * 功能说明:Dao层创建主入口
 * 
 * @title
 *
 * @author Dai
 * 
 * @Date 2016年12月3日 下午11:42:16
 *
 *
 */
public class DaoBuildMain {
	public static void main(String[] args) throws Exception {
		PropertiesUtil pu = new PropertiesUtil("daoBuildConfig");
		String tableName = pu.getPropertyByName("build.table");
		String type = pu.getPropertyByName("build.type");
		DaoBuild db = new DaoBuild(tableName);
		if ("create".equals(type.toLowerCase())) {
			db.create();
		} else if ("delete".equals(type.toLowerCase())) {
			db.delete();
		} else {
			System.err.println("类型错误");
		}

	}
}
