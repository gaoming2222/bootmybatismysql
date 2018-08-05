package com.springboot.entity;
/**
 * 
 * @author codergaoming
 * 分层水温
 */
public class FctmpP {
	private String StationID;
	private String Datatime;
	private String Depth;
	private String Wtmp;
	private String Rdatatime;
	private String Xh;
	private String Water;
	public String getStationID() {
		return StationID;
	}
	public void setStationID(String stationID) {
		StationID = stationID;
	}
	public String getDatatime() {
		return Datatime;
	}
	public void setDatatime(String datatime) {
		Datatime = datatime;
	}
	public String getDepth() {
		return Depth;
	}
	public void setDepth(String depth) {
		Depth = depth;
	}
	public String getWtmp() {
		return Wtmp;
	}
	public void setWtmp(String wtmp) {
		Wtmp = wtmp;
	}
	public String getRdatatime() {
		return Rdatatime;
	}
	public void setRdatatime(String rdatatime) {
		Rdatatime = rdatatime;
	}
	public String getXh() {
		return Xh;
	}
	public void setXh(String xh) {
		Xh = xh;
	}
	public String getWater() {
		return Water;
	}
	public void setWater(String water) {
		Water = water;
	}
}
