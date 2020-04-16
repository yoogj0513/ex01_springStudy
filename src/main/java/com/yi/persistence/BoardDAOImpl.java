package com.yi.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yi.domain.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	private static final String namespace = "mappers.BoardMapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insert(BoardVO vo) throws Exception {
		sqlSession.insert(namespace + "insert", vo);
	}

	@Override
	public BoardVO readByNo(int bno) throws Exception {
		return sqlSession.selectOne(namespace + "readByNo", bno);
	}

	@Override
	public List<BoardVO> list() throws Exception {
		return sqlSession.selectList(namespace + "list");
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		sqlSession.update(namespace + "update", vo);
	}

	@Override
	public void delete(int bno) throws Exception {
		sqlSession.delete(namespace + "delete", bno);
	}

}
