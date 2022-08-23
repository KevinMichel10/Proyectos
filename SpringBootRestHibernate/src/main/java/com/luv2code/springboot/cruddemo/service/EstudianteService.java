package com.luv2code.springboot.cruddemo.service;

import java.util.List;

import com.luv2code.springboot.cruddemo.entity.Estudiante;
//Ve por la lista de empleado, salva el empleado, elimina
public interface EstudianteService {

	public List<Estudiante> findAll();
	
	public Estudiante findById(int theId);
	
	public void save(Estudiante theEstudiante);
	
	public void deleteById(int theId);
	
}
