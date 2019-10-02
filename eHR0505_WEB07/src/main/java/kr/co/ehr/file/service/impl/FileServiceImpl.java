package kr.co.ehr.file.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ehr.cmn.DTO;
import kr.co.ehr.file.service.FileService;

@Service
public class FileServiceImpl implements FileService {

	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileDaoImpl fileDaoImpl;
	
	@Override
	public int do_delete(DTO dto) {
		
		return fileDaoImpl.do_delete(dto);
	}

	@Override
	public int do_save(DTO dto) {
		
		return fileDaoImpl.do_save(dto);
	}

	@Override
	public List<?> get_retrieve(DTO dto) {
		return fileDaoImpl.get_retrieve(dto);
	}

}
