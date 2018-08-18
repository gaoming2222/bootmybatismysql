package com.springboot.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springboot.entity.FbtmpR;
import com.springboot.entity.FctmpP;
import com.springboot.entity.TmpR;
import com.springboot.entity.Station;
import com.springboot.mapper.FbtmpRMapper;
import com.springboot.mapper.FctmpPMapper;
import com.springboot.mapper.TmpRMapper;
import com.springboot.mapper.StationMapper;
import com.springboot.tools.JsonUtility;

@Service
public class GetDataService {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static Logger logger = Logger.getLogger(SendMailService.class);
	
	@Autowired
    private StationMapper userMapper;
	
	@Autowired
    private FbtmpRMapper fbtmpRMapper;
	
	@Autowired
    private FctmpPMapper fctmpPMapper;
	
	@Autowired
    private TmpRMapper tmpRMapper;
	
	public String getAllUsers() {
		List<Station> users = userMapper.getAllStations();
		return JsonUtility.convertBean2Json(users);
    }
	
	/**
	 * 分布链水温
	 * @return
	 */
	public String getAllFbtmpR() {
		List<FbtmpR> fbtmpRs = fbtmpRMapper.getAllFbtmpR();
		return JsonUtility.convertBean2Json(fbtmpRs);
    }
	
	/**
	 * 分层水温
	 * @return
	 */
	public String getAllFctmpP() {
		List<FctmpP> fctmpPs = fctmpPMapper.getAllFctmpP();
		return JsonUtility.convertBean2Json(fctmpPs);
    }
	
	/**
	 * 表层水温
	 * @return
	 */
	public String getAllTmpR() {
		List<TmpR> tmpRs = tmpRMapper.getAllTmpR();
		return JsonUtility.convertBean2Json(tmpRs);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insertTmpR(Map map) {
		try{
			tmpRMapper.insertOneTmpR(map);
		}catch(Exception e){
			e.getMessage();
		}
		 
    }
	
	/**
	 * 获取人工站数据
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getAllTmpRAndWater(Map<String,Object> param) throws Exception {
		List tmpRs = tmpRMapper.getTmpRAndWater(null);
		if( tmpRs == null || tmpRs.size() == 0){
			return null;
		}
		for(Map map : (List<Map<String,Object>>)tmpRs){
			if (map.get("Datatime") != null ) {
				try{
					String dataTime = sdf.format(map.get("Datatime"));
					map.put("Datatime", dataTime);
				}catch(Exception e){
					logger.error(e.getMessage());
					throw new Exception("Datatime格式错误");
				}
			}
			if (map.get("RDataTime") != null ) {
				try{
					String rDataTime = sdf.format(map.get("RDataTime"));
					map.put("RDataTime", rDataTime);
				}catch(Exception e){
					logger.error(e.getMessage());
					throw new Exception("RDataTime格式错误");
				}
			}
		}
		return tmpRs;
		//return JsonUtility.convertBean2Json(tmpRs);
    }
	/**
	 *  单条插入数据
	 */
	@Transactional
	public Map<String,Object> InsetSwData(Map<String, Object> param) {
		Map<String,Object> resMap = new HashMap<>();
		resMap.put("MSG","");
		if (param.get("StationID") == null || param.get("StationID")=="") {
			resMap.put("MSG","站号不能为空");
			logger.error("站号不能为空");
			return resMap;
		}
		if (param.get("Datatime") == null || param.get("Datatime") == null) {
			resMap.put("MSG","数据时间不能不能为空");
			logger.error("站号不能为空");
			return resMap;
		}
		String dataTime = ((String)param.get("Datatime")).replace('T', ' ');
		param.put("Datatime", dataTime);
		param.put("rdatatime", dataTime);
		int flag1 = 0;
		int flag2 = 0;
		try{
			flag1 = tmpRMapper.insertOneTmpR(param);
			flag2 = tmpRMapper.insertOneWater(param);
		}catch(Exception e){
			resMap.put("MSG",e.getMessage());
			logger.error("新增水温信息失败" + e.getStackTrace());
			return resMap;
		}
		if(flag1 != 1 || flag2 !=1){
			resMap.put("MSG","删除水温信息异常，请核查数据");
			logger.error("删除水温信息失败");
			return resMap;
		}
		resMap.put("num", flag1);
		return resMap;
    }
	
	/**
	 *  批量插入数据
	 */
	@Transactional
	public void BatchInsetSwData(List<Map<String,Object>> params) {
		
    }
	/**
	 * 根据站点ID和数据时间删除数据
	 */
	@Transactional
	public Map<String,Object> deleteSwData(Map<String, Object> param) {
		Map<String,Object> resMap = new HashMap<>();
		resMap.put("MSG","");
		if (param.get("StationID") == null || param.get("StationID")=="") {
			resMap.put("MSG","站号不能为空");
			logger.error("站号不能为空");
			return resMap;
		}
		if (param.get("Datatime") == null || param.get("Datatime") == null) {
			resMap.put("MSG","数据时间不能不能为空");
			logger.error("站号不能为空");
			return resMap;
		}
		String tempStr = (String)param.get("Datatime");
		Date dataTime = new Date();
		try {
			dataTime = sdf.parse(tempStr);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		param.put("Datatime", dataTime);
		int flag = 0;
		try{
			flag = tmpRMapper.deleteTmpR(param);
		}catch(Exception e){
			resMap.put("MSG",e.getMessage());
			logger.error("删除水温信息失败");
			return resMap;
		}
		if(flag != 1){
			resMap.put("MSG","删除水温信息异常，请核查数据");
			logger.error("删除水温信息失败");
			return resMap;
		}
		resMap.put("num", flag);
		return resMap;
		
		
    }
	
	/**
	 * 根据站点ID和数据时间
	 */
	@Transactional
	public Map<String,Object> updateSwData(Map<String, Object> param) {
		Map<String,Object> resMap = new HashMap<>();
		resMap.put("MSG","");
		if (param.get("StationID") == null || param.get("StationID")=="") {
			resMap.put("MSG","站号不能为空");
			logger.error("站号不能为空");
			return resMap;
		}
		if (param.get("Datatime") == null || param.get("Datatime") == null) {
			resMap.put("MSG","数据时间不能不能为空");
			logger.error("站号不能为空");
			return resMap;
		}
		
		if (param.get("Datatime") == null || param.get("Datatime") == null) {
			resMap.put("MSG","数据时间不能不能为空");
			logger.error("站号不能为空");
			return resMap;
		}
		String tempStr = (String)param.get("Datatime");
		Date dataTime = new Date();
		try {
			dataTime = sdf.parse(tempStr);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		param.put("Datatime", dataTime);
		
		int flag1 = 0;
		int flag2 = 0;
		try{
			flag1 = tmpRMapper.updateTmpR(param);
			flag2 = tmpRMapper.updateWater(param);
		}catch(Exception e){
			resMap.put("MSG",e.getMessage());
			logger.error("更新水温信息失败" + e.getStackTrace());
			return resMap;
		}
		if(flag1 != 1 || flag2 !=1){
			resMap.put("MSG","更新水温信息异常，请核查数据");
			logger.error("更新水温信息失败");
			return resMap;
		}
		resMap.put("num", flag1);
		return resMap;
		
    }
	
	
}
