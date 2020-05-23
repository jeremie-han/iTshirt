package com.escape.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.escape.common.CommonUtil;
import com.escape.common.Pager;
import com.escape.model.ChurchMember;
import com.escape.model.ChurchMemberExample;

@RestController
public class ChurchMemberController extends APIController {

	private static final String SAVE_PATH = "d:\\upload/";
	private static final String PREFIX_URL = "D:\\upload/";

	private static Logger logger = LoggerFactory.getLogger(ChurchMemberController.class);

	//@RequestMapping(value="/chMemList.do",method=RequestMethod.GET)
	@GetMapping(path="/chMemList.do")
	public ModelAndView getChurchMemberList(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("churchmember/chMemList");
	}

	@GetMapping(path="/chMemListTpl.do")
	public ModelAndView getChurchMemberListTpl(HttpServletRequest request,HttpServletResponse response) {
		final ModelAndView mav = new ModelAndView("churchmember/chMemListTpl");

		logger.info("############slf4j.Logger chMemListTpl....info");
		logger.debug("############slf4j.Logger chMemListTpl....debug");

		final String searchType = CommonUtil.ckNullStr(request.getParameter("searchType"));
		final String searchContent = CommonUtil.ckNullStr(request.getParameter("searchContent"));
		final String zcvYN = CommonUtil.ckNullStr(request.getParameter("zcv_yn"), "all");
		final String emailYN = CommonUtil.ckNullStr(request.getParameter("email_yn"), "all");

		int curPageNum = Integer.parseInt(CommonUtil.ckNullStr((String) request.getParameter("curPageNum"), "1"));
		int recordsPerPage = Integer.parseInt(CommonUtil.ckNullStr(request.getParameter("lines"),"20"));

		final Map<String, Object> param = new HashMap<String, Object>();
		if (CommonUtil.valid(searchContent)) {
			param.put(searchType, searchType);
			param.put("searchContent", searchContent);
		}

		if (CommonUtil.valid(zcvYN) && !"all".equals(zcvYN)) {
			param.put("zcvYN", zcvYN);
		}

		if (CommonUtil.valid(emailYN) && !"all".equals(emailYN)) {
			param.put("emailYN", emailYN);
		}

		int total_cnt = churchMemberService.getChurchMemberListCount(param);
		Pager pager = new Pager(total_cnt, curPageNum,recordsPerPage, 10);
		if (pager.totalPage < curPageNum) {
			curPageNum = 1;
			pager = new Pager(total_cnt, curPageNum, recordsPerPage,10);
		}
		param.put("offset", pager.startItem-1);
		param.put("limit", recordsPerPage);
		
		List<ChurchMember> list = churchMemberService.getChurchMemberList(param);
		
		logger.info("List size = {}",list.size());
		
		
		mav.addObject("list", list);		
		mav.addObject("pager", pager);
		mav.addObject("totalCnt", String.valueOf(total_cnt));
		return mav;
	}

