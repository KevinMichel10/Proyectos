package com.luv2code.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.luv2code.springboot.cruddemo.entity.Estudiante;


@Repository
public class EstudianteDAOHibernateImpl implements EstudianteDAO {

	// define field for entitymanager	
	private EntityManager entityManager;
		
	// set up constructor injection
	@Autowired
	public EstudianteDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	
	@Override
	public List<Estudiante> findAll() {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create a query
		Query<Estudiante> theQuery =
				currentSession.createQuery("from Estudiante", Estudiante.class);//Traer todos los registros de la tabla empleado
		
		// execute query and get result list
		List<Estudiante> estudiantes = theQuery.getResultList();
		
		// return the results		
		return estudiantes;
	}


	@Override
	public Estudiante findById(int theId) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// get the employee
		Estudiante theEstudiante =
				currentSession.get(Estudiante.class, theId);
		
		// return the employee
		return theEstudiante;
	}


	@Override
	public void save(Estudiante theEstudiante) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// save employee
		currentSession.saveOrUpdate(theEstudiante);//Guardar o actualizar un registro con Hibernate
	}


	@Override
	public void deleteById(int theId) {
		
		// get the current hibernate session recupera la session
		Session currentSession = entityManager.unwrap(Session.class);
				
		// delete object with primary key
		Query theQuery = 
				currentSession.createQuery(
						"delete from Estudiante where id=:estudianteId");
		theQuery.setParameter("estudianteId", theId);//ELimina el id que se paso como parametro
		
		theQuery.executeUpdate();//Ejecuta con el comando de hibernate
	}




}







