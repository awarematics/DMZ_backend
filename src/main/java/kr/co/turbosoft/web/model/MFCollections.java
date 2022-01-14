package kr.co.turbosoft.web.model;

import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class MFCollections {

	private String name;
	private String type;
	private List<HashMap<String, Object>> properties;
	private List<HashMap<String, Object>> geometry;
	private List<HashMap<String, Object>> temporalGeometry;
	private List<HashMap<String, Object>> temporalProperties;
	
}
