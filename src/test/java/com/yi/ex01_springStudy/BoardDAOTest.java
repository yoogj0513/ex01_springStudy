package com.yi.ex01_springStudy;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardDAOTest {
	@Autowired
	private BoardDAO dao;
	
	@Test
	public void testDAO() {
		System.out.println(dao);
	}
	
	//@Test
	public void testInsert() throws Exception {
		BoardVO vo = new BoardVO();
		vo.setTitle("게시글을 등록합니다.");
		vo.setContent("게시글의 내용입니다.");
		vo.setWriter("user00");
		dao.insert(vo);
	}
	
	//@Test
	public void testReadByNo() throws Exception {
		dao.readByNo(1);
	}
	
	//@Test
	public void testList() throws Exception {
		List<BoardVO> list = dao.list();
		for(BoardVO b : list) {
			System.out.println(b);
		}
	}
	
	//@Test
	public void testUpdate() throws Exception {
		BoardVO vo = new BoardVO();
		vo.setTitle("게시글을 수정합니다.");
		vo.setContent("수정된 게시글의 내용입니다.");
		vo.setBno(3);
		dao.update(vo);
	}
	
	//@Test
	public void testDelete() throws Exception {
		dao.delete(1);
	}
	
	//@Test
	public void testListPage() throws Exception {
		dao.listPage(2);
	}
	
	@Test
	public void testListCriteria() throws Exception {
		Criteria cri = new Criteria(); //페이지번호, 페이지당 display 게시글 갯수
		cri.setPage(2);
		cri.setPerPageNum(5);
		dao.listCrigeria(cri);
	}
}
