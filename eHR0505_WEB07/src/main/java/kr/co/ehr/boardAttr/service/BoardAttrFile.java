package kr.co.ehr.boardAttr.service;

import java.util.List;

import kr.co.ehr.cmn.DTO;
import kr.co.ehr.file.service.File;

public class BoardAttrFile extends DTO {

	
	private BoardAttr boardAttr;
	
	private List<File> boardAttrFileList;
	
	public BoardAttrFile() {}

	public BoardAttrFile(BoardAttr boardAttr, List<File> boardAttrFileList) {
		super();
		this.boardAttr = boardAttr;
		this.boardAttrFileList = boardAttrFileList;
	}

	public BoardAttr getBoardAttr() {
		return boardAttr;
	}

	public void setBoardAttr(BoardAttr boardAttr) {
		this.boardAttr = boardAttr;
	}

	public List<File> getBoardAttrList() {
		return boardAttrFileList;
	}

	public void setBoardAttrList(List<File> boardAttrFileList) {
		this.boardAttrFileList = boardAttrFileList;
	}

	@Override
	public String toString() {
		return "BoardAttrFile [boardAttr=" + boardAttr + ", boardAttrList=" + boardAttrFileList + ", toString()="
				+ super.toString() + "]";
	}
	
	
}
