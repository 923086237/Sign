package com.qianfeng.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.qianfeng.entity.User;
import com.qianfeng.vo.PageBean;

public interface IUserService {
	
	public List<User> findAllUsers();
	
	/**
	 * 注册
	 * @param user
	 */
	public void addUser(User user);
	
	public User login(String name, String password); 
	
	public User findUserById(int id);
	
	public void updateUser(User user);
	
	public void deleteUserById(int id);
	
	// 根据页码查询分页的数据
	public PageBean<User> findUserByPage(int page);

	public User findByName(String name);
	
	public void signin(int id);

	
}
