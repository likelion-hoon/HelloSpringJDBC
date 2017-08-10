package com.sk.db;

import java.util.List;

// 인터페이스 정의 
public interface MemberDAO {
	
	public void insert(Member m) throws Exception;
	public int isExist(String mem_id,String mem_pwd) throws Exception;
	public Member selectById(String mem_id);
	public List<Member> select() throws Exception;
}