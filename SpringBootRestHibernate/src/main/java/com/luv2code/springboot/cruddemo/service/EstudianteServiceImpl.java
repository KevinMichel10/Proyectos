package com.luv2code.springboot.cruddemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springboot.cruddemo.dao.EstudianteDAO;
import com.luv2code.springboot.cruddemo.entity.Estudiante;

@Service
public class EstudianteServiceImpl implements EstudianteService {
	
	//Inyecta el empleado con Dao
	private EstudianteDAO estudianteDAO;
	
	@Autowired
	public EstudianteServiceImpl(@Qualifier("estudianteDAOHibernateImpl") EstudianteDAO theEstudianteDAO) {
		estudianteDAO = theEstudianteDAO;
	}
	
	@Override
	@Transactional
	public List<Estudiante> findAll() {
		return estudianteDAO.findAll();
	}

	@Override
	@Transactional
	public Estudiante findById(int theId) {
		return estudianteDAO.findById(theId);
	}

	@Override
	@Transactional
	public void save(Estudiante theEstudiante) {
		estudianteDAO.save(theEstudiante);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		estudianteDAO.deleteById(theId);
	}



}






