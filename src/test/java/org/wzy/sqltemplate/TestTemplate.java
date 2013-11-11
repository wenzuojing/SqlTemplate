package org.wzy.sqltemplate;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class TestTemplate {

	@Test
	public void testIf() {

		Configuration configuration = new Configuration();

		SqlTemplate template = configuration
				.getTemplate("select * from user where <if test='id != null ' > id  = #{id} </if>");

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("id", "11");

		SqlInfo process = template.process(map);

		System.out.println(process);
	}

	@Test
	public void testWhere() {

		Configuration configuration = new Configuration();

		SqlTemplate template = configuration
				.getTemplate("select * from user <where> <if test='id != null ' > and id  = #{id} </if>  <if test=' name != null' >name =#{name}</if> </where>");

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("name", "1fffdsfdf1");

		SqlInfo process = template.process(map);

		System.out.println(process);
	}

	
	@Test
	public void testSet() {

		Configuration configuration = new Configuration();

		SqlTemplate template = configuration
				.getTemplate("insert into user  <set> <if test='id != null '> id = #{id} ,</if><if test='name != null '> name = #{name} ,</if> </set> ");

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("id", "123");
		map.put("name", "1fffdsfdf1");

		SqlInfo process = template.process(map);

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

		SqlInfo process = template.process(map);

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

		SqlInfo process = template.process(map);

		System.out.println(process);

	}
	
	

}
