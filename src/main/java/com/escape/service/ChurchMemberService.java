package com.escape.service;

import java.util.List;
import java.util.Map;

import com.escape.model.ChurchMember;
import com.escape.model.ChurchMemberExample;

public interface ChurchMemberService {

	void insert(ChurchMember record);	
	
	void update(ChurchMember record);
	
	ChurchMember getChurchMemberForZCV();
	
	List<ChurchMember> selectByEmail(String email);
	
	List<ChurchMember> getChurchMemberList(ChurchMemberExample record);
	
	List<ChurchMember> getChurchMemberListByLike(String content);
	
	List<ChurchMember> getChurchMemberList(Map<String,Object> param);
	
	int getChurchMemberListCount(Map<String,Object> param);
}
