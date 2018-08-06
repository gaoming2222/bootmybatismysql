package com.springboot.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Chart;
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

@Service
public class Data2ExcelServcie {
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
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> exportFctmpP(List list1) throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();
		// excel标题
		String[] title = { "站点ID", "数据时间", "温度", "高程", "库水位"};

		// excel文件名
		String fileName = "向家坝坝前" + sdf8.format(new Date()) + ".xls";
		
		//组合查询参数，昨天9：00-今天8：00的所有数据
		Map<String,Object> param = new HashMap<>();
		Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.DAY_OF_YEAR,-1);
	    calendar.set(Calendar.HOUR,9);
	    calendar.set(Calendar.MINUTE,0);
	    calendar.set(Calendar.SECOND,0);
	    Date strtDate = calendar.getTime();
	    calendar.add(Calendar.HOUR,23);
	    Date enDate = calendar.getTime();
	    
	    param.put("strtDate", strtDate);
	    param.put("enDate", enDate);
	    param.put("StationID", "6666");
	    
	    List<FctmpP> allFctmpPList = new ArrayList<>();
	    List<FctmpP> list = new ArrayList<>();
	    allFctmpPList = fctmpPMapper.getFctmpPById(param);
	    for(FctmpP fctmpP : allFctmpPList){
	    	String date = fctmpP.getDatatime();
	    	String date1 = sdf8.format(strtDate);
	    	if(date.equals(date1)){
	    		list.add(fctmpP);
	    	}
	    }

		String[][] content = new String[list.size()][];

		// sheet名
		String sheetName = "库水位：" + list.get(0).getWater() + "，日期：" +list.get(0).getDatatime();

		for (int i = 0; i < list.size(); i++) {
			content[i] = new String[title.length];
			FctmpP obj = (FctmpP) list.get(i);
			content[i][0] = obj.getStationID();
			content[i][1] = obj.getDatatime();
			content[i][2] = obj.getDepth();
			content[i][3] = obj.getWtmp();
			content[i][4] = obj.getRdatatime();
			content[i][5] = obj.getXh();
			content[i][6] = obj.getWater();
		}

		// 创建HSSFWorkbook
		HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
		
		String chartName = "向家坝坝前(" + sheetName + ")";

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
	
//	public Chart createChart(String chartName,DefaultCategoryDataset  dataset){
//		
//		return wb;
//	}

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
