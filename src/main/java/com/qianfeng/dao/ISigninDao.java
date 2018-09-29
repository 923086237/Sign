package com.qianfeng.dao;

import java.util.Date;
import java.util.List;

import com.qianfeng.entity.Signin;

public interface ISigninDao {
	public void addSignin(Signin signin);
	
	public List<Signin> findAll();
	
	public Signin findByName(String name);
	
	public List<Signin> findByNameAndDate(String name, Date date);
	
	public Signin findByDateAndRet(String date, int ret);
}
