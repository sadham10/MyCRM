package com.hussain.mycrm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyController {
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public String getIndex(){
		return "index";
	}
	
	@RequestMapping(value="/error",method=RequestMethod.GET)
	public String getError(){
		return "index";
	}
	

}
