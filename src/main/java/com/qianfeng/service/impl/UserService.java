package com.qianfeng.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.qianfeng.dao.IUserDao;
import com.qianfeng.entity.User;
import com.qianfeng.service.IUserService;
import com.qianfeng.utils.StringUtils;
import com.qianfeng.vo.PageBean;



@Service
public class UserService implements IUserService{

	@Autowired
	private IUserDao uDao;
	
	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		List<User> list = null;
		try {
			list = uDao.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub
		if(user == null){
			throw new RuntimeException("用户数据为空");
		}
		if(StringUtils.isEmpty(user.getName())){
			throw new RuntimeException("用户名不能为空");
		}
		if(StringUtils.isEmpty(user.getPassword())){
			throw new RuntimeException("密码不能为空");
		}
		// 如果有名字相同的员工不能添加
		try {
			User user2 = uDao.findUserByName(user.getName());
			if(user2 != null){
				throw new RuntimeException("存在同名的人，不能添加");
			}else{
				uDao.addUser(user);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public User findUserById(int id) {
		// TODO Auto-generated method stub
		return uDao.findUserById(id);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		if(user == null){
			throw new RuntimeException("员工数据为空");
		}
		try {
			
			User u = uDao.findUserById(user.getId());
			
			if(u != null){
				
				uDao.updateUser(user);
			}else{
				throw new RuntimeException("对应员工数据不存在，不能更新");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void deleteUserById(int id) {
		// TODO Auto-generated method stub
		try {
			
			User user = uDao.findUserById(id);
			if(user != null){
				uDao.deleteUserById(id);
			}else{
				throw new RuntimeException("对应人员数据不存在");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public PageBean<User> findUserByPage(int page) {
		// TODO Auto-generated method stub
		System.out.println("kkkkkkk");
		PageBean<User> pageInfo = new PageBean<>();
		
		pageInfo.setCurrentPage(page);
		
		// 获取表中的记录总数
		int count = uDao.count();
		// 计算总页数
		if(count % pageInfo.getPageSize() == 0){
			pageInfo.setTotalPage(count / pageInfo.getPageSize());
		}else{
			pageInfo.setTotalPage(count / pageInfo.getPageSize()+ 1);
		}
		// 根据页码计算分页查询时的开始索引
		int index = (page - 1) * pageInfo.getPageSize();
		Map<String, Object> map = new HashMap<>();
		map.put("index", index);
		map.put("size", pageInfo.getPageSize());
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		String name = (String) request.getSession(false).getAttribute("uname");	
		map.put("name", name);
		List<User> list = uDao.findByIndexAndSize(map);
		
		pageInfo.setPageInfos(list);
		
		return pageInfo;
	}

	@Override
	public User login(String name, String password) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(name)){
			throw new RuntimeException("用户名不能为空");
		}
		if(StringUtils.isEmpty(password)){
			throw new RuntimeException("密码不能为空");
		}
		User user = null;
		try {
			user = uDao.findUserByName(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		if(user == null){
			throw new RuntimeException("输入的用户名错误");
		}
		
		return user;
	}

	@Override
	public User findByName(String name) {
		// TODO Auto-generated method stub
		return uDao.findUserByName(name);
	}

	@Override
	public void signin(int id) {
		// TODO Auto-generated method stub
		uDao.change(id);
	}

}
