package com.springboot.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.entity.FbtmpR;
import com.springboot.entity.FctmpP;
import com.springboot.entity.Station;
import com.springboot.entity.TmpR;
import com.springboot.mapper.FctmpPMapper;
import com.springboot.mapper.StationMapper;
import com.springboot.mapper.TmpRMapper;
import com.springboot.tools.ExcelUtil;

import org.apache.log4j.Logger;

@Service
public class Data2ExcelServcie {
	
	private static Logger logger = Logger.getLogger(SendMailService.class);
	
	@Autowired
    private StationMapper stationMapper;
	
	@Autowired
    private TmpRMapper tmpRMapper;
	
	@Autowired
    private FctmpPMapper fctmpPMapper;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf8 = new SimpleDateFormat("yyyy-MM-dd");

	@SuppressWarnings("rawtypes")
	public Map<String, Object> exportTmpR(List list1) throws Exception {
		
		//初始化sheet页
		List<String> ids = new ArrayList<>();
		ids.add("5001");
		ids.add("5002");
		ids.add("5003");
		ids.add("5004");
		ids.add("5005");
		ids.add("5006");
		ids.add("5007");
		ids.add("5008");
		ids.add("5009");
		ids.add("5011");
		ids.add("6001");
		ids.add("6002");
		ids.add("6003");
		ids.add("6004");
		
		List<Station> stationList1 = new ArrayList<>();
		List<Station> stationList = new ArrayList<>();
		stationList1 = stationMapper.getAllStations();
		for(Station station : stationList1){
			if (ids.contains((station.getStationid()))) {
				stationList.add(station);
			}
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		Map<String, Object> resMap = new HashMap<String, Object>();
		// excel标题
		String[] title = { "站点ID", "数据时间", "水位", "流量m3/s", "气温℃", "水温℃"};

		// excel文件名
		String fileName = "2018年汇总表" + sdf.format(new Date()) + ".xls";
		
		for(Station station : stationList){
			String sheetName = station.getCName();
			Map<String,Object> params = new HashMap<>();
			params.put("StationID", station.getStationid());
			Thread.sleep(10*1000);
			List<TmpR> list = tmpRMapper.getTmpRById(params);
			String[][] content = new String[list.size()][];
			for (int i = 0; i < list.size(); i++) {
				content[i] = new String[title.length];
				TmpR obj = (TmpR) list.get(i);
				content[i][0] = obj.getStationID();
				content[i][1] = obj.getDatatime();
				content[i][2] = obj.getData();
				content[i][3] = obj.getDataplus();
				content[i][4] = obj.getATMP();
				content[i][5] = obj.getWTMP();
			}
			wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, wb);
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		wb.write(baos);
		baos.flush();
		byte[] bt = baos.toByteArray();
		InputStream is = new ByteArrayInputStream(bt, 0, bt.length);
		baos.close();

		resMap.put("FILE_NAME", fileName);
		resMap.put("FILE", is);

		return resMap;

		// //响应到客户端
		// try{
		// this.setResponseHeader(response, fileName);
		// OutputStream os = response.getOutputStream();
		// wb.write(os);
		// os.flush();
		// os.close();
		// }catch(Exception e){
		// e.printStackTrace();
		// }
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> exportFbtmpR(List list) throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();
		// excel标题
		String[] title = { "站点ID", "数据时间", "高程", "水温","水位"};

		// excel文件名
		String fileName = "叠梁门水温数据表" + sdf.format(new Date()) + ".xls";

		String[][] content = new String[list.size()][];

		 Calendar date = Calendar.getInstance();
	     String year = String.valueOf(date.get(Calendar.YEAR));
	        
		// sheet名
		String sheetName =  year  + "年主数据";

		for (int i = 0; i < list.size(); i++) {
			content[i] = new String[title.length];
			FbtmpR obj = (FbtmpR) list.get(i);
			content[i][0] = obj.getStationID();
			content[i][1] = obj.getDatatime();
			content[i][2] = obj.getDepth();
			content[i][3] = obj.getWtmp();
			//content[i][4] = obj.getWater();
		}

		// 创建HSSFWorkbook
		HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		wb.write(baos);
		baos.flush();
		byte[] bt = baos.toByteArray();
		InputStream is = new ByteArrayInputStream(bt, 0, bt.length);
		baos.close();

		resMap.put("FILE_NAME", fileName);
		resMap.put("FILE", is);

		return resMap;

		// //响应到客户端
		// try{
		// this.setResponseHeader(response, fileName);
		// OutputStream os = response.getOutputStream();
		// wb.write(os);
		// os.flush();
		// os.close();
		// }catch(Exception e){
		// e.printStackTrace();
		// }
	}
	
	
	public Map<String, Object> exportFctmpP(Station station) throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();
		// excel标题
		String[] title = { "站点ID", "数据时间", "温度", "高程", "库水位" };

		// excel文件名
		String fileName = station.getCName() + sdf8.format(new Date()) + ".xls";

		// 组合查询参数，昨天9：00-今天8：00的所有数据
		Map<String, Object> param = new HashMap<>();
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DAY_OF_YEAR, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Calendar calendarFalg = (Calendar) calendar.clone();
		Date strtDate = calendar.getTime();
		calendar.add(Calendar.HOUR, 23);
		Date enDate = calendar.getTime();

		// TODO 由于没有数据,用于測試
		String strt = "2018-05-28 09:00:00";
		strtDate = sdf.parse(strt);
		String end = "2018-5-29 08:00:00";
		enDate = sdf.parse(end);
		calendarFalg.setTime(strtDate);

		param.put("strtDate", strtDate);
		param.put("enDate", enDate);
		param.put("StationID", station.getStationid());

		List<FctmpP> allFctmpPList = new ArrayList<>();
		
		allFctmpPList = fctmpPMapper.getFctmpPById(param);
		int flag = 1;
		HSSFWorkbook wb = new HSSFWorkbook();
		while (flag <= 24) {
			List<FctmpP> list = new ArrayList<>();
			for (FctmpP fctmpP : allFctmpPList) {
				String date = fctmpP.getDatatime();
				String date1 = sdf.format(calendarFalg.getTime());
				if (date.equals(date1) || date.contains(date1)) {
					list.add(fctmpP);
				}
			}
			calendarFalg.add(Calendar.HOUR, 1);
			flag = flag + 1;
			
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			List<FctmpP> NotNulllist = new ArrayList<>();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					FctmpP fctmpP = (FctmpP) list.get(i);
					if (fctmpP != null && fctmpP.getDepth() != null && fctmpP.getDepth() != "" && fctmpP.getWtmp() != null && fctmpP.getWtmp() != "") {
						dataset.addValue(Double.valueOf(fctmpP.getDepth()), "高程-温度", fctmpP.getWtmp());
						NotNulllist.add(fctmpP);
					}

				}
			}
			
