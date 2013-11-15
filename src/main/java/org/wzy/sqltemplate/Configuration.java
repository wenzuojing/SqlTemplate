package org.wzy.sqltemplate;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Wen
 * 
 */

public class Configuration {

	private Map<String, SqlTemplate> templateCache;

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
		SqlTemplate template = new SqlTemplate.SqlTemplateBuilder(this,content ).build() ;
		
		templateCache.put(content, template) ;

		return  template;
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

}
