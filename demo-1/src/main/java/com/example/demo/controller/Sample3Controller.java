package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Sample3Controller {

	@RequestMapping(value="/sample3")
	public String Sample3()
	{
		return "sample3";
	}
}
