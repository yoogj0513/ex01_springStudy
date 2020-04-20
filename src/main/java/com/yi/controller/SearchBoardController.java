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
import com.yi.domain.SearchCriteria;
import com.yi.service.BoardService;

@Controller
@RequestMapping("/sboard/*") // command에 항상 /sboard/로 시작한다.
public class SearchBoardController {
	@Autowired
	BoardService service;
	
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public String listPage(SearchCriteria cri, Model model) throws Exception {
		System.out.println("cri------------ "+cri);
		List<BoardVO> list = service.listSearchCriteria(cri);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.totalSearchCount(cri)); //임시로 카운트 작성한 것
		
		model.addAttribute("cri", cri);
		model.addAttribute("list", list);
		model.addAttribute("pageMaker", pageMaker);
		return "/sboard/listPage";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET) //value : url 주소
	public String registerGet() { // 등록 : register을 많이 씀
		return "/sboard/register"; //jsp파일 명
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPost(BoardVO vo) throws Exception {
		System.out.println("register POST ---------" + vo);
		
		service.create(vo);
		
		return "redirect:/sboard/listPage"; //예약어 (forwrad, redirect)를 작성하면 그에 해당하는 기능이 실행
	}
	
	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public String readPage(int bno, SearchCriteria cri, boolean flag, Model model) throws Exception {
		BoardVO vo = service.readByNo(bno);
		if(flag) {			
			vo.setViewcnt(vo.getViewcnt() + 1);
		}
		service.viewcntUpdate(vo);
		model.addAttribute("board", vo);
		model.addAttribute("cri", cri);
		return "/sboard/readPage";
	}
	
	@RequestMapping(value = "/removePage", method = RequestMethod.GET)
	public String removePage(int bno, SearchCriteria cri, Model model) throws Exception {
		service.delete(bno);
		
		// 매개변수 값이 한글일 때는 브라우저를 밖에서 처리된 값이 들어와 깨진 값이 들어오기 때문에 model로 전달하면 한글로 전달됨
		model.addAttribute("page", cri.getPage());
		model.addAttribute("searchType", cri.getSearchType());
		model.addAttribute("keyword", cri.getKeyword());
		return "redirect:/sboard/listPage";
		//return "redirect:/sboard/listPage?page="+cri.getPage()+"&searchType="+cri.getSearchType()+"&keyword="+cri.getKeyword();
	}
	
	@RequestMapping(value = "/updatePage", method = RequestMethod.GET)
	public String updatePageGet(int bno, SearchCriteria cri, Model model) throws Exception {
		BoardVO vo = service.readByNo(bno);
		model.addAttribute("board", vo);
		model.addAttribute("cri", cri);
		return "/sboard/updatePage";
	}
	
	@RequestMapping(value = "/updatePage", method = RequestMethod.POST)
	public String updatePagePost(BoardVO vo, SearchCriteria cri, Model model) throws Exception {
		System.out.println("update POST ---------" + vo);
		System.out.println("update POST ---------" + cri);
		
		service.update(vo);
		
		model.addAttribute("board", vo);
		model.addAttribute("page", cri.getPage());
		model.addAttribute("searchType", cri.getSearchType());
		model.addAttribute("keyword", cri.getKeyword());
		return "redirect:/sboard/readPage?bno="+vo.getBno();
	}
}
