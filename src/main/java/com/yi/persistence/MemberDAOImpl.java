package com.yi.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yi.domain.MemberVO;

@Repository //<context:component-scan>에서 @Repository로 등록된 class만 bean으로 설정됨
public class MemberDAOImpl implements MemberDAO {
	@Autowired //@Inject와 @Autosired 둘 중 하나 쓰면 됨. 둘다 기능이 같음
	private SqlSession sqlSession;
	
	private static final String namespace = "mappers.MemberMapper.";

	@Override
	public void createMember(MemberVO vo) throws Exception {
		sqlSession.insert(namespace + "createMember", vo);
	}

	@Override
	public MemberVO readMember(String userid) throws Exception {
		return sqlSession.selectOne(namespace + "readMember", userid);
	}

	@Override
	public List<MemberVO> list() throws Exception {
		return sqlSession.selectList(namespace + "list");
	}

	@Override
	public void updateMember(MemberVO vo) throws Exception {
		sqlSession.update(namespace + "updateMember", vo);
	}

	@Override
	public int removeMember(String userid) throws Exception {
		return sqlSession.delete(namespace + "removeMember", userid);
	}

}
