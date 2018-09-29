package com.qianfeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qianfeng.dao.ISigninDao;
import com.qianfeng.entity.Signin;
import com.qianfeng.service.ISigninService;
@Service
public class SigninService implements ISigninService {

	@Autowired
	private ISigninDao sDao;
	
	@Override
	public List<Signin> findAllSign() {
		// TODO Auto-generated method stub
		return sDao.findAll();
	}

	@Override
	public void addSign(Signin sign) {
		// TODO Auto-generated method stub
		sDao.addSignin(sign);
	}

	@Override
	public Signin findByName(String uname) {
		// TODO Auto-generated method stub
		return sDao.findByName(uname);
	}

	@Override
	public Signin findByDateAndRet(String date, int ret) {
		// TODO Auto-generated method stub
		return sDao.findByDateAndRet(date, ret);
	}

}
