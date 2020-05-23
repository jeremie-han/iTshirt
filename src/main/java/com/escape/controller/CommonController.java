package com.escape.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.escape.batch.NHNTask;

@Controller
public class CommonController {

	
	@Autowired
	NHNTask nHNTask;
	
	@RequestMapping("/collectnhn.do")
	public ModelAndView getChurchMemberList(HttpServletRequest request,HttpServletResponse response) {
		try {
			nHNTask.collectNhnRankData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		final ModelAndView mav = new ModelAndView("churchmember/chMemList");
		return mav;
	}
}
