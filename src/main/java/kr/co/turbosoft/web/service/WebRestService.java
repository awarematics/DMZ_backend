package kr.co.turbosoft.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.turbosoft.web.dao.WebRestDaoImpl;

@Service
public class WebRestService {

	@Autowired
	private WebRestDaoImpl dataDao;
	
	public List<LinkedHashMap<String, Object>> getLandingService() {
		
		List<LinkedHashMap<String, Object>> result = dataDao.getLandingPage(); 
		
		return result;
	}
	
	public List<Object> getConformance() {
		
		List<Object> result = new ArrayList<Object> ();
		
		result.add("http://www.opengis.net/spec/wfs-1/3.0/req/core");
		result.add("http://www.opengis.net/spec/wfs-1/3.0/req/oas30");
		result.add("http://www.opengis.net/spec/wfs-1/3.0/req/html");
		result.add("http://www.opengis.net/spec/wfs-1/3.0/req/geojson");
	
		return result;
	}
	
	public List<Object> getCapabilities() {
		
		List<Object> result = new ArrayList<Object> ();
		
		return result;
	}
	
	public int putMFCollection(Map<String, Object> obj) {
		
		int pa = dataDao.putMFCollection(obj);
		
		return pa;
	}
	
	public List<LinkedHashMap<String, Object>> getMFCollections() {
		
		List<LinkedHashMap<String, Object>> result = dataDao.getMFCollections();
		
		return result;
	}
	
	public List<String> getMFCollection(String cname, String ver, String dataFormat, String classId) {
		
		Map<String, Object> param = new HashMap<String, Object> ();
		param.put("tbName", cname);
		param.put("ver", ver);
		param.put("dataFormat", dataFormat);
		param.put("classId", classId);
		
		List<String> result = dataDao.getMFCollection(param);	
				
		return result;
	}
	
	public List<String> getMFCollection(String cname, String ver, String dataFormat, int compressFactor) {
		
		Map<String, Object> param = new HashMap<String, Object> ();
		param.put("tbName", cname);
		param.put("ver", ver);
		param.put("dataFormat", dataFormat);
		param.put("compFactor", compressFactor);
		
		List<String> result = dataDao.getMFCollection(param);	
				
		return result;
	}
	
	public int deleteMFCollection(String cname) {
		Map<String, Object> param = new HashMap<String, Object> ();
		param.put("tbName", cname);
		
		int result = dataDao.deleteMgeometryColumns(param);
		result = dataDao.dropMFCollection(param);
		
		return result;
	}
	
	public List<String> getMFCollectionAbbr(String cname, String ver, String dataFormat) {
		
		Map<String, Object> param = new HashMap<String, Object> ();
		param.put("tbName", cname);
		param.put("ver", ver);
		param.put("dataFormat", dataFormat);
		
		List<String> result = dataDao.getMFCollectionAbbr(param);
		
		return result;
	}	
	
}
