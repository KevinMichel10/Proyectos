package com.luv2code.springboot.cruddemo.dao;

import java.util.List;

import com.luv2code.springboot.cruddemo.entity.Estudiante;
//Quien te implemente debe definir el metodo findAll
//Quien quiera salvar debe definir el metodo save
//Quien quiera eliminar debe definir el metodo deleteById
//Se puede quitar el public
//Ejemplo de como estaba 	public List<Employee> findAll();public Employee findById(int theId);

public interface EstudianteDAO {

	List<Estudiante> findAll();
	
	Estudiante findById(int theId);
	
	void save(Estudiante theEstudiante);
	
	void deleteById(int theId);
	
}
