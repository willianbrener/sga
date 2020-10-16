package br.com.sga.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sga.core.model.Region;
import br.com.sga.web.constants.ControllerMapping;

@Controller
@RequestMapping("/template")
public class TemplateController {
	
	@GetMapping("/dashboard1")
	public ModelAndView dashboard1(HttpServletRequest request) {
		return new ModelAndView("pages/template/dashboard1");
	}

	@GetMapping("/dashboard2")
	public ModelAndView dashboard2(HttpServletRequest request) {
		return new ModelAndView("pages/template/dashboard2");
	}

	@GetMapping("/dashboard3")
	public ModelAndView dashboard3(HttpServletRequest request) {
		return new ModelAndView("pages/template/dashboard3");
	}

	@GetMapping("/ui-elements")
	public ModelAndView uiElements(HttpServletRequest request) {
		return new ModelAndView("pages/template/ui-elements");
	}

	@GetMapping("/buttons")
	public ModelAndView buttons(HttpServletRequest request) {
		return new ModelAndView("pages/template/buttons");
	}

	@GetMapping("/widgets")
	public ModelAndView widgets(HttpServletRequest request) {
		return new ModelAndView("pages/template/widgets");
	}

	@GetMapping("/profile")
	public ModelAndView profile(HttpServletRequest request) {
		return new ModelAndView("pages/template/profile");
	}
}