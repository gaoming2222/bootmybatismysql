package com.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.entity.FbtmpR;
import com.springboot.entity.FctmpP;
import com.springboot.entity.TmpR;
import com.springboot.entity.Station;
import com.springboot.mapper.FbtmpRMapper;
import com.springboot.mapper.FctmpPMapper;
import com.springboot.mapper.TmpRMapper;
import com.springboot.mapper.StationMapper;
import com.springboot.tools.JsonUtility;

@Service
public class GetDataService {
	
	@Autowired
    private StationMapper userMapper;
	
	@Autowired
    private FbtmpRMapper fbtmpRMapper;
	
	@Autowired
    private FctmpPMapper fctmpPMapper;
	
	@Autowired
    private TmpRMapper tmpRMapper;
	
	public String getAllUsers() {
		List<Station> users = userMapper.getAllStations();
		return JsonUtility.convertBean2Json(users);
    }
	
	/**
	 * 分布链水温
	 * @return
	 */
	public String getAllFbtmpR() {
		List<FbtmpR> fbtmpRs = fbtmpRMapper.getAllFbtmpR();
		return JsonUtility.convertBean2Json(fbtmpRs);
    }
	
	/**
	 * 分层水温
	 * @return
	 */
	public String getAllFctmpP() {
		List<FctmpP> fctmpPs = fctmpPMapper.getAllFctmpP();
		return JsonUtility.convertBean2Json(fctmpPs);
    }
	
	/**
	 * 表层水温
	 * @return
	 */
	public String getAllTmpR() {
		List<TmpR> tmpRs = tmpRMapper.getAllTmpR();
		return JsonUtility.convertBean2Json(tmpRs);
    }

}
