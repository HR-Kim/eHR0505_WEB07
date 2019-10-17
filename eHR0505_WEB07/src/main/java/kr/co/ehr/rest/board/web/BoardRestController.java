package kr.co.ehr.rest.board.web;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.google.gson.Gson;

import kr.co.ehr.board.service.Board;
import kr.co.ehr.board.service.BoardService;
import kr.co.ehr.cmn.DTO;
import kr.co.ehr.cmn.Message;
import kr.co.ehr.cmn.StringUtil;
import kr.co.ehr.code.service.Code;
import kr.co.ehr.code.service.CodeService;
import kr.co.ehr.rest.board.service.BoardRestVO;
import kr.co.ehr.user.service.Search;

@RestController
@RequestMapping("board_rest/*")
public class BoardRestController {

	Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	CodeService  codeService;
	
	@Resource(name="downloadView")
	private View download;
	
	
	//view
	private final String VIEW_LIST_NM ="board_rest/board_list";
	private final String VIEW_MNG_NM  ="board_rest/board_mng";
	
	/**엑셀다운 */
	@RequestMapping(value="board_rest/do_exceldown.do",method = RequestMethod.GET)	
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
		
		String saveFileNm = this.boardService.excelDown(search, ext);
		String orgFileNm  = "게시판_"+StringUtil.cureDate("yyyyMMdd")+"."+ext;
		mView.setView(download);

		
		File  downloadFile=new File(saveFileNm);
		mView.addObject("downloadFile", downloadFile);
		mView.addObject("orgFileNm", orgFileNm);
		
		return mView;
	}
	
	/**수정 */
	@RequestMapping(value="board_rest/do_update.do",method = RequestMethod.POST
			,produces = "application/json;charset=UTF-8")
	@ResponseBody		
	public String do_update(Board board) {
		String gsonStr = "";
		LOG.debug("============================");
		LOG.debug("=board="+board);
		LOG.debug("============================");		
		if(null == board.getBoardId() || "".equals(board.getBoardId().trim())) {
			throw new IllegalArgumentException("ID를 입력 하세요.");
		}
		
		if(null == board.getTitle() || "".equals(board.getTitle().trim())) {
			throw new IllegalArgumentException("제목을 입력 하세요.");
		}
		
		if(null == board.getContents() || "".equals(board.getContents().trim())) {
			throw new IllegalArgumentException("내용을 입력 하세요.");
		}		
		
		int flag = this.boardService.do_update(board);
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
	@RequestMapping(value="board_rest/do_delete.do",method = RequestMethod.POST
			,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String do_delete(Board board) {
		LOG.debug("============================");
		LOG.debug("=board="+board);
		LOG.debug("============================");
		
		int flag = this.boardService.do_delete(board);
		Message  message=new Message();
		
		if(flag>0) {
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
	@RequestMapping(value="board_rest/do_save.do",method = RequestMethod.POST
			,produces = "application/json;charset=UTF-8")
	@ResponseBody	
	public String do_save(Board board) {
		LOG.debug("============================");
		LOG.debug("=board="+board);
		LOG.debug("============================");
		
		if(null == board.getTitle() || "".equals(board.getTitle().trim())) {
			throw new IllegalArgumentException("제목을 입력 하세요.");
		}
		
		if(null == board.getContents() || "".equals(board.getContents().trim())) {
			throw new IllegalArgumentException("내용을 입력 하세요.");
		}
		
		int flag = this.boardService.do_save(board);
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
	//http://localhost:8080/ehr/board_rest/do_selectOne.do/1641
	@RequestMapping(value="/{boardId}",method = RequestMethod.PUT)
	public Board get_selectOne(@PathVariable("boardId") String boardId) {
		LOG.debug("============================");
		LOG.debug("=boardId="+boardId);
		LOG.debug("============================");
		Board board=new Board();
		
		
		if(null == boardId || "".equals(boardId)) {
			throw new IllegalArgumentException("ID를 입력 하세요.");
		}
		board.setBoardId(boardId);
		
		Board outVO= (Board) this.boardService.get_selectOne(board);
		
		
		return outVO;
	}
	
	
	/**목록조회 */
	//http://localhost:8080/ehr/board_rest/get_retrieve.do?searchDiv=10&searchWord=1&pageSize=10&pageNum=2
	@RequestMapping(value="/get_retrieve.do",method = RequestMethod.GET)
	public List<Board> get_retrieve(HttpServletRequest req,Search search, Model model) {
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

		//목록조회
		List<Board> list = (List<Board>) this.boardService.get_retrieve(search);
		model.addAttribute("list", list);
		

		return list;
	}
}







