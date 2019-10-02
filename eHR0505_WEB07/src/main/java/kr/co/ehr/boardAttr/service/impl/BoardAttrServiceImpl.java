package kr.co.ehr.boardAttr.service.impl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ehr.board.service.BoardService;
import kr.co.ehr.boardAttr.service.BoardAttr;
import kr.co.ehr.boardAttr.service.BoardAttrService;
import kr.co.ehr.cmn.DTO;
import kr.co.ehr.cmn.ExcelWriter;
import kr.co.ehr.file.service.File;
import kr.co.ehr.file.service.impl.FileDaoImpl;
import kr.co.ehr.user.service.Search;

@Service
public class BoardAttrServiceImpl implements BoardAttrService {
	private Logger  LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardAttrDaoImpl boardAttrDaoImpl;
	
	@Autowired
	private FileDaoImpl  fileDaoImpl;
	
	
	@Autowired
	private ExcelWriter excelWriter;
	
	
	@Override
	public String excelDown(Search vo, String ext) {
		//header
		List<String> headers = Arrays.asList( "게시글_순번"
											,"제목"
											,"내용"
											,"조회수"
											,"파일ID"
											,"등록자ID"
											,"등록일");	
		//data
		List<BoardAttr> list = (List<BoardAttr>) boardAttrDaoImpl.get_retrieve(vo);
		
        String saveFileNm = "";
		
		if(ext.equalsIgnoreCase("csv")) {
			saveFileNm = excelWriter.csvWriterGeneralization(list, headers);
		}else if(ext.equalsIgnoreCase("xlsx")) {
			saveFileNm = excelWriter.xlsxWriterGeneralization(list, headers);
		}else if(ext.equalsIgnoreCase("xls")) {
			saveFileNm = excelWriter.xlsWriterGeneralization(list, headers);
		}
		return saveFileNm;
	}

	@Override
	public int do_update(DTO dto) {
		
		return boardAttrDaoImpl.do_update(dto);
	}

	//Transaction처리 
	@Override
	public int do_delete(DTO dto) {
		
		return boardAttrDaoImpl.do_delete(dto);
	}

	@Override
	public int do_save(DTO dto) {
		
		return boardAttrDaoImpl.do_save(dto);
	}

	/**
	 * 단건조회 및 ReadCount증가.
	 */
	@Override
	public DTO get_selectOne(DTO dto) {
		BoardAttr  boardAttr = (BoardAttr) boardAttrDaoImpl.get_selectOne(dto);
		
		if(null != boardAttr) {
			boardAttrDaoImpl.do_updateReadCnt(dto);
		}
		
		return boardAttr;
	}

	@Override
	public List<?> get_retrieve(DTO dto) {
		return boardAttrDaoImpl.get_retrieve(dto);
	}

	@Override
	public int tx_do_delete(DTO dto) throws SQLException {
		//----------------------------------
		//board_attr 테이블 Data삭제(File_ID),
		//if(FILE_ID is not null && flag ==1)
		// FILE_MNG   테이블 Data삭제(File_ID)
		//----------------------------------
		
		BoardAttr  inVO = (BoardAttr) dto;
		int flag = boardAttrDaoImpl.do_delete(dto);
		//FILE_MNG:FILE_ID기준으로 삭제.
		if( flag == 1) {
			if( (null !=inVO.getFileId()) || !inVO.getFileId().equals("0")) {
				LOG.debug("================================");
				LOG.debug("=fileDaoImpl="+fileDaoImpl);
				File file=new File();
				file.setFileId(inVO.getFileId());
				
				LOG.debug("=file="+file);
				flag+=this.fileDaoImpl.do_deleteFileId(file);
				
				LOG.debug("=flag="+flag);
				LOG.debug("================================");
				if(inVO.getFileId().equals("1016"))throw new SQLException("KIM Tr test");
			
			}
		}
		
		
		return flag;
	}

  

}
