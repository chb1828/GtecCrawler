package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SkillsController {

	@RequestMapping(value="/skills")
	public String Skills()
	{
		return "skills";
	}
}
