package com.qianfeng.service;

import java.util.List;

import com.qianfeng.entity.Signin;

public interface ISigninService {
	
	public List<Signin> findAllSign();
	
	public void addSign(Signin sign);
	
	public Signin findByName(String uname);
	
	public Signin findByDateAndRet(String date, int ret);

}
