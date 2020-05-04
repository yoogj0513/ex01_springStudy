package com.yi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yi.domain.Criteria;
import com.yi.domain.PageMaker;
import com.yi.domain.ReplyVO;
import com.yi.service.ReplyService;

@RestController
@RequestMapping("/replies/*")
public class ReplyController { 
	@Autowired
	private ReplyService service;
	
	// String: text / 객체 : json으로 데이터 전송됨
	//@RequestBody : body에 실려오는 json 데이터를 class 객체에 알맞게 주입을 시킴 ({객체}). 아작스용(아작스전용)일때만 쓰임
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody ReplyVO vo) {
		System.out.println(vo);
		ResponseEntity<String> entity = null;
		
		try {
			service.insert(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST); // 400 error
		}
		
		return entity;
	}
	
	// /replies/all/4098 -> 주소줄에 필요한 번호를 실어서 보내는 것
	@RequestMapping(value = "/all/{bno}", method = RequestMethod.GET)
	public ResponseEntity<List<ReplyVO>> list(@PathVariable int bno){//@PathVariable : 주소줄에 있는 값을 가져옴, 여러개 가져올 수 있음, method get말고도고 다 됨
		ResponseEntity<List<ReplyVO>> entity = null;
		
		try {
			List<ReplyVO> list = service.list(bno);
			entity = new ResponseEntity<List<ReplyVO>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<List<ReplyVO>>(HttpStatus.BAD_REQUEST); // 400 err / null 없어 처리를 해도 됨
		}
		
		return entity;
	}
	
	// replise/2 : 주소
	@RequestMapping(value = "/{rno}", method = RequestMethod.PUT)
	public ResponseEntity<String> update(@PathVariable("rno") int rno, @RequestBody ReplyVO vo) {
		ResponseEntity<String> entity = null;
		try {
			vo.setRno(rno); // 주소줄에 넘어온 rno 번호를 넣음
			service.update(vo);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST);//400 err
		}
		return entity;
	}
	
	
	// replies/{rno} -> success/fail
	@RequestMapping(value = "/{rno}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("rno") int rno) {
		ResponseEntity<String> entity = null;
		
		try {
			service.delete(rno);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST); // 400 err
		}
		
		return entity;
	}
	
	
	// /replies/4098/1
	@RequestMapping(value = "/{bno}/{page}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listPage(@PathVariable("bno") int bno, @PathVariable("page") int page){//@PathVariable : 주소줄에 있는 값을 가져옴, 여러개 가져올 수 있음, method get말고도고 다 됨
		
		ResponseEntity<Map<String, Object>> entity = null;
		
		try {
			Criteria cri = new Criteria();
			cri.setPage(page);
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.totalCount(bno));
			
			List<ReplyVO> list = service.listPage(bno, cri);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("pageMaker", pageMaker);
			
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST); // 400 err / null 없어 처리를 해도 됨
		}
		
		return entity;
	}
	
	
}
