package com.sk.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MemberDAOJDBC implements MemberDAO {


	DataSource ds;
	
	@Autowired
	public MemberDAOJDBC(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void insert(Member m) throws Exception {
		
	}

	@Override
	public int isExist(String mem_id, String mem_pwd) {
		return 0;
	}
	
	public Member selectById(String mem_id) {
		return new Member("","","","","");
	}
	
	
	public List<Member> select() throws Exception {

		Connection conn = ds.getConnection();
		ArrayList<Member> ret = new ArrayList<Member>();

		Statement stmt = conn.createStatement();
		String sql = "select * from member";
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			System.out.println(rs.getString("mem_id") + "  " + rs.getString("mem_name"));

			Member m = new Member(rs.getString("mem_id"), rs.getString("mem_pwd"), rs.getString("mem_name"),
					rs.getString("gender"), rs.getString("hobby"));

			ret.add(m);
		}

		rs.close();
		stmt.close();

		return ret;
	}
}
