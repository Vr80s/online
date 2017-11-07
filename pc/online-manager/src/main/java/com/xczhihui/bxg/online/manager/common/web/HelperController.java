package com.xczhihui.bxg.online.manager.common.web;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class HelperController {
	private StringTrimmerEditor stringTrimmer = new StringTrimmerEditor(true);

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, stringTrimmer);
	}
}