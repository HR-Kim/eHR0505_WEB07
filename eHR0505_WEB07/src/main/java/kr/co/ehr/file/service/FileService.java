package kr.co.ehr.file.service;

import java.util.List;

import kr.co.ehr.cmn.DTO;

public interface FileService {
	/** */
	public int do_deleteFileId(DTO dto);
	
	public int do_file_count(DTO dto);
	
	/**자동증가:NUM*/
	public int do_file_max_count(DTO dto);
	
	/**삭제*/
	public int do_delete(DTO dto);
	
	/**등록*/
	public int do_save(DTO dto);
	
	/**목록조회*/
	public List<?> get_retrieve(DTO dto);
}
