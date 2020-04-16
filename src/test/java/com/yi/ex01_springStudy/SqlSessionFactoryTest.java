package com.yi.ex01_springStudy;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})// 컨테이너 만듬
public class SqlSessionFactoryTest {
	
	@Autowired
	private SqlSessionFactory sqlFactory;
	
	@Test
	public void testSqlSessionFactory() {
		System.out.println(sqlFactory);
	}
}
