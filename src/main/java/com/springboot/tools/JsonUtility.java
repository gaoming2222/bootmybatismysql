package com.springboot.tools;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json工具
 * 
 * @author huangwei
 *
 */
public class JsonUtility {

	private static Logger logger = LogManager.getLogger(JsonUtility.class
			.getName());

	public static String convertBean2Json(Object bean) {
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(bean);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage());
		}
		return json;
	}
}
