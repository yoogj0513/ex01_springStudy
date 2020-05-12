package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yi.domain.MemberVO;
import com.yi.persistence.MemberDAO;

@Service
public class MemberService {
	
	@Autowired
	private MemberDAO dao;
	
	public void createMember(MemberVO vo) throws Exception {
		dao.createMember(vo);
	};
	
	public MemberVO readMember(String userid) throws Exception {
		return dao.readMember(userid);
	};
	
	public List<MemberVO> list() throws Exception {
		return dao.list();
	};
	
	public void updateMember(MemberVO vo) throws Exception {
		dao.updateMember(vo);
	};
	
	public int removeMember(String userid) throws Exception {
		return dao.removeMember(userid);
	}
}
