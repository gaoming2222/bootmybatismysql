package com.springboot.web;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.fastjson.JSONObject;
import com.springboot.entity.FbtmpR;
import com.springboot.entity.Station;
import com.springboot.mapper.FbtmpRMapper;
import com.springboot.service.Data2ExcelServcie;
import com.springboot.service.GetDataService;
import com.springboot.service.SendMailService;



@Controller
@EnableAutoConfiguration
@EnableScheduling
@MapperScan("com.springboot.mapper")
@SpringBootApplication
public class UserController{

	private static Logger logger = Logger.getLogger(SendMailService.class);
	
	@Autowired
	private GetDataService getDataService;
	
	
	@Autowired
	private FbtmpRMapper  fbtmpRMapper;

	
	@Autowired
	private Data2ExcelServcie data2ExcelService;
	
	@Autowired
	private SendMailService sendMailService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
    String home() {
        return "fileupload";
    }
	/**
	 * 查询水温数据
	 * @author codergaoming
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequestMapping(value = "/getswdata", method = RequestMethod.GET)
	@ResponseBody
    List  getSwData () throws Exception {
		List<Map<String,Object>> resList = new ArrayList<>();
		resList = getDataService.getAllTmpRAndWater(null);
		return resList;
    }
	/**
	 * 根据站点ID和数据时间删除数据
	 * @author codergaoming
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/deleteswdata", method = RequestMethod.POST)
	@ResponseBody
	Map deleteSwData(@RequestBody JSONObject params){
		Map<String,Object> resMap = new HashMap<>();
		String stationID = params.getString("StationID");
        String dataTime = params.getString("Datatime");
        Map<String,Object> prmMap = new HashMap<>();
        prmMap.put("StationID", stationID);
        prmMap.put("Datatime", dataTime);
        resMap = getDataService.deleteSwData(prmMap);
		return resMap;
	}
	/**
	 * 单条插入雨量数据
	 * @author codergaoming
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/insertswdata", method = RequestMethod.POST)
	@ResponseBody
	Map InsetSwData(@RequestBody JSONObject params){
		Map<String,Object> resMap = new HashMap<>();
		String stationID = params.getString("StationID");
        String dataTime = params.getString("Datatime");
        String data = params.getString("data");
        String dataplus = params.getString("dataplus");
        String ATMP = params.getString("ATMP");
        String WTMP = params.getString("WTMP");
        Map<String,Object> prmMap = new HashMap<>();
        prmMap.put("StationID", stationID);
        prmMap.put("Datatime", dataTime);
        prmMap.put("data", data);
        prmMap.put("dataplus", dataplus);
        prmMap.put("ATMP", ATMP);
        prmMap.put("WTMP", WTMP);
        resMap = getDataService.InsetSwData(prmMap);
		return resMap;
	}
	
	/**
	 * 批量插入雨量数据
	 * @author codergaoming
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/batchinsertswdata", method = RequestMethod.POST)
	@ResponseBody
	Map BatchInsetSwData(@RequestBody JSONObject params){
		Map<String,Object> resMap = new HashMap<>();
		String stationID = params.getString("StationID");
        String dataTime = params.getString("Datatime");
        String data = params.getString("data");
        String dataplus = params.getString("dataplus");
        String ATMP = params.getString("ATMP");
        String WTMP = params.getString("WTMP");
        Map<String,Object> prmMap = new HashMap<>();
        prmMap.put("StationID", stationID);
        prmMap.put("Datatime", dataTime);
        prmMap.put("data", data);
        prmMap.put("dataplus", dataplus);
        prmMap.put("ATMP", ATMP);
        prmMap.put("WTMP", WTMP);
		return resMap;
	}
	/**
	 * 根据站点ID和数据时间更新数据
	 * @author codergaoming
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/updateswdata", method = RequestMethod.POST)
	@ResponseBody
	Map UpdateSwData(@RequestBody JSONObject params){
		Map<String,Object> resMap = new HashMap<>();
		String stationID = params.getString("StationID");
        String dataTime = params.getString("Datatime");
        String data = params.getString("data");
        String dataplus = params.getString("dataplus");
        String ATMP = params.getString("ATMP");
        String WTMP = params.getString("WTMP");
        Map<String,Object> prmMap = new HashMap<>();
        prmMap.put("StationID", stationID);
        prmMap.put("Datatime", dataTime);
        prmMap.put("data", data);
        prmMap.put("dataplus", dataplus);
        prmMap.put("ATMP", ATMP);
        prmMap.put("WTMP", WTMP);
        getDataService.updateSwData(prmMap);
		return resMap;
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/operData", method = RequestMethod.POST)
    @ResponseBody
	public void operData(HttpServletRequest request,HttpServletResponse response){
		Map map=request.getParameterMap();  
	    Set keSet=map.entrySet();
	    Map<String,Object> param = new HashMap<>();
	    for(Iterator itr=keSet.iterator();itr.hasNext();){  
	        Map.Entry me=(Map.Entry)itr.next();  
	        Object ok=me.getKey();
	        String key = ok.toString();
	        Object ov=me.getValue();  
	        String[] value=new String[1];  
	        if(ov instanceof String[]){  
	            value=(String[])ov;  
	        }else{  
	            value[0]=ov.toString();  
	        }  
	        if(key.contains("action")){
	        	param.put("action", value[0]);
	        }
	        if(key.contains("StationID")){
	        	param.put("StationID", value[0]);
	        }
	        if(key.contains("Datatime")){
	        	param.put("Datatime", new Date());
	        }
	        if(key.contains("ATMP")){
	        	param.put("ATMP", value[0]);
	        }
	        if(key.contains("WTMP")){
	        	param.put("WTMP", value[0]);
	        }
	        
	        for(int k=0;k<value.length;k++){  
	            System.out.println(ok+"="+value[k]);  
	        }  
	      }
	    getDataService.insertTmpR(param);
	    //return "header";
	    
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> uploadFile(HttpServletRequest request,HttpServletResponse response) throws Exception {
        logger.info("uploadFile:获取上传的Excel文件开始");
        request.setCharacterEncoding("UTF-8");
        Map<String, Object> resMap = new HashMap<String, Object>();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        
        /** 页面控件的文件流* */
        List<MultipartFile> multipartFileList = new ArrayList<>();
        Map map =multipartRequest.getFileMap();
        MultipartFile multipartFile = null;
        for (Iterator i = map.keySet().iterator(); i.hasNext();) {
            Object obj = i.next();
            multipartFile=(MultipartFile) map.get(obj);
            multipartFileList.add(multipartFile);
           }
        data2ExcelService.getExcelData(multipartFile);
        response.setStatus(200);
        resMap.put("msg", "");
        resMap.put("response", response);
        return resMap;
    }
	

	@RequestMapping("/getAllUsers")
    @ResponseBody
    public String getAllUsers() {
		return getDataService.getAllUsers();
	}
	@RequestMapping("/getAllFbtmpR")
    @ResponseBody
	public String getAllFbtmpR() {
		return getDataService.getAllFbtmpR();
	}
	@RequestMapping("/getAllFctmpP")
    @ResponseBody
	public String getAllFctmpP() {
		return getDataService.getAllFctmpP();
	}
	@RequestMapping("/getAllTmpR")
    @ResponseBody
	public String getAllTmpR() {
		return getDataService.getAllTmpR();
	}
	
	@RequestMapping("/exportTmpR")
    @ResponseBody
	public Map<String,Object> exportTmpR() throws Exception{
		try {
			return data2ExcelService.exportTmpR();
		} catch (Exception e) {
			logger.error("exportTmpR:生成excel文档错误" + e.getMessage());
			throw new  Exception(e);
		}
	}
	
	@RequestMapping("/exportFctmpP")
    @ResponseBody
	public Map<String,Object> exportFctmpP(Station station) throws Exception {
		try {
			return data2ExcelService.exportFctmpP(station);
		} catch (Exception e) {
			logger.error("exportFctmpP:生成excel文档错误" + e.getMessage());
			throw new  Exception(e);
		}
	}
	
	@RequestMapping("/exportFbtmpR")
    @ResponseBody
	public Map<String,Object> exportFbtmpR() throws Exception {
		List<FbtmpR> fbtmpRs = new ArrayList<>();
		try {
			logger.info("exportFbtmpR：查询分布链水温开始");
			fbtmpRs = fbtmpRMapper.getAllFbtmpR();
			logger.info("exportFbtmpR：查询分布链水温完成");
		} catch (Exception e) {
			logger.error("查询分布链水温失败 " + e.getMessage());
			throw new Exception(e);
		}
		
		try {
			return data2ExcelService.exportFbtmpR(fbtmpRs);
		} catch (Exception e) {
			logger.error("exportFbtmpR:生成excel文档错误" + e.getMessage());
			throw new  Exception(e);
		}
	}
	@Scheduled(cron = "0 0 9 * * ?")
	//@Scheduled(cron = "0 */1 * * * ?")
	@RequestMapping("/sendMail")
    @ResponseBody
	public void sendMail() throws Exception{
		logger.info("sendMail：发送邮件定时任务开始");
		List<Map<String,Object>> isMapList = new ArrayList<>();
		Map<String,Object> isMapTmp  = new HashMap<>();
		Map<String,Object> isMapFbtmpR  = new HashMap<>();
		Map<String,Object> isMapFctmpP  = new HashMap<>();
		isMapTmp = exportTmpR();
		if(isMapTmp != null && !isMapTmp.isEmpty() && isMapTmp.size() > 0 && isMapTmp.keySet() != null){
			isMapList.add(isMapTmp);
		}
		isMapFbtmpR = exportFbtmpR();
		if(isMapFbtmpR != null && !isMapFbtmpR.isEmpty() && isMapFbtmpR.size() > 0 && isMapFbtmpR.keySet() != null){
			isMapList.add(isMapFbtmpR);
		}
		Station station1 = new Station();
		Station station2 = new Station();
		station1.setStationid("7777");
		station1.setCName("向家坝坝前");
		station2.setStationid("6666");
		station2.setCName("溪洛渡坝前 ");
		List<Station> stationList = new ArrayList<>();
		stationList.add(station1);
		stationList.add(station2);
		for(Station station : stationList){
			isMapFctmpP = exportFctmpP(station);
			if(isMapFctmpP != null && !isMapFctmpP.isEmpty() && isMapFctmpP.size() > 0 && isMapFctmpP.keySet() != null){
				isMapList.add(isMapFctmpP);
			}
		}
		//File fileTmpR = new File("d://test.xls")
        //14516778王志飞  305355933雷昌友  高明1925013282
		String mailAddress = "1925013282@qq.com";
        try {
        	logger.info("sendMail: 邮件接收邮箱：" + mailAddress);
			sendMailService.sendMail(mailAddress, "你好，这是长江水利委员会水文监测数据，无需回复。", "水文数据邮件",null,isMapList);
		} catch (IOException e) {
			logger.error("sendMail:邮件发送失败" + e.getMessage());
			throw new Exception(e);
		}  
        logger.info("邮件发送成功");
	}
}
