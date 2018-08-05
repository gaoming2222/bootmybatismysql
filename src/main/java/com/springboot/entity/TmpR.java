package com.springboot.entity;
/**
 * 
 * @author codergaoming
 *  表层水温
 */

public class TmpR {
	
	private String StationID;
	private String Datatime;
	private String ATMP;
	private String WTMP;
	private String RDataTime;
	private String Trantype;
	private String Sourcetype;
	private String _MASK_FROM_V2;
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
	public String getRDataTime() {
		return RDataTime;
	}
	public void setRDataTime(String rDataTime) {
		RDataTime = rDataTime;
	}
	public String getTrantype() {
		return Trantype;
	}
	public void setTrantype(String trantype) {
		Trantype = trantype;
	}
	public String getSourcetype() {
		return Sourcetype;
	}
	public void setSourcetype(String sourcetype) {
		Sourcetype = sourcetype;
	}
	public String get_MASK_FROM_V2() {
		return _MASK_FROM_V2;
	}
	public void set_MASK_FROM_V2(String _MASK_FROM_V2) {
		this._MASK_FROM_V2 = _MASK_FROM_V2;
	}
	
	

}
