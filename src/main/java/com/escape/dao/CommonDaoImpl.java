package com.escape.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.escape.model.ChurchMember;
import com.escape.model.Member;
import com.escape.model.MemberVO;

@Repository
public class CommonDaoImpl implements CommonMapper {

	
	@Autowired
	private SqlSession sqlSession;
	
	public void addContract() {
		// TODO Auto-generated method stub
		
		System.out.println(this.getClass().getSimpleName());
		
		MemberMapper memberDao = sqlSession.getMapper(MemberMapper.class);
		Member persons = memberDao.selectByPrimaryKey("1");
		if(persons != null){
			System.out.println("[PID="+persons.getId()+"][NAME="+persons.getName()+"][AGE="+persons.getAge()+"]");
		}
		
		Member persons2 = memberDao.selectMember(2);
		if(persons2 != null){
			System.out.println("[PID2="+persons2.getId()+"][NAME="+persons2.getName()+"][AGE="+persons2.getAge()+"]");
		}
		
		List list2= memberDao.selectByExample(null);
		if(list2 != null){
			for (Object object : list2) {
				Member memberVO = (Member) object;
				System.out.println("[ID="+memberVO.getId()+"][NAME="+memberVO.getName()+"][AGE="+memberVO.getAge()+"]");				
			}
		}
		

		List list = sqlSession.selectList("userMapper.selectUser");
		if(list != null){
			for (Object object : list) {
				MemberVO memberVO = (MemberVO) object;
				System.out.println("[ID2="+memberVO.getId()+"][NAME2="+memberVO.getName()+"][AGE2="+memberVO.getAge()+"]");				
			}
		}
	}

	public List<ChurchMember> selectChurchMemberListByLike(String content) {
		// TODO Auto-generated method stub
		
		
		
		
		return null;
	}
}
