/**
 * 
 */
package com.dai.daobuild.test.build.main;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dai.daobuild.core.util.PropertiesUtil;
import com.dai.daobuild.core.util.StringUtil;
import com.dai.daobuild.test.build.JDBC.TableUtil;
import com.dai.daobuild.test.build.entity.DaoNames;
import com.dai.daobuild.test.build.entity.FileInfo;
import com.dai.daobuild.test.build.entity.TableInfo;
import com.dai.daobuild.test.build.render.RenderBase;
import com.dai.daobuild.test.build.render.RenderMapperClass;
import com.dai.daobuild.test.build.render.RenderMapperXML;
import com.dai.daobuild.test.build.render.RenderModelClass;
import com.dai.daobuild.test.build.render.RenderParam;
import com.dai.daobuild.test.build.render.RenderServiceClass;
import com.dai.daobuild.test.build.render.RenderServiceImplClass;

import freemarker.template.TemplateException;

/**
 * 功能说明:Dao层创建类
 * 
 * @title
 *
 * @author Dai
 * 
 * @Date 2016年12月3日 下午11:42:16
 *
 *
 */
public class DaoBuild {
	private PropertiesUtil properties;

	private String srcPath;

	private String modelClassPackageName;
	private String modelClassPath;
	private String modelClassTemplatePath;

	private String mapperClassPackageName;
	private String mapperClassPath;
	private String mapperClassTemplatePath;

	private String mapperXMLPackageName;
	private String mapperXMLPath;
	private String mapperXMLTemplatePath;

	private String serviceClassPackageName;
	private String serviceClassPath;
	private String serviceClassTemplatePath;

	private String serviceImplClassPackageName;
	private String serviceImplClassPath;
	private String serviceImplClassTemplatePath;

	private FileInfo modelClassFile;
	private FileInfo mapperClassFile;
	private FileInfo mapperXMLFile;
	private FileInfo serviceClassFile;
	private FileInfo serviceImplClassFile;

	private RenderParam rp = new RenderParam();
	private TableInfo table;

	private boolean overwrite = true;

	private List<RenderBase> buildList = new ArrayList<>();

	public DaoBuild(String tableName)
			throws ClassNotFoundException, SQLException, IOException {
		if (properties == null) {
			properties = new PropertiesUtil("daoBuildConfig");
		}
		this.srcPath = System.getProperty("user.dir") + File.separator
				+ properties.getPropertyByName("target.project");

		this.modelClassPackageName = properties
				.getPropertyByName("model.package");
		this.modelClassPath = StringUtil.getPackagePath(srcPath,
				modelClassPackageName);
		this.modelClassTemplatePath = properties
				.getPropertyByName("model.template");

		this.mapperClassPackageName = properties
				.getPropertyByName("mapper.package");
		this.mapperClassPath = StringUtil.getPackagePath(srcPath,
				mapperClassPackageName);
		this.mapperClassTemplatePath = properties
				.getPropertyByName("mapper.template");

		this.mapperXMLPackageName = properties
				.getPropertyByName("mapperXML.package");
		this.mapperXMLPath = StringUtil.getPackagePath(srcPath,
				mapperXMLPackageName);
		this.mapperXMLTemplatePath = properties
				.getPropertyByName("mapperXML.template");

		this.serviceClassPackageName = properties
				.getPropertyByName("service.package");
		this.serviceClassPath = StringUtil.getPackagePath(srcPath,
				serviceClassPackageName);
		this.serviceClassTemplatePath = properties
				.getPropertyByName("service.template");

		this.serviceImplClassPackageName = properties
				.getPropertyByName("serviceImpl.package");
		this.serviceImplClassPath = StringUtil.getPackagePath(srcPath,
				serviceImplClassPackageName);
		this.serviceImplClassTemplatePath = properties
				.getPropertyByName("serviceImpl.template");
		TableUtil ti = TableUtil.getInstance();
		this.table = ti.getTableByName(tableName);
		DaoNames dn = new DaoNames(tableName);
		modelClassFile = new FileInfo(dn.getModelClassName(),
				dn.getModelClassFile(), modelClassPackageName, modelClassPath,
				modelClassTemplatePath);
		mapperClassFile = new FileInfo(dn.getMapperClassName(),
				dn.getMapperClassFile(), mapperClassPackageName,
				mapperClassPath, mapperClassTemplatePath);
		mapperXMLFile = new FileInfo(dn.getMapperXMLName(),
				dn.getMapperXMLFile(), mapperXMLPackageName, mapperXMLPath,
				mapperXMLTemplatePath);
		serviceClassFile = new FileInfo(dn.getServiceClassName(),
				dn.getServiceClassFile(), serviceClassPackageName,
				serviceClassPath, serviceClassTemplatePath);
		serviceImplClassFile = new FileInfo(dn.getServiceImplClassName(),
				dn.getServiceImplClassFile(), serviceImplClassPackageName,
				serviceImplClassPath, serviceImplClassTemplatePath);
		rp.setTable(table);
		rp.addFileInfo("modelClassFile", modelClassFile);
		rp.addFileInfo("mapperClassFile", mapperClassFile);
		rp.addFileInfo("mapperXMLFile", mapperXMLFile);
		rp.addFileInfo("serviceClassFile", serviceClassFile);
		rp.addFileInfo("serviceImplClassFile", serviceImplClassFile);
		buildList.add(new RenderModelClass(rp));
		buildList.add(new RenderMapperClass(rp));
		buildList.add(new RenderMapperXML(rp));
		buildList.add(new RenderServiceClass(rp));
		buildList.add(new RenderServiceImplClass(rp));
		overwrite = StringUtil
				.toBoolean(properties.getPropertyByName("build.overwrite"));
	}

	public void addFileInfo(String name, FileInfo fileInfo) {
		rp.addFileInfo(name, fileInfo);
	}

	public void addRender(RenderBase render) {
		buildList.add(render);
	}

	public void create() throws IllegalArgumentException,
			IllegalAccessException, TemplateException, IOException {
		for (RenderBase renderBase : buildList) {
			renderBase.create(overwrite);
		}
	}

	public void delete() throws IllegalArgumentException,
			IllegalAccessException, TemplateException, IOException {
		for (RenderBase renderBase : buildList) {
			renderBase.delete();
		}
	}
}
