package kr.co.turbosoft.core.service;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.turbosoft.core.dao.CoreRestDaoImpl;

@Service
public class CoreRestService {

	private List<Object> result;
	private List<LinkedHashMap<String, Object>> resultList;
	
	@Autowired
	private CoreRestDaoImpl dataDao;
	
	public List<LinkedHashMap<String, Object>> getLandingService() {
		
		resultList = dataDao.getLandingPage(); 
		
		return resultList;
	}

	public List<LinkedHashMap<String, Object>> getLayersService() {
		
		resultList = dataDao.getLayers(); 
		
		return resultList;
	}
	
	public List<Object> getServerInfoService() {
		
		result = dataDao.getServerInfo();
		
		return result;
	}
}
