package org.wzy.sqltemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class SqlTemplateEngin {
	
	private Configuration config ;
	
	public SqlTemplateEngin(){
		this(new Configuration());
	}
	
	public SqlTemplateEngin(Configuration config){
		this.config = config ;
	}
	
	/**
	 * 设置是否缓存sqlTemplate对象
	 * 
	 * @param cacheTemplate
	 */
	public void setCacheTemplate(boolean cacheTemplate){
		this.config.setCacheTemplate(cacheTemplate);
	}
	
	/**
	 * 设置默认字符集
	 * @param charset
	 */
	public void setCharset(Charset charset){
		this.config.setCharset(charset);
	}
	
	
	/**
	 * 设置默认字符集
	 * 
	 * @param charsetName
	 */
	public void setCharset(String charsetName){
		this.config.setCharset(Charset.forName(charsetName));
	}
	
	
	/**
	 * 
	 * @param content  sql模板内容
	 * @return  模板对象
	 */
	public SqlTemplate getSqlTemplate(String content ){
		return this.config.getTemplate(content) ;
	}
	
	/**
	 * 从流中解释模板内容
	 * 
	 * @param in  
	 * @return
	 * @throws IOException
	 */
	public SqlTemplate  getSqlTemplate(InputStream in) throws IOException{
		return this.config.getTemplate(in) ;
	}
	
	
	/**
	 * 从文件中读取模板内容
	 * 
	 * @param tplFle
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public SqlTemplate  getSqlTemplate(File tplFle) throws FileNotFoundException, IOException{
		return this.config.getTemplate(tplFle) ;
	}

}
