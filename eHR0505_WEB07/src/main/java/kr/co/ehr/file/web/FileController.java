package kr.co.ehr.file.web;

import static kr.co.ehr.cmn.StringUtil.UPLOAD_ROOT;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import kr.co.ehr.board.service.Board;
import kr.co.ehr.cmn.Message;
import kr.co.ehr.cmn.StringUtil;
import kr.co.ehr.file.service.FileService;
import kr.co.ehr.file.service.FileVO;
import kr.co.ehr.user.service.User;

@Controller
public class FileController {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("myProperties")
	private Properties myProperties;	
	
	@Autowired
	private FileService fileService;
	
	private final String VIEW_LIST_NM = "file/file_popup";

	@Resource(name="downloadView")
	private View download;
	
	
	public FileController() {
		LOG.debug("==================================");
		LOG.debug("=myProperties="+myProperties);
		LOG.debug("==================================");
	}
	@RequestMapping(value="file/download.do",method = RequestMethod.POST)
	public ModelAndView download(HttpServletRequest req, ModelAndView mView) {
		//----------------------------------------------------
        //			download.do
        //	file.jsp  ->  FileController.java
        //       				-download()  -> View(downloadView) 
		//		                 		 -> DownloadView.java
		//		                 		 	-renderMergedOutputModel()
		//		                 		 	-setDownloadFileName
		//		                 		 	-downloadFile
		//----------------------------------------------------
		
		
		String orgFileNm  = req.getParameter("orgFileNm");// 원본파일명
		String saveFileNm = req.getParameter("saveFileNm");// 저장파일명 
		LOG.debug("===============================");
		LOG.debug("=@Controller orgFileNm="+orgFileNm);
		LOG.debug("=@Controller saveFileNm="+saveFileNm);
		LOG.debug("===============================");		
		// File downloadFile= (File) model.get("downloadFile");
		// String orgFileNm = (String) model.get("orgFileNm");
		mView.setView(download);
		
		File  downloadFile=new File(saveFileNm);
		mView.addObject("downloadFile", downloadFile);
		mView.addObject("orgFileNm", orgFileNm);
		
		return mView;
	}	
	
