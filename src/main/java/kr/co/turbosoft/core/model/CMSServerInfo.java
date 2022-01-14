package kr.co.turbosoft.core.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CMSServerInfo {

	private String uid;
	private String serverName;
	private String serverUrl;
	private int serverPort;
	private String serverId;
	private String serverPass;
	private String serverPath;
	private int serverViewPort;
	private String selectYN;
	private Timestamp rgdate;
	private Timestamp update;
}
