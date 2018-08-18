package com.springboot.mapper;

import java.util.List;
import java.util.Map;
import com.springboot.entity.TmpR;;

public interface TmpRMapper {
	
	/**获取所有表层水温
	 * @return
	 */
	public List<TmpR> getAllTmpR();
	
	
	public List<TmpR> getTmpRById(Map<String,Object> map);
	
	@SuppressWarnings("rawtypes")
	public List getTmpRAndWater(Map<String,Object> map);
	
	public int insertOneTmpR(Map<String,Object> map);
	
	public int insertOneWater(Map<String,Object> map);
	
	public int deleteTmpR(Map<String,Object> map);
	
	public int updateTmpR(Map<String,Object> map);
	
	public int updateWater(Map<String,Object> map);
	
	@SuppressWarnings("rawtypes")
	public int insertWaterBatch(List list);
	
	@SuppressWarnings("rawtypes")
	public int insertTmpRBatch(List list);
	
	

}