			if(NotNulllist == null || NotNulllist.size() == 0){
				continue;
			}
			String[][] content = new String[NotNulllist.size()][];

			// sheet名
			String water = "";
			String time = "";
			for (int i = 0; i < NotNulllist.size(); i++) {
				FctmpP obj = (FctmpP) NotNulllist.get(i);
				if (obj.getWater() != null && obj.getDatatime() != null) {
					water = obj.getWater();
					time = obj.getDatatime();
					break;
				}
			}
			if("".equals(water) || "".equals(time)){
				continue;
			}
			String sheetNameTmp = "库水位" + water + "，日期" + time;
			String sheetNameTmp1 =  "日期" + time;
			String sheetName = sheetNameTmp1.replace(':', '-');
			logger.info(sheetNameTmp);
			// String sheetName = "test";

			for (int i = 0; i < NotNulllist.size(); i++) {
				content[i] = new String[title.length];
				FctmpP obj = (FctmpP) NotNulllist.get(i);
				content[i][0] = obj.getStationID();
				content[i][1] = obj.getDatatime();
				content[i][3] = obj.getDepth();
				content[i][2] = obj.getWtmp();
				content[i][4] = obj.getWater();
			}
			
			DefaultXYDataset xydataset = new DefaultXYDataset();
			int size = NotNulllist.size(); 
			double[][] xydatas = new double[2][size];  
			if (NotNulllist != null && NotNulllist.size() > 0) {
				for (int i = 0; i < NotNulllist.size(); i++) {
					FctmpP fctmpP = (FctmpP) NotNulllist.get(i);
					if (fctmpP != null && fctmpP.getDepth() != null && fctmpP.getDepth() != "" && fctmpP.getWtmp() != null && fctmpP.getWtmp() != "") {
						xydatas[0][i] = Double.parseDouble(fctmpP.getWtmp());  
						xydatas[1][i] = Double.parseDouble(fctmpP.getDepth());  	
					}

				}
			}
			 xydataset.addSeries("温度-高程", xydatas);
			// 创建HSSFWorkbook
			wb = ExcelUtil.getHSSFWorkbook1(sheetName, title, content, wb, xydataset, sheetName,sheetNameTmp);

			
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		wb.write(baos);
		baos.flush();
		byte[] bt = baos.toByteArray();
		InputStream is = new ByteArrayInputStream(bt, 0, bt.length);
		baos.close();

		resMap.put("FILE_NAME", fileName);
		resMap.put("FILE", is);
		return resMap;

		// //响应到客户端
		// try{
		// this.setResponseHeader(response, fileName);
		// OutputStream os = response.getOutputStream();
		// wb.write(os);
		// os.flush();
		// os.close();
		// }catch(Exception e){
		// e.printStackTrace();
		// }
	}

//	// 发送响应流方法
//	public void setResponseHeader(HttpServletResponse response, String fileName) {
//		try {
//			try {
//				fileName = new String(fileName.getBytes(), "ISO-8859-1");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//			response.setContentType("application/octet-stream;charset=ISO-8859-1");
//			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//			response.addHeader("Pargam", "no-cache");
//			response.addHeader("Cache-Control", "no-cache");
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
}
