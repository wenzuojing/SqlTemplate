SqlTemplate
===========

SqlTemplate 是sql模板引擎，主要解决动态拼接sql字符串 。原理是比较简单，把模板内容构建成完成的xml，这样可以解析成相关的数据结构，
再结合Ognl强大表达式计算条件。设计上参考了MyBatis动态sql部分，为了减少学习成本，兼容Mybatis大部分用法。目前能支持以下标签：
<ul>
<li>if</li>
<li>choose (when, otherwise)</li>
<li>trim ， where, set </li>
<li>foreach</li>
</ul>

详细用法，请参考[MyBatis](http://mybatis.github.io/mybatis-3/dynamic-sql.html)

例1：动态查询

    
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
		
		
例2：动态更新
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



