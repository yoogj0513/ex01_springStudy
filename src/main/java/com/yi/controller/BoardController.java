package com.yi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yi.domain.BoardVO;
import com.yi.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	BoardService sevice;
	
	@RequestMapping(value = "/board/register", method = RequestMethod.GET) //value : url 주소
	public String registerGet() { // 등록 : register을 많이 씀
		return "/board/register"; //jsp파일 명
	}
	
	@RequestMapping(value = "/board/register", method = RequestMethod.POST)
	public String registerPost(BoardVO vo) throws Exception {
		System.out.println("register POST ---------" + vo);
		
		sevice.create(vo);
		
		return "redirect:/board/list"; //예약어 (forwrad, redirect)를 작성하면 그에 해당하는 기능이 실행
	}
	
	@RequestMapping(value = "/board/list", method = RequestMethod.GET)
	public String list(Model model) throws Exception {
		List<BoardVO> list = sevice.list();
		model.addAttribute("list", list);
		return "/board/list";
	}
	
	@RequestMapping(value = "/board/read", method = RequestMethod.GET)
	public String read(int bno, Model model) throws Exception {
		BoardVO vo = sevice.readByNo(bno);
		model.addAttribute("board", vo);
		return "/board/read";
	}
	
	@RequestMapping(value = "/board/remove", method = RequestMethod.GET)
	public String remove(int bno) throws Exception {
		sevice.delete(bno);
		return "redirect:/board/list";
	}
}
