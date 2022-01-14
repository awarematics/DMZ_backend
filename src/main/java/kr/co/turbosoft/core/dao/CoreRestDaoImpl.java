package kr.co.turbosoft.core.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CoreRestDaoImpl {
	
	private List<Object> result;
	private List<LinkedHashMap<String, Object>> resultList;	
	
	@Autowired
    private SqlSessionTemplate sqlSession;
	
	public List<LinkedHashMap<String, Object>> getLandingPage() {
		
		resultList = sqlSession.selectList("CORE.selectLandingPage");
		
		return resultList;
	}
	
	public List<LinkedHashMap<String, Object>> getLayers() {	
		
		resultList = sqlSession.selectList("CORE.selectLayers");
		
		return resultList;
	}
	
	public List<Object> getServerInfo() {
		result = sqlSession.selectList("CORE.selectServerInfo");
		
		return result;
	}
}
