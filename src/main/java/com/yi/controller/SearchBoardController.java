package com.yi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yi.domain.BoardVO;
import com.yi.domain.Criteria;
import com.yi.domain.PageMaker;
import com.yi.domain.SearchCriteria;
import com.yi.service.BoardService;
import com.yi.util.UploadFileUtils;

@Controller
@RequestMapping("/sboard/*") // command에 항상 /sboard/로 시작한다.
public class SearchBoardController {
	@Autowired
	BoardService service;
	
	
	@Resource(name="uploadPath") // String은 자바에 있는 클래스이기 때문에 @Autowired로 주입하지 않고 @Resource(name="uploadPath")로 주입받음
	String uploadPath;
	
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
	public String registerPost(BoardVO vo, List<MultipartFile> imageFiles) throws Exception {
		System.out.println("register POST ---------" + vo);
		
		ArrayList<String> fullName = new ArrayList<String>();
		for(MultipartFile file : imageFiles) {
			System.out.println(file.getOriginalFilename());
			System.out.println(file.getSize());
			
			// upload 처리
			String savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
			fullName.add(savedName);
		}
		vo.setFiles(fullName);
	
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
		ArrayList<String> delFiles = service.readByNo(bno).getFiles();
		for(String d : delFiles) {
			service.deleteAttach(bno, d);
			
			// thumbnail 파일 삭제
			File file = new File(uploadPath + d);
			file.delete();
			
			// 원본 삭제
			String originlName = d.substring(0, 12) + d.substring(14);
			File originFile = new File(uploadPath + originlName);
			originFile.delete();
		}
		
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
	public String updatePagePost(String[] check, List<MultipartFile> imageFiles, BoardVO vo, SearchCriteria cri, Model model) throws Exception {
		System.out.println("update POST ---------" + vo);
		System.out.println("update POST ---------" + cri);
		System.out.println("check -----------" + check);
		System.out.println("imageFiles size ---------" + imageFiles.size());
		
		if(check != null) {			
			for(String d : check) {
				service.deleteAttach(vo.getBno(), d);
				
				// thumbnail 파일 삭제
				File file = new File(uploadPath + d);
				file.delete();
				
				// 원본 삭제
				String originlName = d.substring(0, 12) + d.substring(14);
				File originFile = new File(uploadPath + originlName);
				originFile.delete();
			}
		}
		
		ArrayList<String> fullName = new ArrayList<String>();
		for(MultipartFile file : imageFiles) {
			System.out.println("fileName -----------"+file.getOriginalFilename());
			System.out.println("fileSize -----------"+file.getSize());
			
			if(file.getSize() != 0) {					
				// upload 처리
				String savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
				fullName.add(savedName);
			}
		}
		vo.setFiles(fullName);
		
		service.update(vo);
		
		model.addAttribute("board", vo);
		model.addAttribute("page", cri.getPage());
		model.addAttribute("searchType", cri.getSearchType());
		model.addAttribute("keyword", cri.getKeyword());
		return "redirect:/sboard/readPage?bno="+vo.getBno();
	}
	
	@ResponseBody
	@RequestMapping(value = "displayFile", method = RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(String filename){
		//서버에 있는 데이터가 아닌 외부 데이터를 들고오는 것이기 때문에 서버에서 전달할 수 없음 직접 전달할 수 있도록 데이터를 까서 전달
		
		ResponseEntity<byte[]> entity = null;
		System.out.println("displayFile -----" + filename);
		
		InputStream in = null;
		try {
			in = new FileInputStream(uploadPath+"/" + filename);
			String format = filename.substring(filename.lastIndexOf(".") + 1); //확장자
			MediaType mType = null;
			if(format.equalsIgnoreCase("png")) {
				mType = MediaType.IMAGE_PNG;
			} else if(format.equalsIgnoreCase("jpg") || format.equalsIgnoreCase("jpeg")) {
				mType = MediaType.IMAGE_JPEG;
			} else if(format.equalsIgnoreCase("gif")) {
				mType = MediaType.IMAGE_GIF;
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(mType);
			
			//IOUtils.toByteArray(in) : byte while 처리 대신 함
			// 이미지 확장자 마다 디코딩하는 방법이 따로 있기 때문에 headers와 같은 처리를 해줘야 함
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
}
