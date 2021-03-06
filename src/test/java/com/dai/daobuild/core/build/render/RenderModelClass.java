package com.dai.daobuild.core.build.render;

import java.io.IOException;

import com.dai.daobuild.core.build.entity.FileInfo;

/**
 * @author Dai
 *
 */
public class RenderModelClass extends RenderBase {

	/**
	 * @throws IOException
	 */
	public RenderModelClass(RenderParam param) throws IOException {
		super(param);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 */
	@Override
	public FileInfo getFileInfoByParam(RenderParam param) {
		return param.getFileInfo("modelClassFile");
	}

}
