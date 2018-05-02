package com.dai.daobuild.core.build.render;

import java.io.IOException;

import com.dai.daobuild.core.build.entity.FileInfo;

/**
 * @author Dai
 *
 */
public class RenderServiceClass extends RenderBase {

	/**
	 * @throws IOException
	 */
	public RenderServiceClass(RenderParam param) throws IOException {
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
		return param.getFileInfo("serviceClassFile");
	}

}
