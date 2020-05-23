package com.escape.service;

import java.util.List;

import com.escape.model.NhnCategory;
import com.escape.model.NhnRankDate;
import com.escape.model.NhnRankMonth;
import com.escape.model.NhnRankWeek;
import com.escape.model.NhnTimeDimension;

public interface NhnService {
	
	public void addCategory(NhnCategory record);
	
	public NhnCategory getCategoryById(int cid);
	
	public List<NhnCategory> getCategoryAll();
	
	
	public void addTimeDimension(NhnTimeDimension record);
	
	public NhnTimeDimension getTimeDimensionByType(String type);
	
	public List<NhnTimeDimension> getDimensionAll();
	
	
	public void addRankDate(NhnRankDate record);
	public List<NhnRankDate> selectRankDateByDateTimeAndTitleAndCid(String dateTime,String title,int cid);
	
	public void addRankWeek(NhnRankWeek record);
	public List<NhnRankWeek> selectRankWeekByDateTimeAndTitleAndCid(String dateTime,String title,int cid);
	
	public void addRankMonth(NhnRankMonth record);
	public List<NhnRankMonth> selectRankMonthByDateTimeAndTitleAndCid(String dateTime,String title,int cid);
}
