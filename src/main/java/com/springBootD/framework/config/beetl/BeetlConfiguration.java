package com.springBootD.framework.config.beetl;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;

public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

	@Override
	public void initOther() {

		groupTemplate.registerFunctionPackage("shiro", new ShiroExt());
//		groupTemplate.registerFunctionPackage("tool", new ToolUtil());

	}

}
