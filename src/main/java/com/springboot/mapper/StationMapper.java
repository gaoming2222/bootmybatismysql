package com.springboot.mapper;

import java.util.List;

import com.springboot.entity.Station;

public interface StationMapper {

	/**获取所有站点信息
	 * @return
	 */
	public List<Station> getAllStations();
}
