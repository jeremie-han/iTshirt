package com.escape.controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.escape.common.CommonUtil;
import com.escape.model.Lotto;


@Controller
public class LottoController extends APIController {

	
	private static final String FILE_NAME = "C:/Users/harmy/Desktop/Windows/Temp/temp.xlsx";
	
	private static Logger logger = LoggerFactory.getLogger(ChurchMemberController.class);
	
	@RequestMapping("/lotto/upload.do")
	public void uploadLottoHistoryData(HttpServletRequest request, HttpServletResponse response){
		logger.info("LOTTO HISTORY DATA UPLOAD.....");
		List<Lotto> list = parseLottoHistory();
		
//		if(CommonUtil.valid(list)){
//			for (Lotto lotto : list) {
//				System.out.print("[ROUND="+lotto.getRound()+"]");
//				System.out.print("[LOTTERY DAY="+lotto.getLotteryDay()+"]");
//				System.out.print("[WIN_NUM1="+lotto.getWinNum1()+"]");
//				System.out.print("[WIN_NUM2="+lotto.getWinNum2()+"]");
//				System.out.print("[WIN_NUM3="+lotto.getWinNum3()+"]");
//				System.out.print("[WIN_NUM4="+lotto.getWinNum4()+"]");
//				System.out.print("[WIN_NUM5="+lotto.getWinNum5()+"]");
//				System.out.print("[WIN_NUM6="+lotto.getWinNum6()+"]");
//				System.out.print("[BONUS_NUM="+lotto.getBonusNum()+"]");
//				System.out.println();
//				
//				Lotto temp = lottoService.selectByPrimaryKey(lotto.getRound());
//				if(temp == null){
//					lottoService.insertSelective(lotto);
//				}
//			}
//		}
	}
	
	@SuppressWarnings("resource")
	private List<Lotto> parseLottoHistory(){
		Lotto lotto = null;
		List<Lotto> result = new ArrayList<Lotto>();
		DataFormatter formatter = new DataFormatter();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDate = sdf.format(new Date());
		
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		Map<Integer,Integer> Bonus = new HashMap<Integer,Integer>();
		for(int i = 1 ; i<= 45 ; i++){
			map.put(i, 0);
			System.out.println(i);
		}
		
		try {
			int num = 0;
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {
				lotto = new Lotto();
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					String value = formatter.formatCellValue(currentCell);
					switch (currentCell.getColumnIndex()) {
					case 0:
						lotto.setRound(Integer.parseInt(value));
						break;
					case 1:
						lotto.setLotteryDay(value);					
						break;
					case 2:
						lotto.setWinNum1(Integer.parseInt(value));
						num = map.get(Integer.parseInt(value));
						map.put(Integer.parseInt(value), num+1);
						
						break;
					case 3:
						lotto.setWinNum2(Integer.parseInt(value));
						
						num = map.get(Integer.parseInt(value));
						map.put(Integer.parseInt(value), num+1);
						break;
					case 4:
						lotto.setWinNum3(Integer.parseInt(value));
						
						num = map.get(Integer.parseInt(value));
						map.put(Integer.parseInt(value), num+1);
						break;
					case 5:
						lotto.setWinNum4(Integer.parseInt(value));
						
						num = map.get(Integer.parseInt(value));
						map.put(Integer.parseInt(value), num+1);
						
						break;
					case 6:
						lotto.setWinNum5(Integer.parseInt(value));
						
						num = map.get(Integer.parseInt(value));
						map.put(Integer.parseInt(value), num+1);
						break;
					case 7:
						lotto.setWinNum6(Integer.parseInt(value));
						
						num = map.get(Integer.parseInt(value));
						map.put(Integer.parseInt(value), num+1);
						break;
					case 8:
						lotto.setBonusNum(Integer.parseInt(value));
						
						num = Bonus.get(Integer.parseInt(value));
						Bonus.put(Integer.parseInt(value), num+1);
						break;
					default:
						
						break;
					}
				}
				lotto.setInsDate(currentDate);
				result.add(lotto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		
		// 방법3
        for( int key : map.keySet() ){
            System.out.println( String.format("키 : %s, 값 : %s", key, map.get(key)) );
        }

System.out.println("=============================");
        
     // 방법3
        for( int key : Bonus.keySet() ){
            System.out.println( String.format("키 : %s, 값 : %s", key, Bonus.get(key)) );
        }

		
		
		
		
		
		
		
		
		return result;
	}
}
