package com.qianfeng.dao;

import java.util.List;
import java.util.Map;

import com.qianfeng.entity.User;

public interface IUserDao {
	/**
	 * 查询所有人员
	 * @return
	 */
	public List<User> findAll();
	
	/**
	 * 根据id查找人员
	 * @param id
	 * @return
	 */
	public User findUserById(int id);
	
	/**
	 * 根据名字查找人员
	 * @param name
	 * @return
	 */
	public User findUserByName(String name);
	
	/**
	 * 添加人员
	 * @param user
	 */
	public void addUser(User user);
	
	/**
	 * 根据id删除人员
	 * @param id
	 */
	public void deleteUserById(int id);
	
	/**
	 * 修改成员信息
	 * @param user
	 */
	public void updateUser(User user);
	
	/**
	 * 计数多少人员
	 * @return
	 */
	public int count();
	
	/**
	 * 分页
	 * @param info
	 * @return
	 */
	public List<User> findByIndexAndSize(Map<String, Object> info);
	
	public void change(int id);
	
}
