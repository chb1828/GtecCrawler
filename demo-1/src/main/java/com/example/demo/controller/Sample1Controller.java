package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Sample1Controller {

	@RequestMapping(value="/sample1")
	public String Sample1()
	{
		return "sample1";
	}
}
