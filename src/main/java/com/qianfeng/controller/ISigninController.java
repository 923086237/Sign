package com.qianfeng.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qianfeng.entity.Signin;
import com.qianfeng.service.ISigninService;
import com.qianfeng.utils.JsonUtils;
import com.qianfeng.vo.JsonBean;

@Controller
public class ISigninController {
	
	
	
	@Autowired
	private ISigninService sService;
	
	@RequestMapping(value="findAllSign", method=RequestMethod.GET)
	@ResponseBody
	public JsonBean findAllSign() {
		
		List<Signin> list = sService.findAllSign();
		return JsonUtils.createJsonBean(1, list);
	}
	
	
	
	@RequestMapping(value = "addSign", method = RequestMethod.GET)
	@ResponseBody
	public JsonBean addsignin(HttpSession session) {
		String name = (String) session.getAttribute("uname");
		Signin signin = new Signin();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		//添加签到日期
		String date = sdf1.format(new Date());
		signin.setDate(date);
		//添加用户名
		signin.setUname(name);
		//判断为早or晚签到，并添加信息
		Signin s1 = sService.findByDateAndRet(date, 1);
		Signin s2 = sService.findByDateAndRet(date, 2);
		if (s1 == null && s2 == null) {
			signin.setRet(1);
		} else if (s1 != null && s2 == null) {
			signin.setRet(2);
		} else if (s1 != null && s2 != null) {
			return JsonUtils.createJsonBean(1, "已签两次");
		}
		//计算签到状态(正常or早退迟到)
		Date stime = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date ms = null;
		Date me = null;
		Date es = null;
		try {
			ms = dateFormat.parse(date + " 08:00:00");
			me = dateFormat.parse(date + " 09:00:00");
			es = dateFormat.parse(date + " 20:00:00");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (stime.before(ms)) {
			return JsonUtils.createJsonBean(1, "时间没到，不能签");
		}
		signin.setStime(stime);
		if (signin.getRet() == 1) {
			if (stime.before(me) && ms.before(stime)) {
				signin.setStatus("准时");
			} else if (me.before(stime)) {
				signin.setStatus("迟到");
			}
		} else {
			if (es.before(stime)) {
				signin.setStatus("准时");
			} else if (stime.before(es)) {
				signin.setStatus("早退");
			}
		}
		sService.addSign(signin);
		return JsonUtils.createJsonBean(1, "");
	}
	
	
	
}
