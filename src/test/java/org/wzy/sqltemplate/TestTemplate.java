package org.wzy.sqltemplate;

import java.util.ArrayList;
import java.util.HashMap;



import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TestTemplate {

	@Test
	public void testTime(){
		Configuration configuration = new Configuration();

		SqlTemplate template = configuration
			.getTemplate("select * from user where <if test='id != null ' > id  = #{id} </if> T = #{T},T1=#{util.addDays(T,15)}");

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("id", "11");
		map.put("T","20200322");
		SqlMeta process = template.process(map);
		System.out.println(process.getSql());
		System.out.println(process);
	}

	@Test
	public void testIf() {

		Configuration configuration = new Configuration();

		SqlTemplate template = configuration
				.getTemplate("select * from user where <if test='id != null ' > id  = #{id} </if> id = #{id-1}");

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("id", "11");

		SqlMeta process = template.process(map);
		System.out.println(process.getSql());
		System.out.println(process);
	}

	@Test
	public void testWhere() {

		Configuration configuration = new Configuration();

		SqlTemplate template = configuration
				.getTemplate("select * from user <where> <if test='id != null ' > and id  = #{id} </if>  <if test=' name != null' >name =#{name}</if> </where>");

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("name", "1fffdsfdf1");

		SqlMeta process = template.process(map);

		System.out.println(process);
	}

	
	@Test
	public void testSet() {

		Configuration configuration = new Configuration();

		SqlTemplate template = configuration
				.getTemplate("update user  <set> <if test='id != null '> id = #{id} ,</if><if test='name != null '> name = #{name} ,</if> </set> ");

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("id", "123");
		map.put("name", "1fffdsfdf1");

		SqlMeta process = template.process(map);

		System.out.println(process);

	}
	
	
	@Test
	public void testChoose() {

		Configuration configuration = new Configuration();

		SqlTemplate template = configuration
				.getTemplate("select  * from user <where><choose><when test=' id!= null '> and id = #{id} </when><when test=' name!= null '> and name = #{name} </when></choose> </where>");

		HashMap<String, Object> map = new HashMap<String, Object>();
		
		//map.put("id", "123");
		//map.put("name", "hhh1");

		SqlMeta process = template.process(map);

		System.out.println(process);

	}
	
	@Test
	public void testForEach() {

		Configuration configuration = new Configuration();

		SqlTemplate template = configuration
				.getTemplate("select  * from user <where> id in <foreach item=\"item\" index=\"index\" collection=\"list\"    open=\"(\" separator=\",\" close=\")\">   ${item}   ${index}  </foreach></where>") ;

		HashMap<String, Object> map = new HashMap<String, Object>();
		
		//map.put("id", "123");
		//map.put("name", "hhh1");
		
		/*ArrayList<String> arrayList = new ArrayList<String>() ;
		
		arrayList.add("1") ;
		arrayList.add("2") ;
		arrayList.add("3") ;
		arrayList.add("4") ;*/
		
		
		
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		
		map2.put("11", "11-11") ;
		map2.put("22", "22-22") ;
		
		map.put("list", map2) ;

		SqlMeta process = template.process(map);

		System.out.println(process);

	}
	
	@Test
	public void testTemplateEngin(){
		
		SqlTemplateEngin sqlTemplateEngin = new SqlTemplateEngin();
		String sqlTpl = "select * from user_info <where><if test=' username != null' > and  username = #{username} </if><if test=' email != null' > and  email = #{email} </if></where> ";
		//从字符串读取sql模板内容,还可以从单独的文件读取  
		SqlTemplate sqlTemplate = sqlTemplateEngin.getSqlTemplate(sqlTpl) ;
		
		Bindings bind = new Bindings().bind("email", "wenzuojing@gmail.com");
		
		SqlMeta sqlMeta = sqlTemplate.process(bind) ; //可传map对象或javabean对象
		
		//System.out.println(sqlMeta.getSql());
		
		Assert.assertEquals("select * from user_info  WHERE  email = ?   ", sqlMeta.getSql());
		List<Object> parameter = sqlMeta.getParameter(); //取出参数
		Assert.assertEquals(1, parameter.size());
		
		
		
		bind.bind("username", "wenzuojing") ;
		sqlMeta = sqlTemplate.process(bind) ;
		Assert.assertEquals("select * from user_info  WHERE  username = ?   and  email = ?   ", sqlMeta.getSql());
		List<Object> parameter2 = sqlMeta.getParameter();//取出参数
		Assert.assertEquals( 2  ,parameter2.size() ); 
		
	}
	
	
	@Test
	public void testTemplateEngin2(){
		
		SqlTemplateEngin sqlTemplateEngin = new SqlTemplateEngin();
		
		Map<String ,Object > userInfo = new HashMap<String,Object>() ;
		
		userInfo.put("id", "123456") ;
		userInfo.put("email", "wenzuojing@126.com") ;
		
		String sqlTpl =" update userinfo <set> <if test ='email != null '> email = #{email} </if>, <if test='age'> age = #{age} </if> </set> where id = #{id}" ;
		
		SqlTemplate sqlTemplate = sqlTemplateEngin.getSqlTemplate(sqlTpl) ;
		
		SqlMeta sqlMeta = sqlTemplate.process(userInfo) ;
		
		Assert.assertEquals(" update userinfo  SET email = ?    where id = ? ", sqlMeta.getSql());
		
		List<Object> parameter = sqlMeta.getParameter(); //取出参数
		Assert.assertEquals(2, parameter.size());
		
	}
	
	
	

}
