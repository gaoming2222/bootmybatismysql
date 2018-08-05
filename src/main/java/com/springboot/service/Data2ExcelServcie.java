package com.springboot.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.springboot.entity.FbtmpR;
import com.springboot.entity.FctmpP;
import com.springboot.entity.TmpR;
import com.springboot.tools.ExcelUtil;

@Service
public class Data2ExcelServcie {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@SuppressWarnings("rawtypes")
	public Map<String, Object> exportTmpR(List list) throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();
		// excel标题
		String[] title = { "站点ID", "时间", "ATMP", "WTMP", "RDataTime", "传输类型", "数据源类型", "_MASK_FROM_V2" };

		// excel文件名
		String fileName = "表层水温" + sdf.format(new Date()) + ".xls";

		String[][] content = new String[list.size()][];

		// sheet名
		String sheetName = "表层水温表";

		for (int i = 0; i < list.size(); i++) {
			content[i] = new String[title.length];
			TmpR obj = (TmpR) list.get(i);
			content[i][0] = obj.getStationID();
			content[i][1] = obj.getDatatime();
			content[i][2] = obj.getATMP();
			content[i][3] = obj.getWTMP();
			content[i][4] = obj.getRDataTime();
			content[i][5] = obj.getTrantype();
			content[i][6] = obj.getSourcetype();
			content[i][6] = obj.getSourcetype();
			content[i][7] = obj.get_MASK_FROM_V2();
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
	public Map<String, Object> exportFbtmpR(List list) throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();
		// excel标题
		String[] title = { "站点ID", "时间", "DEPTH", "WTMP", "RDataTime", "xh","water","传输类型", "数据源类型"};

		// excel文件名
		String fileName = "分布链水温" + sdf.format(new Date()) + ".xls";

		String[][] content = new String[list.size()][];

		// sheet名
		String sheetName = "分布链水温";

		for (int i = 0; i < list.size(); i++) {
			content[i] = new String[title.length];
			FbtmpR obj = (FbtmpR) list.get(i);
			content[i][0] = obj.getStationID();
			content[i][1] = obj.getDatatime();
			content[i][2] = obj.getDepth();
			content[i][3] = obj.getWtmp();
			content[i][4] = obj.getRdatatime();
			content[i][5] = obj.getXh();
			content[i][6] = obj.getWater();
			content[i][7] = obj.getTrantype();
			content[i][8] = obj.getSourcetype();
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
	public Map<String, Object> exportFctmpP(List list) throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();
		// excel标题
		String[] title = { "站点ID", "时间", "DEPTH", "WTMP", "RDataTime", "xh","water"};

		// excel文件名
		String fileName = "分层水温" + sdf.format(new Date()) + ".xls";

		String[][] content = new String[list.size()][];

		// sheet名
		String sheetName = "分层水温";

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
