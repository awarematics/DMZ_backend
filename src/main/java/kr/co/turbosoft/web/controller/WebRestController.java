package kr.co.turbosoft.web.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.turbosoft.web.service.WebRestService;

/**
 * WebService REST API Controller
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
public class WebRestController {

	private static final Logger logger = LoggerFactory.getLogger(WebRestController.class);

	@Autowired
	private WebRestService wrs;

	/**
	 * Landing pag
	 * 
	 * Service landing page operation
	 * 
	 * @return Map<String, Object>
	 * 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Map<String, Object> rootGetController() {
		logger.info("REST API - CONTROLLER START(GET: /)");

		Map<String, Object> resData = new LinkedHashMap<String, Object>();

		List<LinkedHashMap<String, Object>> data = wrs.getLandingService();

		resData.put("links", data);

		logger.info("REST API - CONTROLLER END(GET: /)");

		return resData;
	}

	/**
	 * Conformance classes
	 * 
	 * Conformance operation
	 * 
	 * @return Map<String, Object>
	 * 
	 */
	@RequestMapping(value = "/conformance", method = RequestMethod.GET)
	public Map<String, Object> comfomanceGetController() {
		logger.info("REST API - CONTROLLER START(GET: /conformance)");

		Map<String, Object> resData = new LinkedHashMap<String, Object>();

		List<Object> data = wrs.getConformance();

		resData.put("conformsTo", data);

		logger.info("REST API - CONTROLLER END(GET: /conformance)");

		return resData;
	}

	/**
	 * GetCapabilities metadata
	 * 
	 * GetCapabilities operation
	 * 
	 * @return Map<String, Object>
	 * 
	 */
	@RequestMapping(value = "/getcapabilities", method = RequestMethod.GET)
	public Map<String, Object> getcapabilitiesGetController() {
		logger.info("REST API - CONTROLLER START(GET: /getcapabilities)");

		Map<String, Object> resData = new LinkedHashMap<String, Object>();

//		List<Object> data = wrs.getCapabilities();

		logger.info("REST API - CONTROLLER END(GET: /getcapabilities)");

		return resData;
	}

	/**
	 * Feature collections metadata
	 * 
	 * Retrieving Meta Data of Moving Feature Collections
	 * 
	 * @return Map<String, Object>
	 * 
	 */
	@RequestMapping(value = "/mfcollections", method = RequestMethod.GET)
	public Map<String, Object> mfcollectionsGetController() {
		logger.info("REST API - CONTROLLER START(GET: /mfcollections)");

		Map<String, Object> resData = new LinkedHashMap<String, Object>();

		Map<String, Object> links = new LinkedHashMap<String, Object>();

		links.put("href", "http://geocms.u-gis.net/datapi/mfcollections/?f=application+json");
		links.put("rel", "self");
		links.put("type", "application/json");
		links.put("title", "this document");

		resData.put("links", links);

		List<LinkedHashMap<String, Object>> data = wrs.getMFCollections();

		resData.put("collections", data);

		logger.info("REST API - CONTROLLER END(GET: /mfcollections)");

		return resData;
	}

	/**
	 * Feature collections metadata
	 * 
	 * Creating a Moving Feature Collection in Collections
	 * 
	 * @return Map<String, Object>
	 * 
	 */
	@RequestMapping(value = "/mfcollections", method = RequestMethod.POST)
	public Map<String, Object> mfcollectionsPostController(@RequestBody Map<String, Object> obj) {
		logger.info("REST API - CONTROLLER START(POST: /mfcollections)");

		Map<String, Object> resData = new LinkedHashMap<String, Object>();
		
		int result = wrs.putMFCollection(obj);
		
		if (result == 0) {
			resData.put("result", "ok");
			resData.put("count", 1);
		} else {
			resData.put("result", "fail");
			resData.put("message", "This Job was fail!");
			resData.put("count", 0);
		}

		logger.info("REST API - CONTROLLER END(POST: /mfcollections)");

		return resData;
	}

	/**
	 * Feature collections metadata
	 * 
	 * Retrieving Meta Data of a Moving Feature Collection
	 * 
	 * @param 
	 *   ver:[
	 *   	1.0(GeoJson)
	 *   	1.1(Moving Feature), 
	 *   	1.2(Moving Feature Extension)
	 *   ],
	 *   dataFormat:[
	 *   	json,   	
	 *   	xml
	 *   ]
	 * @return Map<String, Object>
	 * @throws IOException 
	 * 
	 */
	@RequestMapping(value = "/mfcollection/{collection_name}", method = RequestMethod.GET)
	public JsonNode mfcollectionGetController(@PathVariable("collection_name") String cname,
			@RequestParam(value = "ver", defaultValue = "1.1") String ver,
			@RequestParam(value = "dataFormat", defaultValue = "json") String dataFormat,
			@RequestParam(value = "classId", defaultValue = "") String classId) throws IOException {
		logger.info("REST API - CONTROLLER START(GET: /mfcollection/{collection_name})");

		List<String> data = wrs.getMFCollection(cname, ver, dataFormat, classId);
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode node = mapper.readTree(data.get(0));

		logger.info("REST API - CONTROLLER END(GET: /mfcollection/{collection_name})");

		return node;
	}
	
	@RequestMapping(value = "/mfcollection/{collection_name}", method = RequestMethod.DELETE)
	public Map<String, Object> mfcollectionDeleteController(@PathVariable("collection_name") String cname,
			@RequestParam(value = "ver", defaultValue = "1.1") String ver,
			@RequestParam(value = "dataType", defaultValue = "json") String dataType) throws IOException {
		logger.info("REST API - CONTROLLER START(DELETE: /mfcollection/{collection_name})");
				
		int result = wrs.deleteMFCollection(cname);

		Map<String, Object> resData = new LinkedHashMap<String, Object>();

		if (result > 0) {
			resData.put("result", "ok");
			resData.put("count", result);
		} else {
			resData.put("result", "fail");
			resData.put("message", "This Job was fail!");
			resData.put("count", result);
		}

		logger.info("REST API - CONTROLLER END(DELETE: /mfcollection/{collection_name})");

		return resData;
	}
	
	/**
	 * Feature collections metadata
	 * 
	 * Retrieving Meta Data of a Moving Feature Collection
	 * 
	 * @param 
	 *   ver:[
	 *   	1.0(GeoJson)
	 *   	1.1(Moving Feature), 
	 *   	1.2(Moving Feature Extension)
	 *   ],
	 *   dataFormat:[
	 *   	json,   	
	 *   	xml
	 *   ],
	 *   compressFactor:[
	 *   	10,5,1
	 *   ]
	 * @return Map<String, Object>
	 * @throws IOException 
	 * 
	 */
	@RequestMapping(value = "/mfcollection_compress/{collection_name}", method = RequestMethod.GET)
	public JsonNode mfcollectionGetController2(@PathVariable("collection_name") String cname,
			@RequestParam(value = "ver", defaultValue = "1.1") String ver,
			@RequestParam(value = "dataFormat", defaultValue = "json") String dataFormat,
			@RequestParam(value = "compressFactor", defaultValue = "1") int compressFactor) throws IOException {
		logger.info("REST API - CONTROLLER START(GET:(Compress) /mfcollection/{collection_name})");

		List<String> data = wrs.getMFCollection(cname, ver, dataFormat, compressFactor);
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode node = mapper.readTree(data.get(0));
		
		logger.info("REST API - CONTROLLER END(GET:(Compress) /mfcollection/{collection_name})");

		return node;
	}
	
	/**
	 * Feature collections metadata
	 * 
	 * Retrieving Meta Data of a Moving Feature Collection
	 * 
	 * @param 
	 *   ver:[
	 *   	1.0(GeoJson)
	 *   	1.1(Moving Feature), 
	 *   	1.2(Moving Feature Extension)
	 *   ],
	 *   dataFormat:[
	 *   	json,   	
	 *   	xml
	 *   ]
	 * @return Map<String, Object>
	 * @throws IOException 
	 * 
	 */
	@RequestMapping(value = "/mfcollectionAbbr/{collection_name}", method = RequestMethod.GET)
	public JsonNode mfcollectionAbbrGetController(@PathVariable("collection_name") String cname,
			@RequestParam(value = "ver", defaultValue = "1.1") String ver,
			@RequestParam(value = "dataFormat", defaultValue = "json") String dataFormat) throws IOException {
		logger.info("REST API - CONTROLLER START(GET: /mfcollectionAbbr/{collection_name})");

		List<String> data = wrs.getMFCollectionAbbr(cname, ver, dataFormat);
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode node = mapper.readTree(data.get(0));

		logger.info("REST API - CONTROLLER END(GET: /mfcollectionAbbr/{collection_name})");

		return node;
	}	
	
}
