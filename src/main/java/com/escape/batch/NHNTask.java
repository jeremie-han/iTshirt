package com.escape.batch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.escape.common.CommonUtil;
import com.escape.model.NhnCategory;
import com.escape.model.NhnRankDate;
import com.escape.model.NhnRankMonth;
import com.escape.model.NhnRankWeek;
import com.escape.model.NhnTimeDimension;
import com.escape.service.NhnService;

@Component
public class NHNTask {
	
	@Autowired
	NhnService nhnService ;
	
	//@Scheduled(cron = "0 0/1 * * * *")
	public void collectNhnCategory() {
		
		System.out.println("collectNhnCategory start ("+ new Date()+")");
		
		try {
			Document doc = Jsoup.connect("https://datalab.naver.com/")
					.header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
					.header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
					.get();
			
			List<NhnCategory> categoryList = new ArrayList<NhnCategory>();
			Elements elements = doc.select("div.depth ul.scroll_cst li");
			for (Element element : elements) {
				Document doc2 = Jsoup.parseBodyFragment(element.html());
				String text = doc2.select(".option").attr("data-cid");
				if(text!= null && !text.isEmpty()){
					NhnCategory item = new NhnCategory();
					item.setCid(Integer.parseInt(text.trim()));
					item.setCname(element.text().trim());
					categoryList.add(item);
				}
			}
			if(categoryList != null && categoryList.size() > 0){
				for (NhnCategory item : categoryList) {
					System.out.println("CID = " + item.getCid());
					NhnCategory obj = nhnService.getCategoryById(item.getCid());
					if(obj == null){
						System.out.println("Can not find CID = " + item.getCid());
						nhnService.addCategory(item);
					}else{
						System.out.println("CID = " + item.getCid()+ " exists!");
					}
				}
			}
			
			List<NhnTimeDimension> timeDimList = new ArrayList<NhnTimeDimension>();
			elements = doc.select("div.period ul.scroll_cst li");
			for (Element element : elements) {
				Document doc2 = Jsoup.parseBodyFragment(element.html());
				String text = doc2.select(".option").attr("data-time-dimension");
				if(text!= null && !text.isEmpty()){
					NhnTimeDimension item = new NhnTimeDimension();
					item.setDimType(text);
					item.setDimName(element.text().trim());
					timeDimList.add(item);
				}
			}
			
			if(timeDimList != null && timeDimList.size() > 0){
				for (NhnTimeDimension item : timeDimList) {
					NhnTimeDimension obj = nhnService.getTimeDimensionByType(item.getDimType());
					if(obj == null){
						nhnService.addTimeDimension(item);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//collectNhnRankData();
		}
	}
	
	public void collectNhnRankData(){
		
		List<NhnCategory> categoryList = nhnService.getCategoryAll();
		List<NhnTimeDimension> timeDimList = nhnService.getDimensionAll();
		
		String dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
		
		List<Object> list = null;
		if(CommonUtil.valid(categoryList) && CommonUtil.valid(timeDimList)){
			for (NhnCategory  category : categoryList) {
				for (NhnTimeDimension dimension : timeDimList) {
					try {
						
					System.out.println("DIM_TYPE:"+dimension.getDimType() +", CID : "+ category.getCid());
						
					list = new ArrayList<Object>();
						Document doc = Jsoup.connect("https://datalab.naver.com/")
								.header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
								.header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
								.cookie("_datalab_cid", String.valueOf(category.getCid()))
								.data("timeUnit",dimension.getDimType()) //date , week , month
								.data("cid",String.valueOf(category.getCid()))
								.get();
						
						Elements tables = doc.select(".rank_title");
						Elements tables2 = doc.select("ul li a .title");
						Elements tables3 = doc.select("ul li a .num");
						
						for(int i = 0; i< tables.size() ; i++){
							Element table = tables.get(i);
							if(table.text().isEmpty()) break;
							System.out.println("[TITLE= "+table.text()+"]");
							for(int j=i*10 + 0; j<tables2.size() && j < i*10 + 10; j++){
								Element element = tables2.get(j);
								Element element3 = tables3.get(j);
								
								String content = table.text();
								if(CommonUtil.valid(content)){
									content = content.trim();
									if("month".equals(dimension.getDimType())){
										content = content.substring(0,7);
									}else{
										content = content.substring(0,10);
									}
									content = content.replace(".", "-");
								}
								
						    	if("date".equals(dimension.getDimType())){
									NhnRankDate item = new NhnRankDate();
									item.setCid(category.getCid());
									item.setSeq(Integer.parseInt(element3.text()));
									item.setDatetime(content);
									item.setTitle(element.text());
									item.setInsDate(dateTime);
									list.add(item);
								}else if("week".equals(dimension.getDimType())){
									NhnRankWeek item = new NhnRankWeek();
									item.setCid(category.getCid());
									item.setSeq(Integer.parseInt(element3.text()));
									item.setDatetime(content);
									item.setTitle(element.text());
									item.setInsDate(dateTime);
									list.add(item);
									
								}else if("month".equals(dimension.getDimType())){
									NhnRankMonth item = new NhnRankMonth();
									item.setCid(category.getCid());
									item.setSeq(Integer.parseInt(element3.text()));
									item.setDatetime(content);
									item.setTitle(element.text());
									item.setInsDate(dateTime);
									list.add(item);
								}
							}
						}
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					if(CommonUtil.valid(list)){
						for (Object object : list) {
							if("date".equals(dimension.getDimType())){
								NhnRankDate item = (NhnRankDate)object;
								List<NhnRankDate> history = nhnService.selectRankDateByDateTimeAndTitleAndCid(item.getDatetime(),item.getTitle(),item.getCid());
								if(!CommonUtil.valid(history)){
									nhnService.addRankDate(item);
								}
							}else if("week".equals(dimension.getDimType())){
								NhnRankWeek item = (NhnRankWeek)object;
								List<NhnRankWeek> history = nhnService.selectRankWeekByDateTimeAndTitleAndCid(item.getDatetime(),item.getTitle(),item.getCid());
								if(!CommonUtil.valid(history)){
									nhnService.addRankWeek(item);
								}
							}else if("month".equals(dimension.getDimType())){
								NhnRankMonth item = (NhnRankMonth)object;
								List<NhnRankMonth> history = nhnService.selectRankMonthByDateTimeAndTitleAndCid(item.getDatetime(),item.getTitle(),item.getCid());
								if(!CommonUtil.valid(history)){
									nhnService.addRankMonth(item);
								}
							}
						}
					}
				}
			}
		}
	}
}
