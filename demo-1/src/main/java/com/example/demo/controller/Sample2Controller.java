package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Sample2Controller {

	@RequestMapping(value="/sample2")
	public String Sample2()
	{
		return "sample2";
	}
}
