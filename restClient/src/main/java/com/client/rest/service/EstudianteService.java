package com.client.rest.service;

import java.util.List;
import com.client.rest.model.Estudiante;

public interface EstudianteService {

	public List<Estudiante> getEstudiante();

	public void saveEstudiante(Estudiante theEstudiante);

	public Estudiante getEstudiante(int theId);

	public void deleteEstudiante(int theId);
	
}
