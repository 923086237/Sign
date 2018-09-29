package com.qianfeng.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.qianfeng.entity.User;
import com.qianfeng.entity.Signin;
import com.qianfeng.service.IUserService;
import com.qianfeng.utils.JsonUtils;
import com.qianfeng.vo.JsonBean;
import com.qianfeng.vo.PageBean;

@Controller
public class UserController {

	@Autowired
	private IUserService uService;
	
	@RequestMapping(value="RegisterUser", method=RequestMethod.POST)
	@ResponseBody
	public JsonBean addUser(User user) {
		try {
			uService.addUser(user);
			return JsonUtils.createJsonBean(1, "注册成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtils.createJsonBean(0, e.getMessage());
		}
	}
	
	@RequestMapping(value="loginUser", method=RequestMethod.POST)
	@ResponseBody
	public JsonBean loginUser(String name, String password, HttpSession session) {
		try {
			uService.login(name, password);
			session.setAttribute("uname", name);
			return JsonUtils.createJsonBean(1, "登录成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtils.createJsonBean(0, e.getMessage());
		}
	}
	
	@RequestMapping(value="deleteUser", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean DeleteUser(int id) {
		try {
			uService.deleteUserById(id);
			return JsonUtils.createJsonBean(1, "删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtils.createJsonBean(0, e.getMessage());
		}
	}
	
	@RequestMapping(value="findAllUser", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean findAllUser() {
		List<User> list = null;
		try {
			list = uService.findAllUsers();
			return JsonUtils.createJsonBean(1, list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtils.createJsonBean(0, e.getMessage());
		}
	}
	
	@RequestMapping(value="page/{page}", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean findAllUser(@PathVariable("page") int page){
		
		JsonBean bean = new JsonBean();
		
		PageBean<User> pageInfo = uService.findUserByPage(page);
		bean.setCode(1);
		bean.setMsg(pageInfo);
		return bean;
	}
	
	@RequestMapping(value="updateUser/{id}", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean updateUser(String name, String password, String sex, String phone) {
		
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setSex(sex);
		user.setPhone(phone);
		
		try {
			
			uService.updateUser(user);
			return JsonUtils.createJsonBean(1, "修改成功");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return JsonUtils.createJsonBean(0, e.getMessage());
		}
	}
	
	@RequestMapping(value="fogetPwd", method=RequestMethod.POST)
	@ResponseBody
	public JsonBean forget(User user) {
		JsonBean bean = new JsonBean();
		try {
			User old = uService.findByName(user.getName());
			if (old == null) {
				throw new RuntimeException("用户不存在！");
			}
			System.out.println("old: " + old.getPhone());
			System.out.println("user: " + user.getPhone());
			if (!old.getPhone().equals(user.getPhone())) {
				throw new RuntimeException("验证信息错误！");
			}
			bean.setCode(1);
			bean.setMsg("验证成功！您的密码为: " + old.getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			bean.setCode(0);
			bean.setMsg(e.getMessage());
		}
		return bean;
	}
	
//	@RequestMapping(value="Signin", method=RequestMethod.POST)
//	@ResponseBody
//	public JsonBean Signin(int id, HttpSession session) {
//		uService.signin(id);
//		
//		return JsonUtils.createJsonBean(1, "签到成功");
//	}
}
