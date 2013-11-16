package org.wzy.sqltemplate;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Wen
 * 
 */

public class Configuration {

	private ConcurrentHashMap<String, SqlTemplate> templateCache;

	private transient boolean cacheTemplate;

	private Charset charset;

	public Configuration() {
		this(true, Charset.defaultCharset());
	}

	public Configuration(boolean cacheTemplate, Charset charset) {
		super();

		this.cacheTemplate = cacheTemplate;
		this.charset = charset;

		templateCache = new ConcurrentHashMap<String, SqlTemplate>();
	}

	public SqlTemplate getTemplate(String content) {

		if (cacheTemplate) {
			SqlTemplate sqlTemplate = templateCache.get(content);

			if (sqlTemplate != null)
				return sqlTemplate;
		}

		return createTemplate(content);

	}

	private SqlTemplate createTemplate(String content) {
		SqlTemplate template = new SqlTemplate.SqlTemplateBuilder(this, content)
				.build();
		
		templateCache.putIfAbsent(content, template) ;

		return template;
	}

	public SqlTemplate getTemplate(InputStream in) throws IOException {

		String content;
		try {
			content = readerContent(in);
		} catch (IOException e) {
			throw new IOException("Error reading template ", e);
		}

		return getTemplate(content);

	}

	public SqlTemplate getTemplate(File tplFile) throws FileNotFoundException,
			IOException {
		
		return this.getTemplate(new FileInputStream(tplFile));
	}

	private String readerContent(InputStream in) throws IOException {

		StringBuilder sb = new StringBuilder(in.available());

		InputStreamReader inputStreamReader = new InputStreamReader(
				new BufferedInputStream(in), charset);

		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String line;

		while ((line = bufferedReader.readLine()) != null) {
			sb.append(line);
		}

		bufferedReader.close();

		return sb.toString();
	}

	public boolean isCacheTemplate() {
		return cacheTemplate;
	}

	public void setCacheTemplate(boolean cacheTemplate) {
		this.cacheTemplate = cacheTemplate;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

}
