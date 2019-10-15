package kr.co.ehr.boardAttr.web;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.google.gson.Gson;

import kr.co.ehr.board.service.Board;
import kr.co.ehr.board.service.BoardService;
import kr.co.ehr.boardAttr.service.BoardAttr;
import kr.co.ehr.boardAttr.service.BoardAttrService;
import kr.co.ehr.cmn.Message;
import kr.co.ehr.cmn.StringUtil;
import kr.co.ehr.code.service.Code;
import kr.co.ehr.code.service.CodeService;
import kr.co.ehr.file.service.FileService;
import kr.co.ehr.user.service.Search;
import kr.co.ehr.user.service.User;

@Controller
public class BoardAttrController {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private BoardAttrService service;
	
	@Autowired
	private CodeService codeService;
	
	@Resource(name="downloadView")
	private View  download;
	
	
	private final String VIEW_LIST_NM = "board_attr/board_attr_list";
	private final String VIEW_MNG_NM = "board_attr/board_attr_mng";
	private final String VIEW_MNG_REG_NM = "board_attr/board_attr_reg";
	
	//http://localhost:8080/ehr/file/uploadfileview.do
	@RequestMapping(value="board_attr/board_attr_mng.do")
	public String boardAttrMngView() {
		LOG.debug("===============================");
		LOG.debug("=@Controller uploadFileView=");
		LOG.debug("===============================");
		return VIEW_MNG_NM;
	}

	//http://localhost:8080/ehr/file/uploadfileview.do
	@RequestMapping(value="board_attr/board_attr_reg.do")
	public String boardAttrRegView() {
		LOG.debug("===============================");
		LOG.debug("=@Controller uploadFileView=");
		LOG.debug("===============================");
		return VIEW_MNG_REG_NM;
	}
	
