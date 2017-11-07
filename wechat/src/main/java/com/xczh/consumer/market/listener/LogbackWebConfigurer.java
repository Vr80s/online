package com.xczh.consumer.market.listener;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import java.io.FileNotFoundException;
import java.net.URL;

class LogbackWebConfigurer {

	private static final String CONFIG_LOCATION_PARAM = "logbackConfigLocation";

	private static final String DEFAULT_CONFIG_FILE = "/WEB-INF/conf/logback.xml";

	private static LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
	private static JoranConfigurator configurator = new JoranConfigurator();

	public static void initLogging(ServletContext servletContext) {
		WebUtils.setWebAppRootSystemProperty(servletContext);

		String location = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
		if (location == null) {
			location = DEFAULT_CONFIG_FILE;
		}
		try {
			if (!ResourceUtils.isUrl(location)) {
				location = SystemPropertyUtils.resolvePlaceholders(location);
				location = WebUtils.getRealPath(servletContext, location);
			}
			servletContext.log("Initializing logback from [" + location + "]");
			initLogback(location);
		} catch (FileNotFoundException ex) {
			throw new IllegalArgumentException("Invalid 'logbackConfigLocation' parameter: " + ex.getMessage());
		}
	}

	public static void shutdownLogging(ServletContext servletContext) {
		servletContext.log("Shutting down logback");
		try {
			lc.stop();
		} finally {
			WebUtils.removeWebAppRootSystemProperty(servletContext);
		}
	}

	private static void initLogback(String location) throws FileNotFoundException {
		if (location.toLowerCase().endsWith(".xml")) {
			configurator.setContext(lc);
			lc.reset();
			URL url = ResourceUtils.getURL(location);
			try {
				configurator.doConfigure(url);
			} catch (JoranException ex) {
				throw new FileNotFoundException(url.getPath());
			}
			lc.start();
		}
	}
}
