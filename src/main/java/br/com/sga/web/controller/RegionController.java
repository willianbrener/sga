package br.com.sga.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.sga.core.model.Region;
import br.com.sga.core.service.RegionService;
import br.com.sga.core.validator.input.RegionInputValidator;
import br.com.sga.web.constants.ControllerMapping;
import br.com.sga.web.support.Pagination;

@Controller
@RequestMapping("/region")
public class RegionController {
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private RegionInputValidator regionInputValidator;
	
	@GetMapping
	public ModelAndView regionList(@PageableDefault Pageable pageable, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pages/region/region-list");

		Pagination<Region> page = new Pagination<>(regionService.findAll(pageable), request);
		
		mv.addObject("page", page);
		
		return mv;
	}

	@GetMapping("/form")
	public ModelAndView regionForm(Region region) {
		ModelAndView mv = new ModelAndView("pages/region/region-form");
		
		return mv;
	}
	
	@PostMapping("/save")
	public ModelAndView save(Region region, BindingResult result, RedirectAttributes redirectAttributes) {
		
		this.regionInputValidator.validateSave(region, result);
		
		if (!result.hasErrors()) {
			
			this.regionService.save(region);
			
			ModelAndView mv = new ModelAndView("redirect:/region");
			redirectAttributes.addFlashAttribute(ControllerMapping.FORM_SUCCESS_MESSAGE_KEY, "Region salva com sucesso!");
			return mv;
		} else {
			return regionForm(region);
		}
	}
	
	@GetMapping("/form/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("pages/region/region-form");
		
		Region region = this.regionService.findById(id);
		
		mv.addObject("region", region);
		
		return mv;
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		
		Region region = this.regionService.findById(id);
		
		this.regionService.delete(region);
		
		ModelAndView mv = new ModelAndView("redirect:/region");
		
		redirectAttributes.addFlashAttribute(ControllerMapping.FORM_SUCCESS_MESSAGE_KEY, "Region excluída com sucesso!");
		
		return mv;
	}
	
	@GetMapping("/modal-list")
	public String modalList(@PageableDefault Pageable pageable, Region regionFilter, Model model, BindingResult bindingResult, HttpServletRequest request) {

		Pagination<Region> page = new Pagination<>(regionService.findAll(regionFilter, pageable), request);
		
		model.addAttribute("pageRegion", page);

		return "pages/region/region-modal-list :: tabela-pesquisa";
	}
	
//	@PostMapping("/delete/{id}")
//	public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//		
//		Region region = this.regionService.findById(id);
//		
//		this.regionService.delete(region);
//
//		return "region/region-list :: fragment-table-content";
//	}
}