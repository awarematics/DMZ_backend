package kr.co.turbosoft.core.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.turbosoft.core.service.CoreRestService;

/**
 * CORE REST API Controller
 *
 * NOTE :
 * 
 * @author YOO KI HYUN 
 * @email khyoo1221@gmail.com
 * @since 2020.06.01
 * @version 0.1
 * 
 */
@RestController
@RequestMapping(value = "/core")
public class CoreRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(CoreRestController.class);
		
	private List<Object> data;
	private List<LinkedHashMap<String, Object>> dataList;	
	
	@Autowired
	private CoreRestService crs;
	
	/**
	 * Landing pag
	 * 
	 * Core landing page operation 
	 *   
	 * @return Map<String, Object>
	 * 
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Map<String, Object> coreRootGetController() {
		logger.info("CORE API - CONTROLLER START(GET: /)");
	
		Map<String, Object> resData = new LinkedHashMap<String, Object> ();
		
		List<LinkedHashMap<String, Object>> data = crs.getLandingService();
		
		resData.put("links", data);
		
		logger.info("CORE API - CONTROLLER END(GET: /)");
		
		return resData;
	}
	
	/**
	 * 코어(시스템) 데이터 
	 * 
	 * @param request    요청 파라미터 
	 * @return String
	 * 
	 */
	@RequestMapping(value = "/getServerInfo", method = RequestMethod.GET)
	public Map<String, Object> getServerInfoController(@RequestParam HashMap<String, Object> request) {
		logger.info("CORE API - CONTROLLER START(GET: /core)");
	
		Map<String, Object> resData = new LinkedHashMap<String, Object> ();
		
		data = crs.getServerInfoService();
		
		resData.put("payload", data);
		
		logger.info("CORE API - CONTROLLER END(GET: /core)");
		
		return resData;
	}
	
	/**
	 * 코어(시스템) 데이터 
	 * 
	 * @param request    요청 파라미터 
	 * @return String
	 * 
	 */
	@RequestMapping(value = "/getLayers", method = RequestMethod.GET)
	public Map<String, Object> getLayersController(@RequestParam HashMap<String, Object> request) {
		logger.info("CORE API - CONTROLLER START(GET: /core)");
	
		Map<String, Object> resData = new LinkedHashMap<String, Object> ();
		
//		dataList = crs.getLayersService();
		
		resData.put("payload", dataList);
		
		logger.info("CORE API - CONTROLLER END(GET: /core)");
		
		return resData;
	}	
}
