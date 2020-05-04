package com.yi.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yi.domain.Criteria;
import com.yi.domain.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO {
	
	private static final String namespaces = "mappers.ReplyMapper.";
	
	@Autowired
	public SqlSession sqlSession;

	@Override
	public void insert(ReplyVO vo) throws Exception {
		sqlSession.insert(namespaces + "insert", vo);
	}

	@Override
	public List<ReplyVO> list(int bno) throws Exception {
		return sqlSession.selectList(namespaces + "list", bno);
	}

	@Override
	public void update(ReplyVO vo) throws Exception {
		sqlSession.update(namespaces + "update", vo);
	}

	@Override
	public void delete(int rno) throws Exception {
		sqlSession.delete(namespaces + "delete", rno);
	}

	@Override
	public List<ReplyVO> listPage(int bno, Criteria cri) throws Exception {
		//매개변수가 2개이상일 때는 map으로 보내야 함
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bno", bno);
		map.put("cri", cri);
		return sqlSession.selectList(namespaces + "listPage", map);
	}

	@Override
	public int totalCount(int bno) throws Exception {
		return sqlSession.selectOne(namespaces + "totalCount", bno);
	}

	@Override
	public ReplyVO selectByRno(int rno) throws Exception {
		return sqlSession.selectOne(namespaces + "selectByRno", rno);
	}

}
