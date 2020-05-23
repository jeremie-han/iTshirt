package com.escape.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GMapController {
	
	
	@RequestMapping("gmap.do")
	public ModelAndView viewGoogleMap(HttpServletRequest request, HttpServletResponse response){
		final ModelAndView mav = new ModelAndView("google/googlemap");
		
		return mav;
	}

}
