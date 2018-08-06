package com.springboot.entity;

/**
 * 
 * @author codergaoming 表层水温
 */

public class TmpR {

	private String StationID;
	private String datatime;
	private String data;
	private String dataplus;
	private String ATMP;
	private String WTMP;
	public String getStationID() {
		return StationID;
	}
	public void setStationID(String stationID) {
		StationID = stationID;
	}
	public String getDatatime() {
		return datatime;
	}
	public void setDatatime(String datatime) {
		this.datatime = datatime;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDataplus() {
		return dataplus;
	}
	public void setDataplus(String dataplus) {
		this.dataplus = dataplus;
	}
	public String getATMP() {
		return ATMP;
	}
	public void setATMP(String aTMP) {
		ATMP = aTMP;
	}
	public String getWTMP() {
		return WTMP;
	}
	public void setWTMP(String wTMP) {
		WTMP = wTMP;
	}
	

	

}