	/**삭제 */
	@RequestMapping(value="file/do_retrieve.do",method = RequestMethod.POST
			,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String get_fileIdList(kr.co.ehr.file.service.File inVO) {
		LOG.debug("============================");
		LOG.debug("=inVO="+inVO);
		LOG.debug("============================");
		
		LOG.debug("==================================");
		LOG.debug("=myProperties="+myProperties);
		LOG.debug("=file.config.upload_root="+myProperties.getProperty("file.config.upload_root"));
		LOG.debug("==================================");
		
		List<kr.co.ehr.file.service.File> fileList = (List<kr.co.ehr.file.service.File>) this.fileService.get_retrieve(inVO);
		LOG.debug("============================");
		LOG.debug("=fileList="+fileList);
		LOG.debug("============================");
		Gson gson = new Gson();
		String json = gson.toJson(fileList);

		LOG.debug("============================");
		LOG.debug("=json="+json);
		LOG.debug("============================");
		
		return json;
	}
	
	
	/**삭제 */
	@RequestMapping(value="file/do_delete.do",method = RequestMethod.POST
			,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String do_delete(kr.co.ehr.file.service.File inVO) {
		LOG.debug("============================");
		LOG.debug("=inVO="+inVO);
		LOG.debug("============================");
		
		int flag = this.fileService.do_delete(inVO);
		Message  message=new Message();
		
		if(flag>0) {
			message.setMsgId(String.valueOf(flag));
			message.setMsgMsg("삭제 되었습니다.");
			
			//물리적 파일 삭제.
			File delFile=new File(inVO.getSaveFileNm());
			boolean delFlag = delFile.delete();
			LOG.debug("=파일 삭제delFlag="+delFlag);
			
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
	
	
	@RequestMapping(value="file/do_save.do",method = RequestMethod.POST
			,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String do_save(MultipartHttpServletRequest mReg,HttpSession httpSession)  {
		LOG.debug("===============================");
		LOG.debug("=@Controller do_save=");
		LOG.debug("===============================");
		//Upload파일 정보: 원본,저장,사이즈,확장자 List
		List<kr.co.ehr.file.service.File> fileList = new ArrayList<kr.co.ehr.file.service.File>();
		
//		String workDiv = StringUtil.nvl(mReg.getParameter("work_div"));
		String fileId  = StringUtil.nvl(mReg.getParameter("attrFileId"));
		//--------------------------------------------
		//-예외처리
		//--------------------------------------------
//		if(workDiv.equals("")) {
//			throw new ArithmeticException("0으로 나눌수 없습니다.");
//		}
		

		
//		LOG.debug("=@Controller workDiv="+workDiv);
		LOG.debug("=@Controller fileId="+fileId);
		
		
		//01.동적으로 UPLOAD_ROOT 디렉토리 생성
		File  fileRootDir = new File(UPLOAD_ROOT);
		if(fileRootDir.isDirectory() ==false) {  
			boolean flag = fileRootDir.mkdirs();
			LOG.debug("=@Controller flag="+flag);
		}
		
		//02.년월 디렉토리 생성:D:\\HR_FILE\2019\09
		String yyyy = StringUtil.cureDate("yyyy");
		LOG.debug("=@Controller yyyy="+yyyy);
		String mm = StringUtil.cureDate("MM");
		LOG.debug("=@Controller mm="+mm);
		String datePath = UPLOAD_ROOT+File.separator+yyyy+File.separator+mm;
		LOG.debug("=@Controller datePath="+datePath);
		
		File  fileYearMM = new File(datePath);  
		
		if(fileYearMM.isDirectory()==false) {
			boolean flag = fileYearMM.mkdirs();  
			LOG.debug("=@Controller fileYearMM flag="+flag);
		}
		int flag=0;
		//01.파일 Read      
		Iterator<String> files = mReg.getFileNames();
		while(files.hasNext()) {
			kr.co.ehr.file.service.File fileVO=new kr.co.ehr.file.service.File();
			String orgFileNm  = "";//원본파일명
			String saveFileNm = "";//저장파일명
			long   fileSize   = 0L;//파일사이즈
			String ext        = "";//확장자
			
			String uploadFileNm = files.next();//file01
			MultipartFile mFile = mReg.getFile(uploadFileNm);
			orgFileNm = mFile.getOriginalFilename();
			//file선택이 않되면 continue
			if(null==orgFileNm || orgFileNm.equals(""))continue;
			
			
			LOG.debug("=@Controller uploadFileNm="+uploadFileNm);
			LOG.debug("=@Controller orgFileNm="+orgFileNm);
			fileSize = mFile.getSize();//file size byte
			
			if(orgFileNm.indexOf(".")>-1) {
				ext = orgFileNm.substring(orgFileNm.indexOf(".")+1);
			}
			LOG.debug("=@Controller fileSize="+fileSize);
			LOG.debug("=@Controller ext="+ext);
			File orgFileCheck = new File(datePath,orgFileNm);
			
			String newFile = orgFileCheck.getAbsolutePath();
			//04.파일 rename: README -> README1~9999
			if(orgFileCheck.exists()==true) {
				newFile = StringUtil.fileRename(orgFileCheck);
			}
			//session처리 
			if(null !=httpSession) {
				User user =(User) httpSession.getAttribute("user");
				if(null !=user) {
					fileVO.setRegId(user.getU_id());
				}
			}
			
			//FileID가 없는 경우.	 
			if(fileId.equals("0") || fileId.length()!=40) {
				String yyyymmdd = StringUtil.cureDate("yyyyMMdd");
				String fileIdKey = yyyymmdd+StringUtil.getUUID();
				
				LOG.debug("=@Controller fileIdKey="+fileIdKey);
				
				fileVO.setFileId(fileIdKey);
				fileVO.setNum(1);
				//FileId Set
				fileId = fileVO.getFileId();
				
			//FileID가 있는 경우.	
			}else {
				fileVO.setFileId(fileId);
				//max num조회
				int maxNum = fileService.do_file_max_count(fileVO);
				fileVO.setNum(maxNum);
				LOG.debug("=@Controller maxNum="+maxNum);
			}
			fileVO.setOrgFileNm(orgFileNm);
			fileVO.setSaveFileNm(newFile);
			fileVO.setfSize(fileSize);
			fileVO.setExt(ext);
			
			LOG.debug("=@Controller file="+fileVO);
			fileList.add(fileVO);
			try {
				
				mFile.transferTo(new File(newFile));
				
			} catch (IllegalStateException e) {
				LOG.debug("=@IllegalStateException ="+e.getMessage());
			} catch (IOException e) {
				LOG.debug("=@IllegalStateException ="+e.getMessage());
			}
			
			flag = fileService.do_save(fileVO);
			LOG.debug("=@Controller flag="+flag);
		}
		
		
		Message  message=new Message();
		if(flag>0) {
			message.setMsgId(String.valueOf(flag));
			message.setMsgMsg(fileId);
			
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
}
