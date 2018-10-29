package lxh.fw.demo.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lxh.fw.common.web.base.BaseController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/*")
public class TestController extends BaseController {

	// 登录页面
	private String loginView = "login";

	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("heres");
		ModelAndView mnv = new ModelAndView(this.loginView);
		return mnv;
	}

	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {

		try {
			ModelAndView mnv = new ModelAndView("main");
			return mnv;
		} catch (Exception e) {
			return sendJsonError(e.getLocalizedMessage(), e);
		}
	}
	
	
}
