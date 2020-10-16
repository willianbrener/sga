package br.com.sga.web.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.DocumentException;

import br.com.sga.core.model.Location;
import br.com.sga.core.service.LocationService;
import br.com.sga.core.validator.input.LocationInputValidator;
import br.com.sga.web.constants.ControllerMapping;
import br.com.sga.web.support.Pagination;

@Controller
@RequestMapping("/location")
public class LocationController {
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private LocationInputValidator locationInputValidator;
	
	@GetMapping
	public ModelAndView locationList(Location location, @PageableDefault Pageable pageable, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("pages/location/location-list");

		Pagination<Location> page = new Pagination<>(locationService.findAll(location, pageable), request);
		
		mv.addObject("page", page);
		
		return mv;
	}

	@GetMapping("/form")
	public ModelAndView locationForm(Location location) {
		ModelAndView mv = new ModelAndView("pages/location/location-form");
		
		return mv;
	}
	
	@PostMapping("/save")
	public ModelAndView save(Location location, BindingResult result, RedirectAttributes redirectAttributes) {
		
		this.locationInputValidator.validateSave(location, result);
		
		if (!result.hasErrors()) {
			
			this.locationService.save(location);
			
			ModelAndView mv = new ModelAndView("redirect:/location");
			redirectAttributes.addFlashAttribute(ControllerMapping.FORM_SUCCESS_MESSAGE_KEY, "Location salva com sucesso!");
			return mv;
		} else {
			return locationForm(location);
		}
	}
	
	@GetMapping("/form/{id}")
	public ModelAndView edit(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("pages/location/location-form");
		
		Location location = this.locationService.findById(id);
		
		mv.addObject("location", location);
		
		return mv;
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		
		Location location = this.locationService.findById(id);
		
		this.locationService.delete(location);
		
		ModelAndView mv = new ModelAndView("redirect:/location");
		
		redirectAttributes.addFlashAttribute(ControllerMapping.FORM_SUCCESS_MESSAGE_KEY, "Location excluída com sucesso!");
		
		return mv;
	}
	
	@PostMapping("/export-excel")
	public void locationGenerateExcel(Location locationFilter, HttpServletRequest request, HttpServletResponse response) throws IOException {
		InputStream is = this.locationService.generateLocationExcel(locationFilter);
		
		IOUtils.copy(is, response.getOutputStream());
		
		response.setContentType("application/x-xls");
		response.setHeader("Content-disposition", "attachment; filename=" + "resultado.xls"); 
		
		is.close();
		
		response.flushBuffer();
	}
	
	@PostMapping("/export-pdf")
	public void locationGeneratePdf(Location locationFilter, HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
		InputStream is = this.locationService.generateLocationPdf(locationFilter);
		
		IOUtils.copy(is, response.getOutputStream());
		
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=" + "resultado.pdf"); 
		
		is.close();
		
		response.flushBuffer();
	}
	
//	@PostMapping("/delete/{id}")
//	public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//		
//		Location location = this.locationService.findById(id);
//		
//		this.locationService.delete(location);
//
//		return "location/location-list :: fragment-table-content";
//	}
}