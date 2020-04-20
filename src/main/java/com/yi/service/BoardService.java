package com.yi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.SearchCriteria;
import com.yi.persistence.BoardDAO;

@Service
public class BoardService {
	@Autowired //new DAOImpl 하지 않고 주입받음. 의존적 주입
	BoardDAO dao; 
	
	public void create(BoardVO vo) throws Exception {
		dao.insert(vo);
	}
	
	public BoardVO readByNo(int bno) throws Exception {
		return dao.readByNo(bno);
	}
	
	public List<BoardVO> list() throws Exception{
		return dao.list();
	}
	
	public void update(BoardVO vo) throws Exception {
		dao.update(vo);
	}
	
	public void delete(int bno) throws Exception {
		dao.delete(bno);
	}
	
	public void viewcntUpdate(BoardVO vo) throws Exception {
		dao.viewcntUpdate(vo);
	}
	
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		return dao.listCrigeria(cri);
	}
	
	public int totalCount() throws Exception {
		return dao.totalCount();
	}
	
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception {
		return dao.listSearchCrigeria(cri);
	}
	
	public int totalSearchCount(SearchCriteria cri) throws Exception {
		return dao.totalSearchCount(cri);
	}
}
