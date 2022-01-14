package kr.co.turbosoft.web.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class WebRestDaoImpl {
	
	private List<LinkedHashMap<String, Object>> resultList;	
	
	@Autowired
    private SqlSessionTemplate sqlSession;
	
	public List<LinkedHashMap<String, Object>> getLandingPage() {	
		
		resultList = sqlSession.selectList("REST.selectLandingPage");
		
		return resultList;
	}	
	
	public int putMFCollection(Map<String, Object> obj) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.convertValue(obj, JsonNode.class);
		
		Map<String, Object> param = new HashMap<String, Object> ();
		param.put("tbName", obj.get("name"));
		
		int result = sqlSession.selectOne("REST.isMFCollection", param); 
		
		if(result>0) {
			result = sqlSession.update("REST.deleteMgeometryColumns", param);
			result = sqlSession.update("REST.dropMFCollection", param);
		}
			
		String sql = "";
		sql += "CREATE TABLE " + obj.get("name") + "(";
		
		for (int i=0; i<jsonNode.get("properties").size(); i++) {
			sql += jsonNode.get("properties").get(i).get("name").textValue() + " " + jsonNode.get("properties").get(i).get("type").textValue();
			if (i < jsonNode.get("properties").size()-1) {
				sql += ",";	
			}
		}
		
		if (jsonNode.get("geometry") != null) {
			sql += "," + jsonNode.get("geometry").get(0).get("name").textValue() + " geometry(" + jsonNode.get("geometry").get(0).get("type").textValue() + ")"; 
		}
		
		if (jsonNode.get("temporalGeometry") != null) {
			sql += "," + jsonNode.get("temporalGeometry").get(0).get("name").textValue() + " " + jsonNode.get("temporalGeometry").get(0).get("type").textValue(); 
		}		
		
		sql += ");";
		
		
		System.out.println(sql);
		
		result = sqlSession.update("REST.createNewTable", sql);
		
		
		//param.put("trajColumn", jsonNode.get("temporalGeometry").get(0).get("name").textValue());
		//param.put("trajType", jsonNode.get("temporalGeometry").get(0).get("type").textValue());
		
		sqlSession.update("REST.addMgeometryColumns", param);
		
		return result;
	}
	
	public List<LinkedHashMap<String, Object>> getMFCollections() {	
												
		resultList = sqlSession.selectList("REST.selectMFCollections");
		
		return resultList;
	}
	
	public List<String> getMFCollection(Map<String, Object> param) {	
		
		List<String> resultList = sqlSession.selectList("REST.selectMFCollection", param);
		
		return resultList;
	}
	
	public int deleteMgeometryColumns(Map<String, Object> param) {
		int result = sqlSession.update("REST.deleteMgeometryColumns", param);
		
		return result;
	}
	
	public int dropMFCollection(Map<String, Object> param) {
		int result = sqlSession.update("REST.dropMFCollection", param);
		
		return result;
	}
	
	public List<String> getMFCollectionAbbr(Map<String, Object> param) {	
		
		List<String> resultList = sqlSession.selectList("REST.selectMFCollectionAbbr", param);
		
		return resultList;
	}
	
	public List<String> getTest2(Map<String, Object> param) {	
		
		String sql = "";
		
		sql += " SELECT\r\n"
				+ "			row_to_json(fc)\r\n"
				+ "		FROM\r\n"
				+ "		(\r\n"
				+ "			SELECT				\r\n"
				+ "				array_to_json(array_agg(f)) as \"collections\"\r\n"
				+ "			FROM\r\n"
				+ "			(\r\n"
				+ "				SELECT\r\n"
				+ "					carid					\r\n"
				+ "				FROM\r\n"
				+ "					bdd10k\r\n"
				+ "				GROUP BY carid\r\n"
				+ "			) AS f \r\n"
				+ "		) AS fc";
		
		List<String> resultList = sqlSession.selectList("PGM.selectTest", sql);
		
		return resultList;
	}	
}
