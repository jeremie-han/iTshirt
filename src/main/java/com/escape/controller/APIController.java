package com.escape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.escape.service.ChurchMemberService;
import com.escape.service.LottoService;

@Controller
public class APIController {

	@Autowired
	protected LottoService lottoService;

	@Autowired
	protected ChurchMemberService churchMemberService;

}