	@GetMapping(path="/chuch/uploadFile.do")
	public ModelAndView uploadChurchFile(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("file1") MultipartFile multipartFile)
			throws Exception {
		String url = null;
		try {
			String originFilename = multipartFile.getOriginalFilename();
			String extName = originFilename;
			if (originFilename != null && originFilename.contains(".")) {
				extName = originFilename.substring(
						originFilename.lastIndexOf("."),
						originFilename.length());
			}
			Long size = multipartFile.getSize();

			String saveFileName = genSaveFileName(extName);
			System.out.println("originFilename : " + originFilename);
			System.out.println("extensionName : " + extName);
			System.out.println("size : " + size);
			System.out.println("saveFileName : " + saveFileName);
			writeFile(multipartFile, saveFileName);
			url = PREFIX_URL + saveFileName;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		System.out.println(url);
		ModelAndView mv = new ModelAndView("redirect:/chMemList.do");
		// sampleService.insertBoard(commandMap.getMap(), request);
		return mv;
	}

	private String genSaveFileName(String extName) {
		String fileName = "";

		Calendar calendar = Calendar.getInstance();
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += extName;

		return fileName;
	}

	private boolean writeFile(MultipartFile multipartFile, String saveFileName)
			throws IOException {
		boolean result = false;
		byte[] data = multipartFile.getBytes();
		FileOutputStream fos = new FileOutputStream(SAVE_PATH + "/"
				+ saveFileName);
		fos.write(data);
		fos.close();

		return result;
	}

	@RequestMapping("/church.do")
	public ModelAndView church(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("main");

		String churchName = "";
		String churchRoot = "";
		ChurchMember churchMember = null;
		List<ChurchMember> list = new ArrayList<>();

		try {
			final String filePath = "C:/Users/harmy/Desktop/Windows/Temp/harmy/churchlist.txt";
			File fileDir = new File(filePath);
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "euc-kr"));
			
			String str = "";
			while ((str = in.readLine()) != null) {
				if (str.trim().isEmpty())
					continue;
				System.out.println(str);
				if (str.contains("*")) {
					churchRoot = str.substring(1).trim();
					System.out.println("churchRoot=" + churchRoot);
				} else if (str.contains("/")) {
					String[] array = str.split("/");
					if (array.length < 2) {
						System.out.println("############Data format is wrong!"
								+ str);
						continue;
					}
					churchMember = new ChurchMember();
					churchMember.setEmail(array[0].trim());

					String name = array[1].trim();
					String[] array2 = name.split(" ");
					if (array2.length > 1) {
						churchMember.setChurchCategory(array2[1]);
						name = array2[0];
					}
					churchMember.setName(name);
					churchMember.setChurchRoot(churchRoot);
					churchMember.setChurchName(churchName);
					churchMember.setDelYn("N");
					churchMember.setSendEmailYn("N");
					churchMember.setSendZcYn("N");
					churchMember.setInsDate(new Date());
					churchMember.setUpdDate(new Date());
					list.add(churchMember);
				} else {
					churchName = str.trim();
				}
			}
			in.close();
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		int oldCnt = 0, newCnt = 0;
		for (ChurchMember churchMember2 : list) {
			try {
				List<ChurchMember> chMemlist = churchMemberService
						.selectByEmail(churchMember2.getEmail());
				if (chMemlist != null && chMemlist.size() > 0) {
					oldCnt++;
					System.out.print("[NAME=" + churchMember2.getName() + "]");
					System.out
							.print("[EMAIL=" + churchMember2.getEmail() + "]");
					System.out.print("[CHUACH NAME="
							+ churchMember2.getChurchName() + "]");
					System.out.print("[CHURCH ROOT="
							+ churchMember2.getChurchRoot() + "]");
					System.out.println();
				} else {
					churchMemberService.insert(churchMember2);
					newCnt++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}
		System.out.println("[NEW MEMBER=" + newCnt + "][OLD MEMBER=" + oldCnt
				+ "]");
		mav.addObject("serverTime", new Date());

		return new ModelAndView("redirect:/chMemList.do");
	}

	@RequestMapping("/church/excelDownload.do")
	public void excelDown(HttpServletResponse response) throws Exception {

		ChurchMemberExample example = new ChurchMemberExample();
		example.setOrderByClause("CHURCH_NAME");

		List<ChurchMember> list = churchMemberService.getChurchMemberList(example);

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("�Խ���");
		Row row = null;
		Cell cell = null;
		int rowNo = 0;
		CellStyle headStyle = wb.createCellStyle();
		headStyle.setBorderTop(BorderStyle.THIN);
		headStyle.setBorderBottom(BorderStyle.THIN);
		headStyle.setBorderLeft(BorderStyle.THIN);
		headStyle.setBorderRight(BorderStyle.THIN);

		headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
		headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		headStyle.setAlignment(HorizontalAlignment.CENTER);

		CellStyle bodyStyle = wb.createCellStyle();
		bodyStyle.setBorderTop(BorderStyle.THIN);
		bodyStyle.setBorderBottom(BorderStyle.THIN);
		bodyStyle.setBorderLeft(BorderStyle.THIN);
		bodyStyle.setBorderRight(BorderStyle.THIN);

		row = sheet.createRow(rowNo++);
		cell = row.createCell(0);
		cell.setCellStyle(headStyle);
		cell.setCellValue("No.");

		cell = row.createCell(1);
		cell.setCellStyle(headStyle);
		cell.setCellValue("�̸�");

		cell = row.createCell(2);
		cell.setCellStyle(headStyle);
		cell.setCellValue("����");

		cell = row.createCell(3);
		cell.setCellStyle(headStyle);
		cell.setCellValue("����");

		cell = row.createCell(4);
		cell.setCellStyle(headStyle);
		cell.setCellValue("��ȸ�̸�");

		cell = row.createCell(5);
		cell.setCellStyle(headStyle);
		cell.setCellValue("�Ҽӱ�ȸ");

		// ������ �κ� ����

		for (ChurchMember vo : list) {
			row = sheet.createRow(rowNo++);

			cell = row.createCell(0);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(String.valueOf(rowNo - 1));

			cell = row.createCell(1);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getName());

			cell = row.createCell(2);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getEmail());

			cell = row.createCell(3);
			cell.setCellStyle(bodyStyle);
			String catetory = vo.getChurchCategory();
			if (catetory == null) {
				catetory = "UnKnown";
			}
			cell.setCellValue(catetory);

			cell = row.createCell(4);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getChurchName());

			cell = row.createCell(5);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(vo.getChurchRoot());
		}

		response.setContentType("ms-vnd/excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=test.xls");

		wb.write(response.getOutputStream());
		wb.close();

	}
}
