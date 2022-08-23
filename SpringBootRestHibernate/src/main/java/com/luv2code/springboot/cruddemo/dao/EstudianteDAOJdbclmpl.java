package com.luv2code.springboot.cruddemo.dao;

import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.luv2code.springboot.cruddemo.entity.Estudiante;

@Repository
public class EstudianteDAOJdbclmpl implements EstudianteDAO {

	@Autowired
	DataSource dataSource;

	@Override
	public List<Estudiante> findAll() {
		
		//Se imprime en consola para corroborar que funcione de manera correcta
		System.out.println("Implementación DAO con JDBC:" + dataSource);
		
		//Creacion de una lista de estudiantes
		List<Estudiante> listaEstudiante = new ArrayList<>();
		
		//Se implementa el try with resource para las conexiones 
		try (Connection myConn = dataSource.getConnection();
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery("select * from estudiante")) {

			// process result set
			while (myRs.next()) {

				// Se recuperan los datos de las filas de la bd
				int id = myRs.getInt("id");
				String nombre = myRs.getString("nombre");
				String apellido = myRs.getString("apellido");
				String email = myRs.getString("email");
				String escuela = myRs.getString("escuela");

				// Se crea un nuevo objeto estudiante
				Estudiante tempEstudiante = new Estudiante(id, nombre, apellido, email, escuela);

				// El objeto se agrega a la lista de estudiante
				listaEstudiante.add(tempEstudiante);

			}
			//Catch para las excepciones
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaEstudiante;
	}

	@Override
	public Estudiante findById(int theId) {
		
		//Se imprime en consola para corroborar que funcione de manera correcta
		System.out.println("Implementacion DAO con JDBC:" + dataSource);
		Estudiante theEstudiante = null;

		//Se implementa el try with resource para las conexiones 
		try (Connection myConn = dataSource.getConnection();
				PreparedStatement myStmt = myConn.prepareStatement("select * from estudiante where id=?")) {

			myStmt.setInt(1, theId);

			try (ResultSet myRs = myStmt.executeQuery()) {

				//Se recuperan los datos que coincidan con el Id ingresado
				if (myRs.next()) {
					String nombre = myRs.getString("nombre");
					String apellido = myRs.getString("apellido");
					String email = myRs.getString("email");
					String escuela = myRs.getString("escuela");

					theEstudiante = new Estudiante(theId, nombre, apellido, email, escuela);
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return theEstudiante;
	}

	@Override
	public void save(Estudiante theEstudiante) {
		//Se imprime en consola para corroborar que funcione de manera correcta
		System.out.println("Implementacion DAO con JDBC:");

		//Se implementa un if para poder seleccionar entre un insert o un update
		if (theEstudiante.getId() != 0) {
			
			//Se imprime en consola para saber que se realiza el update
			System.out.println("Implementacion update");
			
			//Se implementa el try with resource para las conexiones 
			try (Connection myConn = dataSource.getConnection();
					//se realiza el sql para insert
					PreparedStatement myStmt = myConn.prepareStatement(
							"update estudiante set nombre=?, apellido=?, email=?, escuela=? where id=?")) {
				
				// establecer los valores de parámetro para estudiante 
				myStmt.setString(1, theEstudiante.getNombre());
				myStmt.setString(2, theEstudiante.getApellido());
				myStmt.setString(3, theEstudiante.getEmail());
				myStmt.setString(4, theEstudiante.getEscuela());
				myStmt.setInt(5, theEstudiante.getId());
				
				//Se ejectua el update 
				myStmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			//Se imprime en consola para saber que se realiza el insert
			System.out.println("Implementacion insert");
			
			//Se implementa el try with resource para las conexiones 
			try (Connection myConn = dataSource.getConnection();
					PreparedStatement myStmt = myConn.prepareStatement("insert into estudiante "
							+ "(nombre, apellido, email, escuela) " + "values (?, ?, ?, ?)")) {
				
				// establecer los valores de parámetro para estudiante
				myStmt.setString(1, theEstudiante.getNombre());
				myStmt.setString(2, theEstudiante.getApellido());
				myStmt.setString(3, theEstudiante.getEmail());
				myStmt.setString(4, theEstudiante.getEscuela());

				//Se ejecuta el insert
				myStmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void deleteById(int theId) {
		
		//Se imprime en consola para corroborar que funcione de manera correcta
		System.out.println("Implementacion DAO con JDBC:");
		
		//Se implementa el try with resource para las conexiones 
		try (Connection myConn = dataSource.getConnection();
				PreparedStatement myStmt = myConn.prepareStatement("delete from estudiante where id=?")) {

			//Se establece el parametro
			myStmt.setInt(1, theId);
			
			//Se ejecuta el delete
			myStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
