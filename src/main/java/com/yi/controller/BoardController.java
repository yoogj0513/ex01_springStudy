package com.yi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.PageMaker;
import com.yi.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	BoardService service;
	
	@RequestMapping(value = "/board/register", method = RequestMethod.GET) //value : url 주소
	public String registerGet() { // 등록 : register을 많이 씀
		return "/board/register"; //jsp파일 명
	}
	
	@RequestMapping(value = "/board/register", method = RequestMethod.POST)
	public String registerPost(BoardVO vo) throws Exception {
		System.out.println("register POST ---------" + vo);
		
		service.create(vo);
		
		return "redirect:/board/listPage"; //예약어 (forwrad, redirect)를 작성하면 그에 해당하는 기능이 실행
	}
	
	@RequestMapping(value = "/board/list", method = RequestMethod.GET)
	public String list(Model model) throws Exception {
		List<BoardVO> list = service.list();
		model.addAttribute("list", list);
		return "/board/list";
	}
	
	@RequestMapping(value = "/board/read", method = RequestMethod.GET)
	public String read(int bno, boolean flag, Model model) throws Exception {
		BoardVO vo = service.readByNo(bno);
		if(flag) {			
			vo.setViewcnt(vo.getViewcnt() + 1);
		}
		service.viewcntUpdate(vo);
		model.addAttribute("board", vo);
		return "/board/read";
	}
	
	@RequestMapping(value = "/board/remove", method = RequestMethod.GET)
	public String remove(int bno) throws Exception {
		service.delete(bno);
		return "redirect:/board/list";
	}
	
	@RequestMapping(value = "/board/update", method = RequestMethod.GET)
	public String updateGet(int bno, Model model) throws Exception {
		BoardVO vo = service.readByNo(bno);
		model.addAttribute("board", vo);
		return "/board/update";
	}
	
	@RequestMapping(value = "/board/update", method = RequestMethod.POST)
	public String updatePost(BoardVO vo, Model model) throws Exception {
		System.out.println("update POST ---------" + vo);
		
		service.update(vo);
		
		model.addAttribute("board", vo);
		return "redirect:/board/read?bno="+vo.getBno()+"&flag=false";
	}
	
	
	// 페이징 --------------------------------------------------------------------------
	
	@RequestMapping(value = "/board/listPage", method = RequestMethod.GET)
	public String listPage(Criteria cri, Model model) throws Exception {
		List<BoardVO> list = service.listCriteria(cri);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.totalCount()); //임시로 카운트 작성한 것
		
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", pageMaker);
		return "/board/listPage";
	}
	
	@RequestMapping(value = "/board/readPage", method = RequestMethod.GET)
	public String readPage(int bno, Criteria cri, boolean flag, Model model) throws Exception {
		BoardVO vo = service.readByNo(bno);
		if(flag) {			
			vo.setViewcnt(vo.getViewcnt() + 1);
		}
		service.viewcntUpdate(vo);
		model.addAttribute("board", vo);
		model.addAttribute("cri", cri);
		return "/board/readPage";
	}
	
	@RequestMapping(value = "/board/removePage", method = RequestMethod.GET)
	public String removePage(int bno, Criteria cri) throws Exception {
		service.delete(bno);
		return "redirect:/board/listPage?page="+cri.getPage();
	}
	
	@RequestMapping(value = "/board/updatePage", method = RequestMethod.GET)
	public String updatePageGet(int bno, Criteria cri, Model model) throws Exception {
		BoardVO vo = service.readByNo(bno);
		model.addAttribute("board", vo);
		model.addAttribute("cri", cri);
		return "/board/updatePage";
	}
	
	@RequestMapping(value = "/board/updatePage", method = RequestMethod.POST)
	public String updatePagePost(BoardVO vo, Criteria cri, Model model) throws Exception {
		System.out.println("update POST ---------" + vo);
		System.out.println("update POST ---------" + cri);
		
		service.update(vo);
		
		model.addAttribute("board", vo);
		return "redirect:/board/readPage?bno="+vo.getBno()+"&page="+cri.getPage()+"&flag=false";
	}
}
