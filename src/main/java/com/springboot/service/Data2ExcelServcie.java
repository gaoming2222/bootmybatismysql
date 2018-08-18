package com.springboot.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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

	
	public Map<String, Object> exportTmpR() throws Exception {
		logger.info("exportTmpR：生成excel开始");
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
		try{
			logger.info("exportTmpR:查询所有站点信息开始");
			stationList1 = stationMapper.getAllStations();
			logger.info("exportTmpR:查询所有站点信息完成");
		}catch(Exception e){
			logger.error("exportTmpR：查询所有站点信息失败");
			throw new Exception(e);
		}
		
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
		logger.info("fileName" + fileName);
		for(Station station : stationList){
			String sheetName = station.getCName();
			logger.info("fileName + sheetName" + fileName + "-" + sheetName);
			Map<String,Object> params = new HashMap<>();
			params.put("StationID", station.getStationid());
			Thread.sleep(10*1000);
			logger.info("exportTmpR: 查询表层水温信息开始" + params);
			List<TmpR> list = tmpRMapper.getTmpRById(params);
			logger.info("exportTmpR: 查询表层水温信息完成");
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
		logger.info("exportTmpR：生成excel完成");
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
		logger.info("exportFbtmpR: 生成excel开始");
		if(list == null || list.size() == 0){
			logger.error("exportFbtmpR：分布链水温数据为空");
		}
		Map<String, Object> resMap = new HashMap<String, Object>();
		// excel标题
		String[] title = { "站点ID", "数据时间", "高程", "水温","水位"};
		// excel文件名
		String fileName = "叠梁门水温数据表" + sdf.format(new Date()) + ".xls";
		logger.info("filename" + fileName);
		// excel文件内容
		String[][] content = new String[list.size()][];

		Calendar date = Calendar.getInstance();
	    String year = String.valueOf(date.get(Calendar.YEAR));
	        
		// sheet名
		String sheetName =  year  + "年主数据";
		logger.info("filename + sheetname" + fileName + "-" + sheetName);
		for (int i = 0; i < list.size(); i++) {
			content[i] = new String[title.length];
			FbtmpR obj = (FbtmpR) list.get(i);
			content[i][0] = obj.getStationID();
			content[i][1] = obj.getDatatime();
			content[i][2] = obj.getDepth();
			content[i][3] = obj.getWtmp();
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
		logger.info("exportFbtmpR: 生成excel完成");
		return resMap;
	}
	
	
	public Map<String, Object> exportFctmpP(Station station) throws Exception {
		logger.info("exportFctmpP:生成excel开始");
		Map<String, Object> resMap = new HashMap<String, Object>();
		// excel标题
		String[] title = { "站点ID", "数据时间", "温度", "高程", "库水位" };

		// excel文件名
		String fileName = station.getCName() + sdf8.format(new Date()) + ".xls";
		logger.info("fileName" + fileName);
		// 组合查询参数，昨天9：00-今天8：00的所有数据
		Map<String, Object> param = new HashMap<>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 8);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 30);
		Calendar calendarFalg = (Calendar) calendar.clone();
		Date strtDate = calendar.getTime();
		calendar.add(Calendar.HOUR, 24);
		Date enDate = calendar.getTime();
		
		

		// TODO 由于没有数据,用于測試
//		String strt = "2018-05-28 09:00:00";
//		strtDate = sdf.parse(strt);
//		String end = "2018-5-29 08:00:00";
//		enDate = sdf.parse(end);
//		calendarFalg.setTime(strtDate);

		param.put("strtDate", strtDate);
		param.put("enDate", enDate);
		param.put("StationID", station.getStationid());
		
		List<FctmpP> allFctmpPList = new ArrayList<>();
		try{
			logger.info("exportFctmpP:查询分层水温数据开始" + param);
			allFctmpPList = fctmpPMapper.getFctmpPById(param);
			logger.info("exportFctmpP:查询分层水温数据完成");
		}catch(Exception e){
			logger.error("exportFctmpP:查询分层水温数据失败");
			throw new Exception(e);
		}
		
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
			logger.info("filename + sheetname" + fileName + "-" +  sheetName);
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
		logger.info("exportFctmpP:生成excel完成");
		return resMap;
	}

	@Transactional
	public  Map<String,Object> getExcelData(MultipartFile file) throws IOException{
		Map<String,Object> resMap = new HashMap<>();
        checkFile(file);
         //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<Map<String,Object>> params = new ArrayList<>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    continue; 
                }
                //获得当前sheet的开始行
                int firstRowNum  = sheet.getFirstRowNum();
                //获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                //循环除了第一行的所有行
                for(int rowNum = firstRowNum+2;rowNum <= lastRowNum;rowNum++){
                	Map<String,Object> param = new HashMap<>();
                    //获得当前行
                	Row row = sheet.getRow(rowNum);
                    if(row == null){
                        continue;
                    }
                    //获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    //获得当前行的列数
                    int lastCellNum = row.getLastCellNum();
                    //循环当前行
                    for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                    	Cell cell = row.getCell(cellNum);
                    	String value = getCellValue(cell);
                    	if(cellNum == 0){
                    		param.put("StationID", value);
                    	}
                    	if(cellNum == 1){
                    		param.put("month", value);
                    	}
                    	if(cellNum == 2){
                    		param.put("day", value);
                    	}
                    	if(cellNum == 3){
                    		param.put("hour", value);
                    	}
                    	if(cellNum == 4){
                    		param.put("data", value);
                    	}
                    	if(cellNum == 5){
                    		param.put("dataplus", value);
                    	}
                    	if(cellNum == 6){
                    		param.put("ATMP", value);
                    	}
                    	if(cellNum == 7){
                    		param.put("WTMP", value);
                    	}
                    }
                    Calendar calendar = Calendar.getInstance();
                    try {
                    	calendar.set(2018, Integer.parseInt((String)param.get("month"))-1, Integer.parseInt((String)param.get("day")), Integer.parseInt((String)param.get("hour")), 0, 0);                 
					} catch (Exception e) {
						resMap.put("MSG","获取数据时间出错，请检查文件内容" + e.getMessage());
						return resMap;
					}
                   Date dataTime = ((Calendar)calendar.clone()).getTime();
                   param.put("Datatime", dataTime);
                   param.put("RDataTime", dataTime);
                   params.add(param);
                }
            }
        }
        
        try{
        	tmpRMapper.insertTmpRBatch(params);
        	tmpRMapper.insertWaterBatch(params);
        }catch(Exception e){
        	resMap.put("MSG", "批量插入失败");
        	logger.error(e.getMessage());
        	return resMap;
        }
        
        return resMap;
    }
	/**
     * 检查文件是否存在
     * @param file
     * @throws IOException
     */
     private void checkFile(MultipartFile file) throws IOException{
         //判断文件是否存在
         if(null == file){
             logger.error("checkFile：上传的excel文件为空！");
         }
         //获得文件名
         String fileName = file.getOriginalFilename();
         //判断文件是否是excel文件
         if(!fileName.endsWith("xls") && !fileName.endsWith("xlsx")){
        	 logger.error(fileName + "不是excel文件");
         }
     }
     /**
      * 获取Excel
      * @param file
      * @return
      */
     private  Workbook getWorkBook(MultipartFile file) {
         //获得文件名
         String fileName = file.getOriginalFilename();
         //创建Workbook工作薄对象，表示整个excel
         Workbook workbook = null;
         try {
             //获取excel文件的io流
             InputStream is = file.getInputStream();
             //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
             if(fileName.endsWith("xls")){
                 //2003
                 workbook = new HSSFWorkbook(is);
             }else if(fileName.endsWith("xlsx")){
                 //2007 及2007以上
                 workbook = new XSSFWorkbook(is);
             }
         } catch (IOException e) {
             logger.error(e.getMessage());
         }
         return workbook;
     }
     
     public  String getCellValue(Cell cell){
         String cellValue = "";
         if(cell == null){
             return cellValue;
         }
     //判断数据的类型
         switch (cell.getCellType()){
             case Cell.CELL_TYPE_NUMERIC: //数字
                 cellValue = stringDateProcess(cell);
                 break;
             case Cell.CELL_TYPE_STRING: //字符串
                 cellValue = String.valueOf(cell.getStringCellValue());
                 break;
             case Cell.CELL_TYPE_BOOLEAN: //Boolean
                 cellValue = String.valueOf(cell.getBooleanCellValue());
                 break;
             case Cell.CELL_TYPE_FORMULA: //公式
                 cellValue = String.valueOf(cell.getCellFormula());
                 break;
             case Cell.CELL_TYPE_BLANK: //空值
                 cellValue = "";
                 break;
             case Cell.CELL_TYPE_ERROR: //故障
                 cellValue = "非法字符";
                 break;
             default:
                 cellValue = "未知类型";
                 break;
         }
         return cellValue;
     }
     
     
     /**
      * 时间格式处理
      * @return
      * @author Liu Xin Nan
      * @data 2017年11月27日
      */
     private String stringDateProcess(Cell cell){
         String result = new String();  
         if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
             SimpleDateFormat sdf = null;  
             if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {  
                 sdf = new SimpleDateFormat("HH:mm");  
             } else {// 日期  
                 sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
             }  
             Date date = cell.getDateCellValue();  
             result = sdf.format(date);  
         } else if (cell.getCellStyle().getDataFormat() == 58) {  
             // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
             double value = cell.getNumericCellValue();  
             Date date = org.apache.poi.ss.usermodel.DateUtil  
                     .getJavaDate(value);  
             result = sdf.format(date);  
         } else {  
             double value = cell.getNumericCellValue();  
             CellStyle style = cell.getCellStyle();  
             DecimalFormat format = new DecimalFormat();  
             String temp = style.getDataFormatString();  
             // 单元格设置成常规  
             if (temp.equals("General")) {  
                 format.applyPattern("#");  
             }  
             result = format.format(value);  
         }  
         
         return result;
     }
    
}
