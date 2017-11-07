package com.xczhihui.bxg.common.web.tool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xczhihui.bxg.common.web.annotation.PageTool;

/**
 * 动态配置静态文件的地址。 页面中用该工具处理静态文件路径。可通过
 * 
 * @author liyong
 *
 */
@PageTool
@Component("domainPathTool")
public final class DomainPathTool {

	@Value("${domainPathTool.staticFilePath:/}")
	private String staticFilePath = "/";

	@Value("${domainPathTool.imagesPath:/images/}")
	private String imagesPath = "/images/";

	@Value("${domainPathTool.jsPath:/js/}")
	private String jsPath = "/js/";

	@Value("${domainPathTool.cssPath:/css/}")
	private String cssPath = "/css/";

	public String getStaticFilePath() {
		return staticFilePath;
	}

	public void setStaticFilePath(String staticFilePath) {
		this.staticFilePath = staticFilePath;
	}

	public String getImagesPath() {
		return imagesPath;
	}

	public void setImagesPath(String imagesPath) {
		this.imagesPath = imagesPath;
	}

	public String getJsPath() {
		return jsPath;
	}

	public void setJsPath(String jsPath) {
		this.jsPath = jsPath;
	}

	public String getCssPath() {
		return cssPath;
	}

	public void setCssPath(String cssPath) {
		this.cssPath = cssPath;
	}
}
