package com.mhdt.analyse;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 需要依赖freemarker
 * @author LazyToShow
 *
 */
public class FreeMarker {

	Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
	StringTemplateLoader stringLoader;

	public void loadStringTemplate(String name, String template) {
		if(stringLoader==null) {
			stringLoader = new StringTemplateLoader();
			configuration.setTemplateLoader(stringLoader);
		}
		
		stringLoader.putTemplate(name, template);
	}
	
	public void loadDirectoryTemplate(File directory) throws IOException {
		configuration.setDirectoryForTemplateLoading(directory);
	}

	public String out(String templateId, Object args) {
		try {
			Template template = configuration.getTemplate(templateId, "utf-8");
			StringWriter writer = new StringWriter();
			template.process(args, writer);
			return writer.toString();
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
			return null;
		}
	}

}
