package com.escape.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escape.dao.ChurchMemberMapper;
import com.escape.dao.CommonMapper;
import com.escape.model.ChurchMember;
import com.escape.model.ChurchMemberExample;
import com.escape.model.MemberVO;

@Service
public class ChurchMemberServiceImpl implements ChurchMemberService {
	
	@Autowired
	private SqlSession sqlSession;
	
	public void insert(ChurchMember record) {
		final ChurchMemberMapper mapper = sqlSession.getMapper(ChurchMemberMapper.class);
		mapper.insert(record);
	}
	
	public void update(ChurchMember record) {
		final ChurchMemberMapper mapper = sqlSession.getMapper(ChurchMemberMapper.class);
		mapper.updateByPrimaryKeySelective(record);
		
	}
	
	public List<ChurchMember> selectByEmail(String email){
		final ChurchMemberMapper mapper = sqlSession.getMapper(ChurchMemberMapper.class);
		final ChurchMemberExample example = new ChurchMemberExample();
		example.createCriteria().andEmailEqualTo(email);
		return mapper.selectByExample(example);
	}

	public List<ChurchMember> getChurchMemberList(ChurchMemberExample example) {
		final ChurchMemberMapper mapper = sqlSession.getMapper(ChurchMemberMapper.class);
		return mapper.selectByExample(example);
	}
	
	public List<ChurchMember> getChurchMemberListByLike(String content) {
		final CommonMapper mapper = sqlSession.getMapper(CommonMapper.class);
		return mapper.selectChurchMemberListByLike(content);
	}

	public ChurchMember getChurchMemberForZCV() {
		ChurchMember result= null;
		final ChurchMemberMapper mapper = sqlSession.getMapper(ChurchMemberMapper.class);
		final ChurchMemberExample example = new ChurchMemberExample();
		example.setOrderByClause("ID ASC LIMIT 1");
		example.createCriteria().andDelYnEqualTo("N").andSendZcYnEqualTo("N");
		List<ChurchMember> list = mapper.selectByExample(example);
		if(list != null && list.size() > 0){
			result = list.get(0);
		}
		return result;
	}

	public List<ChurchMember> getChurchMemberList(Map<String,Object> param) {
		final ChurchMemberMapper mapper = sqlSession.getMapper(ChurchMemberMapper.class);
		return mapper.selectList(param);
	}
	
	public int getChurchMemberListCount(Map<String,Object> param) {
		final ChurchMemberMapper mapper = sqlSession.getMapper(ChurchMemberMapper.class);
		return mapper.selectListCount(param);
	}
	
}
