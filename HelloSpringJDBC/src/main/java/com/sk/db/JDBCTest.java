package com.sk.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;

public class JDBCTest {

	public static void main(String[] args) throws Exception {
		
	    GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("jdbc.xml"); 
	    /*
	    DataSource ds = (DataSource)ctx.getBean("dataSource"); 
		Connection conn = ds.getConnection(); 
		
		Statement stmt = conn.createStatement();
		String sql = "select * from member";
		
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		
		System.out.println(rs.getString("mem_name"));
		
		rs.close();
		stmt.close();
		conn.close(); 
		*/
	    
		MemberDAO dao = (MemberDAO)ctx.getBean("dao"); 
		ArrayList<Member> ml = dao.select(); 
		//System.out.println(ml.get(0).getMem_name());
		
		Member mem = new Member("abcde","leejong~","이종훈1","man","독서");
		//dao.insertTemplate(mem);
		//System.out.println(dao.isExistTemplate("hoho"));
		 
		//System.out.println(dao.getPWDTemplate("hoho"));
		
		List<Member> m0 = dao.selectAll();
		System.out.println();
	}
}
