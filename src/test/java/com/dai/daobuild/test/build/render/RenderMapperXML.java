package com.dai.daobuild.test.build.render;

import java.io.IOException;

import com.dai.daobuild.test.build.entity.FileInfo;

/**
 * @author Dai
 *
 */
public class RenderMapperXML extends RenderBase {

	/**
	 * @throws IOException
	 */
	public RenderMapperXML(RenderParam param) throws IOException {
		super(param);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see
	 * com.account.gms.test.build.render.RenderBase#getFileInfoByParam(com.tpt.
	 * vehicle.test.build.render.RenderParam)
	 */
	@Override
	public FileInfo getFileInfoByParam(RenderParam param) {
		return param.getFileInfo("mapperXMLFile");
	}

}
