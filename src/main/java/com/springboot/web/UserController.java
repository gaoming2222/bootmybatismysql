package com.springboot.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return "index";
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
	public Map<String,Object> exportTmpR() {
		//List<TmpR> tmpRs = tmpRMapper.getAllTmpR();
		try {
			//return data2ExcelService.exportTmpR(tmpRs, response);
			return data2ExcelService.exportTmpR(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping("/exportFctmpP")
    @ResponseBody
	public Map<String,Object> exportFctmpP(Station station) {
		try {
			//return data2ExcelService.exportTmpR(tmpRs, response);
			return data2ExcelService.exportFctmpP(station);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping("/exportFbtmpR")
    @ResponseBody
	public Map<String,Object> exportFbtmpR() {
		List<FbtmpR> fbtmpRs = fbtmpRMapper.getAllFbtmpR();
		try {
			//return data2ExcelService.exportTmpR(tmpRs, response);
			return data2ExcelService.exportFbtmpR(fbtmpRs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	@Scheduled(cron = "0 */1 * * * * ")
	@RequestMapping("/sendMail")
    @ResponseBody
	public void sendMail(){
		logger.info("定时任务启动成功");
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
        try {
			sendMailService.sendMail("1925013282@qq.com", "你好，这是长江水利委员会水文监测数据，无需回复。", "水文数据邮件",null,isMapList);
		} catch (IOException e) {
			e.printStackTrace();
		}  
        logger.info("邮件发送成功");
		
	}
}
