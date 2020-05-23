package com.escape.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.escape.dao.NhnCategoryMapper;
import com.escape.dao.NhnRankDateMapper;
import com.escape.dao.NhnRankMonthMapper;
import com.escape.dao.NhnRankWeekMapper;
import com.escape.dao.NhnTimeDimensionMapper;
import com.escape.model.NhnCategory;
import com.escape.model.NhnRankDate;
import com.escape.model.NhnRankDateExample;
import com.escape.model.NhnRankMonth;
import com.escape.model.NhnRankMonthExample;
import com.escape.model.NhnRankWeek;
import com.escape.model.NhnRankWeekExample;
import com.escape.model.NhnTimeDimension;

@Service
public class NhnServiceImpl implements NhnService {
	
	@Autowired
	private SqlSession sqlSession;

	public void addCategory(NhnCategory record) {
		final NhnCategoryMapper mapper = sqlSession.getMapper(NhnCategoryMapper.class);
		mapper.insert(record);
	}
	
	public NhnCategory getCategoryById(int cid) {
		final NhnCategoryMapper mapper = sqlSession.getMapper(NhnCategoryMapper.class);
		return mapper.selectByPrimaryKey(cid);
	}
	
	public List<NhnCategory> getCategoryAll() {
		final NhnCategoryMapper mapper = sqlSession.getMapper(NhnCategoryMapper.class);
		return mapper.selectByExample(null);
	}
	
	
	
	public void addTimeDimension(NhnTimeDimension record) {
		final NhnTimeDimensionMapper mapper = sqlSession.getMapper(NhnTimeDimensionMapper.class);
		mapper.insert(record);
	}

	public NhnTimeDimension getTimeDimensionByType(String type) {
		final NhnTimeDimensionMapper mapper = sqlSession.getMapper(NhnTimeDimensionMapper.class);
		return mapper.selectByPrimaryKey(type);
	}

	public List<NhnTimeDimension> getDimensionAll() {
		final NhnTimeDimensionMapper mapper = sqlSession.getMapper(NhnTimeDimensionMapper.class);
		return mapper.selectByExample(null);
	}

	public void addRankDate(NhnRankDate record) {
		final NhnRankDateMapper mapper = sqlSession.getMapper(NhnRankDateMapper.class);
		mapper.insertSelective(record);
	}
	
	public List<NhnRankDate> selectRankDateByDateTimeAndTitleAndCid(String dateTime,String title,int cid){
		final NhnRankDateMapper mapper = sqlSession.getMapper(NhnRankDateMapper.class);
		NhnRankDateExample example = new NhnRankDateExample();
		example.createCriteria().andDatetimeEqualTo(dateTime).andTitleEqualTo(title).andCidEqualTo(cid);
		return mapper.selectByExample(example);
	}

	public void addRankWeek(NhnRankWeek record) {
		final NhnRankWeekMapper mapper = sqlSession.getMapper(NhnRankWeekMapper.class);
		mapper.insertSelective(record);
	}
	public List<NhnRankWeek> selectRankWeekByDateTimeAndTitleAndCid(String dateTime,String title,int cid){
		final NhnRankWeekMapper mapper = sqlSession.getMapper(NhnRankWeekMapper.class);
		NhnRankWeekExample example = new NhnRankWeekExample();
		example.createCriteria().andDatetimeEqualTo(dateTime).andTitleEqualTo(title).andCidEqualTo(cid);
		return mapper.selectByExample(example);
	}

	public void addRankMonth(NhnRankMonth record) {
		final NhnRankMonthMapper mapper = sqlSession.getMapper(NhnRankMonthMapper.class);
		mapper.insertSelective(record);
	}
	
	public List<NhnRankMonth> selectRankMonthByDateTimeAndTitleAndCid(String dateTime,String title,int cid){
		final NhnRankMonthMapper mapper = sqlSession.getMapper(NhnRankMonthMapper.class);
		NhnRankMonthExample example = new NhnRankMonthExample();
		example.createCriteria().andDatetimeEqualTo(dateTime).andTitleEqualTo(title).andCidEqualTo(cid);
		return mapper.selectByExample(example);
	}
}
