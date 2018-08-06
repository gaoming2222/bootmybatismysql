package com.springboot.mapper;

import java.util.List;
import java.util.Map;

import com.springboot.entity.TmpR;;

public interface TmpRMapper {
	
	/**获取所有用户信息
	 * @return
	 */
	public List<TmpR> getAllTmpR();
	
	
	public List<TmpR> getTmpRById(Map<String,Object> map);

}
