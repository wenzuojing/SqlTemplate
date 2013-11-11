package org.wzy.sqltemplate;

import java.util.List;

/**
 * 
 * @author Wen
 *
 */
public class SqlInfo {
	
	
	private String sql  ;
	
	private List<Object> parameter ;

	public SqlInfo(String sql, List<Object> parameter) {
		super();
		this.sql = sql;
		this.parameter = parameter;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<Object> getParameter() {
		return parameter;
	}

	public void setParameter(List<Object> parameter) {
		this.parameter = parameter;
	}

	@Override
	public String toString() {
		return "SqlInfo [sql=" + sql + ", parameter=" + parameter + "]";
	}
	
	

}
