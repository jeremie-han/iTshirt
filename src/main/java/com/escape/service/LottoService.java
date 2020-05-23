package com.escape.service;

import com.escape.model.Lotto;

public interface LottoService {
	
	Lotto selectByPrimaryKey(Integer round);
	
	int insertSelective(Lotto record);

}
