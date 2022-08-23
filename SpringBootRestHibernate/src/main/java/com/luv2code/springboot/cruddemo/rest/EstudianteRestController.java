package com.luv2code.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.entity.Estudiante;
import com.luv2code.springboot.cruddemo.service.EstudianteService;

@RestController
@RequestMapping("/api")
public class EstudianteRestController {
	//Se inyecta el contolor en la capa de servicios
	private EstudianteService estudianteService;
	
	@Autowired
	public EstudianteRestController(EstudianteService theEstudianteService) {
		estudianteService = theEstudianteService;
	}
	
	// expose "/employees" and return list of employees
	@GetMapping("/estudiante")
	public List<Estudiante> findAll() {
		return estudianteService.findAll();
	}

	// add mapping for GET /employees/{employeeId}
	
	@GetMapping("/estudiante/{estudianteId}")
	public Estudiante getEstudiante(@PathVariable int estudianteId) {
		
		Estudiante theEstudiante = estudianteService.findById(estudianteId);
		
		if (theEstudiante == null) {
			throw new RuntimeException("Estudiante id not found - " + estudianteId);
		}
		
		return theEstudiante;
	}
	
	// add mapping for POST /employees - add new employee
	
	@PostMapping("/estudiante")
	public Estudiante addEstudiante(@RequestBody Estudiante theEstudiante) {
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
		
		theEstudiante.setId(0);
		
		estudianteService.save(theEstudiante);
		
		return theEstudiante;
	}
	
	// add mapping for PUT /employees - update existing employee
	
	@PutMapping("/estudiante")
	public Estudiante updateEstudiante(@RequestBody Estudiante theEstudiante) {
		
		estudianteService.save(theEstudiante);
		
		return theEstudiante;
	}
	
	// add mapping for DELETE /employees/{employeeId} - delete employee
	
	@DeleteMapping("/estudiante/{estudianteId}")
	public String deleteEstudiante(@PathVariable int estudianteId) {
		
		Estudiante tempEstudiante = estudianteService.findById(estudianteId);
		
		// throw exception if null
		
		if (tempEstudiante == null) {
			throw new RuntimeException("Estudiante id not found - " + estudianteId);
		}
		
		estudianteService.deleteById(estudianteId);
		
		return "Deleted estudiante id - " + estudianteId;
	}
	
}










