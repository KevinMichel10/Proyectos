package com.client.rest.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.client.rest.model.Estudiante;


@Service
public class EstudianteServiceRestClientImpl implements EstudianteService {

	private RestTemplate restTemplate;

	private String crmRestUrl;
		
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	public EstudianteServiceRestClientImpl(RestTemplate theRestTemplate, 
										@Value("${crm.rest.url}") String theUrl) {
		
		restTemplate = theRestTemplate;
		crmRestUrl = theUrl;
				
		logger.info("Loaded property:  crm.rest.url=" + crmRestUrl);
	}
	
	@Override
	public List<Estudiante> getEstudiante() {
		
		logger.info("***OBTENER LISTA DE ESTUDIANTE DESDE EL SERVICE REST CLIENTE");
		logger.info("in getEstudiante(): Calling REST API " + crmRestUrl);

		// make REST call
		ResponseEntity<List<Estudiante>> responseEntity = 
											restTemplate.exchange(crmRestUrl, HttpMethod.GET, null, 
													 new ParameterizedTypeReference<List<Estudiante>>() {});

		// get the list of customers from response
		List<Estudiante> estudiante = responseEntity.getBody();

		logger.info("in getEstudiante(): estudiante" + estudiante);
		
		return estudiante;
	}

	@Override
	public Estudiante getEstudiante(int theId) {
		logger.info("***OBTENER UN ESTUDIANTE DESDE EL SERVICE REST CLIENTE");

		logger.info("in getEstudiante(): Calling REST API " + crmRestUrl);

		// make REST call
		Estudiante theEstudiante = 
				restTemplate.getForObject(crmRestUrl + "/" + theId, 
										  Estudiante.class);

		logger.info("in saveEstudiante(): theEstudiante=" + theEstudiante);
		
		return theEstudiante;
	}

	@Override
	public void saveEstudiante(Estudiante theEstudiante) {

		logger.info("in saveEstudiante(): Calling REST API " + crmRestUrl);
		
		int estudianteId = theEstudiante.getId();

		// make REST call
		if (estudianteId == 0) {
			// add employee
			logger.info("***SALVAR UN ESTUDIANTE DESDE EL SERVICE REST CLIENTE");

			restTemplate.postForEntity(crmRestUrl, theEstudiante, String.class);			
		
		} else {
			// update employee
			logger.info("***ACTUALIZAR UN CLIENTE DESDE EL SERVICE REST CLIENTE");

			restTemplate.put(crmRestUrl, theEstudiante);
		}

		logger.info("in saveEstudiante(): success");	
	}

	@Override
	public void deleteEstudiante(int theId) {
		logger.info("***BORRAR UN ESTUDIANTE DESDE EL SERVICE REST CLIENTE");

		logger.info("in deleteEstudiante(): Calling REST API " + crmRestUrl);

		// make REST call
		restTemplate.delete(crmRestUrl + "/" + theId);

		logger.info("in deleteEstudiante(): deleted estudiante theId=" + theId);
	}

}
