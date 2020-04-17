package com.yi.domain;

public class PageMaker {
	private int totalCount; // 게시글 전체 갯수
	private int startPage; // 시작번호
	private int endPage; // 끝번호
	private boolean prev; // 이전여부
	private boolean next; // 이후 여부

	private int displayPageNum = 10; // 보여지는 페이지 번호의 수
	private Criteria cri;
	
	private void calculatorData() {
		//가상 끝번호
		//현재 페이지의 끝 번호를 구함  ex)현재 선택한 페이지 번호 15 -> 15/10=Math.ceil(1.5) -> 2*10 = 20;
		endPage = (int)(Math.ceil(cri.getPage()/(double)displayPageNum) * displayPageNum);
		
		// 현재 페이지의 시작 번호를 구함 ex) 현재 선택한 페이지 번호 20 -> 20-10 + 1 = 11
		startPage = (endPage - displayPageNum) + 1;
		
		// 실제 끝번호 구함 
		// ex) 전체 번호 153 -> 153/10 = Math.ceil(15.3) = 16
		int tempEndPage = (int)(Math.ceil(totalCount / (double) cri.getPerPageNum()));
		
		// 만약, 실제 끝번호가 가장 끝번호가 클 경우, 실제 끝번호로 변경을 해줌
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		// 이전페이지 존재 여부
		prev = (startPage == 1) ? false : true;
		// 다음페이지 존재여부
		next = (endPage * cri.getPerPageNum() >= totalCount) ? false : true;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calculatorData(); //totalCount가 있어야지 끝번호가 생성이 됨
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	@Override
	public String toString() {
		return String.format(
				"PageMaker [totalCount=%s, startPage=%s, endPage=%s, prev=%s, next=%s, displayPageNum=%s, cri=%s]",
				totalCount, startPage, endPage, prev, next, displayPageNum, cri);
	}

}
