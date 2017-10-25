package com.springboot.mapper;

import java.util.List;

import com.springboot.entity.Users;

public interface UserMapper {

	/**获取所有用户信息
	 * @return
	 */
	public List<Users> getAllUsers();
}
