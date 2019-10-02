package kr.co.ehr.file.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import kr.co.ehr.file.service.FileService;

@Controller
public class FileController {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileService fileService;
	
	private final String VIEW_LIST_NM = "file/file_list";
	private final String VIEW_MNG_NM = "file/file_mng";	
	
}
