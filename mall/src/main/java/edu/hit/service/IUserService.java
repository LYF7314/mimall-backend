package edu.hit.service;

import edu.hit.pojo.User;
import edu.hit.vo.ResponseVo;


public interface IUserService {

	/**
	 * 注册
	 */
	ResponseVo<User> register(User user);

	/**
	 * 登录
	 */
	ResponseVo<User> login(String username, String password);
}
