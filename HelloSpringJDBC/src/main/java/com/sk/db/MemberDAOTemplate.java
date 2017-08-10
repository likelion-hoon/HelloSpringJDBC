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

public class MemberDAOTemplate implements MemberDAO{

	private JdbcTemplate jdbcTemplate;
	DataSource ds;

	@Autowired
	public MemberDAOTemplate(DataSource ds) {
		this.ds = ds;
		jdbcTemplate = new JdbcTemplate(ds);
	}

	public int isExist(String id, String pwd) throws Exception {

		Connection conn = ds.getConnection();
		Statement stmt = conn.createStatement();

		String sql = "select count(*) from member where mem_id = '" + id + "' and mem_pwd ='" + pwd + "'";
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();

		int cnt = rs.getInt(1);
		rs.close();
		stmt.close();

		return cnt;
	}

	// => Spring jdbcTemplate를 이용한 간편화
	public void insert(Member m) {
		this.jdbcTemplate.update("insert into member values(?,?,?,?,?)", m.getMem_id(), m.getMem_pwd(), m.getMem_name(),
				m.getGender(), m.getHobby());
	}

	public int isExist(String mem_id) {
		return this.jdbcTemplate.queryForInt("select count(*) from member");
	}

	public String getPWDTemplate(String mem_id) {

		// 반드시 레코드 개수가 하나여야 한다.
		return this.jdbcTemplate.queryForObject("select mem_pwd from member where mem_id=?", new Object[]{mem_id},
				String.class);
	}

	public Member selectById(String mem_id) {
		Member member = this.jdbcTemplate.queryForObject("select * from member where mem_id=?", new Object[] { mem_id },
				new RowMapper<Member>() { /* 익명클래스가 들어간다(자바에서는 익명함수가 없다) */
					public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new Member(rs.getString("mem_id"),rs.getString("mem_pwd"),rs.getString("mem_name"),
									rs.getString("gender"),rs.getString("hobby"));
					}
				});
		return member; 
	}
	
	public List<Member> selectAll() {
		List<Member> members = this.jdbcTemplate.query(
				"select * from member", 
				new RowMapper<Member>() { /* 익명클래스가 들어간다(자바에서는 익명함수가 없다) */
					public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new Member(rs.getString("mem_id"),rs.getString("mem_pwd"),rs.getString("mem_name"),
									rs.getString("gender"),rs.getString("hobby"));
					}
				});
		
		return members; 
	}

	public List<Member> select() throws SQLException {

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
