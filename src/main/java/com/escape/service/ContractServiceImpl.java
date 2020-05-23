package com.escape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escape.dao.CommonMapper;

@Service
public class ContractServiceImpl implements ContractService {
	
	@Autowired
	private CommonMapper contractDao = null;
	

	public void addContract() {
		System.out.println(this.getClass().getSimpleName());
		
		contractDao.addContract();
	}
}
