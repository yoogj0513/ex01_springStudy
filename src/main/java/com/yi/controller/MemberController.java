package com.yi.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yi.domain.MemberVO;
import com.yi.service.MemberService;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String loginGet() {
		return "member/loginForm";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String loginPost(MemberVO vo, Model model, HttpSession session) throws Exception {
		System.out.println("vo : " + vo.getUserid());
		MemberVO dbVO = service.readMember(vo.getUserid());
		if(dbVO == null) {
			model.addAttribute("error", "id가 없습니다.");
			return "member/loginForm";
		} else if (dbVO.getUserpw().equals(vo.getUserpw()) == false) {
			model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
			return "member/loginForm";
		}
		
		session.setAttribute("Auth", vo.getUserid());
		return "redirect:/";
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET) //a태그로 올때는 get
	public String logout(HttpSession session) {
		session.invalidate(); //session안의 모든 key를 제거
		//session.removeAttribute("Auth"); 특정 키만 삭제
		
		return "redirect:/";
	}
}
