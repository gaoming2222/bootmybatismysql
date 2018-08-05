package com.springboot.mapper;

import java.util.List;

import com.springboot.entity.FctmpP;
/**
 * 
 * @author codergaoming
 *
 */

public interface FctmpPMapper {

	/**
	 * 获取分层水温
	 * @return
	 */
	public List<FctmpP> getAllFctmpP();

}
