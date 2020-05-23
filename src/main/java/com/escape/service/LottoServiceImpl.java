package com.escape.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escape.dao.LottoMapper;
import com.escape.model.Lotto;

@Service
public class LottoServiceImpl implements LottoService {
	
	private SqlSession sqlSession = null;
	
	private LottoMapper mapper = null ;
	
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
		this.mapper = this.sqlSession.getMapper(LottoMapper.class);
	}

	public Lotto selectByPrimaryKey(Integer round) {
		return mapper.selectByPrimaryKey(round);
	}

	public int insertSelective(Lotto record) {
		return mapper.insertSelective(record);
	}

}