	@RequestMapping(value="board_attr/do_fileIdNullUpdate.do",method = RequestMethod.POST
			,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String do_fileIdNullUpdate(BoardAttr inVO) {
		//if(파일이 모두 삭제==true) FILE_ID를 null업데이트 
		String gsonStr = "";
		LOG.debug("============================");
		LOG.debug("=inVO="+inVO);
		LOG.debug("============================");		
		if(null == inVO.getBoardId() || "".equals(inVO.getBoardId().trim())) {
			throw new IllegalArgumentException("ID를 입력 하세요.");
		}
		
		int flag = service.do_fileIdNullUpdate(inVO);
		Message  message=new Message();
		
		if(flag>0) {
			message.setMsgId(String.valueOf(flag));
			message.setMsgMsg("수정 되었습니다.");
		}else {
			message.setMsgId(String.valueOf(flag));
			message.setMsgMsg("수정 실패.");			
		}
		
		Gson gson=new Gson();
		gsonStr = gson.toJson(message);		
		
		
		return gsonStr;
	}
	
	/**엑셀다운 */
	@RequestMapping(value="board_attr/do_exceldown.do",method = RequestMethod.GET)	
	public ModelAndView excelDown(Search search,HttpServletRequest req,ModelAndView mView) {
		//param
		if(search.getPageSize()==0) {
			search.setPageSize(10);
		}
		
		if(search.getPageNum()==0) {
			search.setPageNum(1);
		}		
		
		search.setSearchDiv(StringUtil.nvl(search.getSearchDiv()));
		search.setSearchWord(StringUtil.nvl(search.getSearchWord()));
		//확장자
		String ext = (StringUtil.nvl(req.getParameter("ext")));
		
		String saveFileNm = service.excelDown(search, ext);
		String orgFileNm  = "첨부게시판_"+StringUtil.cureDate("yyyyMMdd")+"."+ext;
		mView.setView(download);

		
		File  downloadFile=new File(saveFileNm);
		mView.addObject("downloadFile", downloadFile);
		mView.addObject("orgFileNm", orgFileNm);
		
		return mView;
	}
	
	/**수정 */
	@RequestMapping(value="board_attr/do_update.do",method = RequestMethod.POST
			,produces = "application/json;charset=UTF-8")
	@ResponseBody		
	public String do_update(BoardAttr inVO,HttpSession httpSession) {
		//TO_DO : 서비스에 파일 모두 삭제 CHECK 생성,
		//if(파일이 모두 삭제==true) FILE_ID를 null업데이트 
		String gsonStr = "";
		LOG.debug("============================");
		LOG.debug("=inVO="+inVO);
		LOG.debug("============================");		
		
		if(null !=httpSession) {
			User user =(User) httpSession.getAttribute("user");
			if(null !=user) {
				inVO.setRegId(user.getU_id());
			}
		}
		
		
		if(null == inVO.getBoardId() || "".equals(inVO.getBoardId().trim())) {
			throw new IllegalArgumentException("ID를 입력 하세요.");
		}
		
		if(null == inVO.getTitle() || "".equals(inVO.getTitle().trim())) {
			throw new IllegalArgumentException("제목을 입력 하세요.");
		}
		
		if(null == inVO.getContents() || "".equals(inVO.getContents().trim())) {
			throw new IllegalArgumentException("내용을 입력 하세요.");
		}		
		
		int flag = service.do_update(inVO);
		Message  message=new Message();
		
		if(flag>0) {
			message.setMsgId(String.valueOf(flag));
			message.setMsgMsg("수정 되었습니다.");
		}else {
			message.setMsgId(String.valueOf(flag));
			message.setMsgMsg("수정 실패.");			
		}
		
		Gson gson=new Gson();
		gsonStr = gson.toJson(message);		
		
		
		return gsonStr;
		
	}
	
	
	/**삭제 */
	@RequestMapping(value="board_attr/do_delete.do",method = RequestMethod.POST
			,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String do_delete(BoardAttr inVO) {
		LOG.debug("============================");
		LOG.debug("=inVO="+inVO);
		LOG.debug("============================");
		
		//FILE_ID가 있는 경우 파일테이블 삭제 추가 
		//flag > 1 삭제 성공.
		//flag ==1 && FILE_ID==0 삭제 성공
		int flag = this.service.do_delete(inVO);
		LOG.debug("============================");
		LOG.debug("=flag="+flag);
		LOG.debug("============================");		
		Message  message=new Message();
		
		if( flag ==1) {
			
			//FILE_MNG 테이블/물리적 파일 삭제.
			if(inVO.getFileId().length()==40) {
				kr.co.ehr.file.service.File fileVO=new kr.co.ehr.file.service.File();
				fileVO.setFileId(inVO.getFileId());
				
				//파일목록 조회
				List<kr.co.ehr.file.service.File> fileList = (List<kr.co.ehr.file.service.File>) fileService.get_retrieve(fileVO);
				
				//파일 DB 삭제
				int fileDel = fileService.do_deleteFileId(fileVO);
				LOG.debug("============================");
				LOG.debug("=fileDel="+fileDel);
				LOG.debug("============================");
				//물리적파일 삭제
				for(kr.co.ehr.file.service.File vo: fileList) {
					File delFile=new File(vo.getSaveFileNm());
					boolean  fileDelFlag =delFile.delete();
					LOG.debug("=fileDelFlag="+fileDelFlag);
				}
			}
			
			message.setMsgId(String.valueOf(flag));
			message.setMsgMsg("삭제 되었습니다.");
		}else {
			message.setMsgId(String.valueOf(flag));
			message.setMsgMsg("삭제 실패.");			
		}
		
		Gson gson=new Gson();
		String gsonStr = gson.toJson(message);
		
		LOG.debug("============================");
		LOG.debug("=gsonStr="+gsonStr);
		LOG.debug("============================");		
		
		return gsonStr;
	}	
	
	
	/**저장 */
	@RequestMapping(value="board_attr/do_save.do",method = RequestMethod.POST
			,produces = "application/json;charset=UTF-8")
	@ResponseBody	
	public String do_save(BoardAttr inVO,HttpSession httpSession) {
		LOG.debug("============================");
		LOG.debug("=inVO="+inVO);
		LOG.debug("============================");
		
		
		if(null !=httpSession) {
			User user =(User) httpSession.getAttribute("user");
			if(null !=user) {
				inVO.setRegId(user.getU_id());
			}
		}
		
		if(null == inVO.getTitle() || "".equals(inVO.getTitle().trim())) {
			throw new IllegalArgumentException("제목을 입력 하세요.");
		}
		
		if(null == inVO.getContents() || "".equals(inVO.getContents().trim())) {
			throw new IllegalArgumentException("내용을 입력 하세요.");
		}
		
		int flag = this.service.do_save(inVO);
		Message  message=new Message();
		
		if(flag>0) {
			message.setMsgId(String.valueOf(flag));
			message.setMsgMsg("등록 되었습니다.");
			
			
		}else {
			message.setMsgId(String.valueOf(flag));
			message.setMsgMsg("등록 실패.");			
		}
		
		Gson gson=new Gson();
		String gsonStr = gson.toJson(message);
		
		LOG.debug("============================");
		LOG.debug("=gsonStr="+gsonStr);
		LOG.debug("============================");				
		
		return gsonStr;
	}
	
	/**단건조회 */
	@RequestMapping(value="board_attr/do_selectOne.do",method = RequestMethod.GET)
	public String get_selectOne(BoardAttr inVO,Model model) {
		LOG.debug("============================");
		LOG.debug("=inVO="+inVO);
		LOG.debug("============================");
		
		if(null == inVO.getBoardId() || "".equals(inVO.getBoardId())) {
			throw new IllegalArgumentException("ID를 입력 하세요.");
		}
		
		BoardAttr outVO= (BoardAttr) service.get_selectOne(inVO);
		model.addAttribute("vo", outVO);
		LOG.debug("============================");
		LOG.debug("=vo="+outVO);
		LOG.debug("============================");	
//		if(null !=outVO && outVO.getFileId().length()==40) {
//			kr.co.ehr.file.service.File  file =new kr.co.ehr.file.service.File();
//			file.setFileId(outVO.getFileId());
//			LOG.debug("============================");
//			LOG.debug("=file="+file);
//			LOG.debug("============================");			
//			List<File> listFile = (List<File>) this.fileService.get_retrieve(file);
//			model.addAttribute("listFile", listFile);
//		}
		
		return VIEW_MNG_NM;
	}
	
	/**목록조회 */
	@RequestMapping(value="board_attr/get_retrieve.do",method = RequestMethod.GET)
	public String get_retrieve(HttpServletRequest req,Search search, Model model) {
		//param
		if(search.getPageSize()==0) {
			search.setPageSize(10);
		}
		
		if(search.getPageNum()==0) {
			search.setPageNum(1);
		}		
		
		search.setSearchDiv(StringUtil.nvl(search.getSearchDiv()));
		search.setSearchWord(StringUtil.nvl(search.getSearchWord()));
		model.addAttribute("vo", search);
		
		LOG.debug("============================");
		LOG.debug("=search="+search);
		LOG.debug("============================");		
		
		Code code=new Code();
		//페이지사이즈
		code.setCodeId("PAGE_SIZE");
		
		List<Code> listPageSize=(List<Code>) this.codeService.get_retrieve(code);
		model.addAttribute("listPageSize", listPageSize);
		
		//엑셀타입
		code.setCodeId("EXCEL_TYPE");
		List<Code> listExcelType=(List<Code>) this.codeService.get_retrieve(code);		
		model.addAttribute("listExcelType", listExcelType);
		
		//게시판 검색 구분
		code.setCodeId("BOARD_ATTR_SEARCH");
		List<Code> listBoardSearch=(List<Code>) this.codeService.get_retrieve(code);		
		model.addAttribute("listBoardSearch", listBoardSearch);
		
		//목록조회
		List<BoardAttr> list = (List<BoardAttr>) this.service.get_retrieve(search);
		model.addAttribute("list", list);
		
		//총건수
		int totalCnt = 0;
		if(null != list && list.size()>0) {
			totalCnt = list.get(0).getTotalCnt();
		}
		model.addAttribute("totalCnt", totalCnt);
		return VIEW_LIST_NM;
	}
	
	
}
