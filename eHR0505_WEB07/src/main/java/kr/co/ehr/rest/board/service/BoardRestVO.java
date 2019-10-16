package kr.co.ehr.rest.board.service;

import kr.co.ehr.cmn.DTO;

public class BoardRestVO extends DTO {
	private String boardId	;//	게시글_순번
	private String title	;//	제목
	private int    readCnt	;//	조회수
	private String contents	;//	내용
	private String regId	;//	등록자ID
	private String regDt	;//	등록일
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getReadCnt() {
		return readCnt;
	}
	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	@Override
	public String toString() {
		return "BoardRestVO [boardId=" + boardId + ", title=" + title + ", readCnt=" + readCnt + ", contents="
				+ contents + ", regId=" + regId + ", regDt=" + regDt + ", toString()=" + super.toString() + "]";
	}
	
	
	
	
}
