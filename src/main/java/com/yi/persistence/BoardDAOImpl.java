package com.yi.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;

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

	@Override
	public void viewcntUpdate(BoardVO vo) throws Exception {
		sqlSession.update(namespace + "viewcntUpdate", vo); 
	}

	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		// 1 -> 0, 2->10, 3->20
		if(page < 0) {
			page = 1;
		}
		
		page = (page -1) * 10;
		return sqlSession.selectList(namespace + "listPage", page);
	}

	@Override
	public List<BoardVO> listCrigeria(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "listCriteria", cri);
	}

	@Override
	public int totalCount() throws Exception {
		return sqlSession.selectOne(namespace + "totalCount");
	}

	@Override
	public List<BoardVO> listSearchCrigeria(SearchCriteria cri) throws Exception {
		return sqlSession.selectList(namespace + "listSearchCriteria", cri);
	}

	@Override
	public int totalSearchCount(SearchCriteria cri) throws Exception {
		return sqlSession.selectOne(namespace + "totalSearchCount", cri);
	}

}
