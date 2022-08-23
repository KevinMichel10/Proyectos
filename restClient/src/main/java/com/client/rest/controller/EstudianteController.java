package com.client.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.client.rest.model.Estudiante;
import com.client.rest.service.EstudianteService;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

	// need to inject our customer service
	@Autowired
	private EstudianteService estudianteService;
	
	@GetMapping("/list")
	public String listEstudiante(Model theModel) {
		
		// get customers from the service
		List<Estudiante> theEstudiante = estudianteService.getEstudiante();
				
		// add the customers to the model
		theModel.addAttribute("estudiante", theEstudiante);
		
		return "list-estudiante";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Estudiante theEstudiante = new Estudiante();
		
		theModel.addAttribute("estudiante", theEstudiante);
		
		return "estudiante-form";
	}
	
	@PostMapping("/saveEstudiante")
	public String saveEstudiante(@ModelAttribute("estudiante") Estudiante theEstudiante) {
		
		// save the customer using our service
		estudianteService.saveEstudiante(theEstudiante);	
		
		return "redirect:/estudiante/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("estudianteId") int theId,
									Model theModel) {
	
		// get the customer from our service
		Estudiante theEstudiante = estudianteService.getEstudiante(theId);	
		
		// set customer as a model attribute to pre-populate the form
		theModel.addAttribute("estudiante", theEstudiante);
		
		// send over to our form		
		return "estudiante-form";
	}
	
	@GetMapping("/delete")
	public String deleteEstudiante(@RequestParam("estudianteId") int theId) {
		
		// delete the customer
		estudianteService.deleteEstudiante(theId);
		
		return "redirect:/estudiante/list";
	}
}










