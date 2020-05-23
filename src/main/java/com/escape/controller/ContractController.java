package com.escape.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.escape.service.ChurchMemberService;
import com.escape.service.ContractService;

@Controller
public class ContractController {

	//private Log log = LogFactory.getLog(this.getClass());
	
	private Logger logger = LoggerFactory.getLogger(ContractController.class);

	@Autowired
	private ContractService contractService = null;

	@Autowired
	private ChurchMemberService churchMemberService = null;
	
	@RequestMapping("/index.do")
	public ModelAndView welcome() {
		ModelAndView mav = new ModelAndView("churchmember/cardList");

		logger.info("##### Welcome to natural project #####");

		contractService.addContract();
		
		

		mav.addObject("serverTime", new Date());

		return mav;
	}

	
}
