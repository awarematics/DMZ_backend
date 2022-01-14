package kr.co.turbosoft.core.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CMSProjectContent {

	private int idx;
	private String uid;
	private String projectname;
	private int sharetype;
	private Timestamp rgdate;
	private Timestamp update;
}
