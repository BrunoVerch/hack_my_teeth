package com.mgmsec.HackMyTeeth.HackMyTeeth.controllers;

import java.time.Clock;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import com.mgmsec.HackMyTeeth.HackMyTeeth.model.User;
import com.mgmsec.HackMyTeeth.HackMyTeeth.model.Session;

import com.mgmsec.HackMyTeeth.HackMyTeeth.service.UserService;
import com.mgmsec.HackMyTeeth.HackMyTeeth.service.SessionService;
import com.mgmsec.HackMyTeeth.HackMyTeeth.setting.SecuritySettings;

import com.mgmsec.HackMyTeeth.HackMyTeeth.setting.SecurityEnum.*;
@Controller
//@Scope("session")
public class SettingController {
	@Autowired
	UserService userService;
	@Autowired
	SessionService sessService;
	@Autowired
	SecuritySettings secSettings;
	@RequestMapping(value = "/settingVal",method = RequestMethod.POST)
	public ModelAndView settingVal(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView();
		UseCookie useCookie = UseCookie.Base64;
		CookieParam cookieParam= CookieParam.True;
		SessFix sessFix= SessFix.Yes;
		switch(request.getParameter("UseCookie")) {
		case "Base64":
			useCookie= UseCookie.Base64;
			break;
		case "Secure":
			useCookie = UseCookie.Secure;
			break;
		}
		switch(request.getParameter("CookieParam")) {
		case "True":
			cookieParam = CookieParam.True;
			break;
		case "False":
			cookieParam = CookieParam.False;
			break;
		}
		switch(request.getParameter("SessFix")) {
		case "Yes":
			sessFix = SessFix.Yes;
			break;
		case "No":
			sessFix = SessFix.No;
			break;
		}
		secSettings.setCookParam(cookieParam);
		secSettings.setUseCookie(useCookie);
		secSettings.setSessFix(sessFix);
		if(sessService.deleteAllSession()) {
			modelAndView.addObject("errorMessage","ALl Cookies in DB are deleted!");
		}
		modelAndView.setViewName("/login");
		return modelAndView;
	}
	@RequestMapping(value = "/setting",method = RequestMethod.GET)
	public ModelAndView setting() {
		return new ModelAndView("setting");
	}
}