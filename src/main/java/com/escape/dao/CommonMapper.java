package com.escape.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.escape.model.ChurchMember;

public interface CommonMapper {

	public void addContract();
	
	public List<ChurchMember> selectChurchMemberListByLike(@Param("content") String content);
	
}
