package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {

	private DataSource dataSource;

	public StudentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Student> getStudents() throws Exception {
		
		List<Student> students = new ArrayList<>();
		
		
		try(Connection myConn=dataSource.getConnection();
				Statement myStmt=myConn.createStatement();
				ResultSet myRs= myStmt.executeQuery("select * from estudiante")) {
			// procesa el conjunto de resultados
			while (myRs.next()) {
				
				// obtiene los datos de las filas de db
				int id = myRs.getInt("id");
				String nombre = myRs.getString("nombre");
				String apellido = myRs.getString("apellido");
				String email = myRs.getString("email");
				String escuela = myRs.getString("escuela");
				
				// Creacion de un nuevo obejto estudiante
				Student tempStudent = new Student(id, nombre, apellido, email, escuela);
				
				// Se agrega a la lista de estudiantes
				students.add(tempStudent);				
			}
			
			return students;		
		}
		
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		//Se agrega un try para cerrar conexiones 
		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();  
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	public void addStudent(Student theStudent) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			//Obtiene la conexion de bd
			myConn = dataSource.getConnection();
			
			// Creacion de un sql insert
			String sql = "insert into estudiante "
					   + "(nombre, apellido, email, escuela) "
					   + "values (?, ?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// establece los valores para estudiante 
			myStmt.setString(1, theStudent.getNombre());
			myStmt.setString(2, theStudent.getApellido());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.setString(4, theStudent.getEscuela());
			
			// ejecuta in insert en sql
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}	
	}

	public Student getStudent(String theStudentId) throws Exception {

		Student theStudent = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int studentId;
		
		try {
			//Convierte id estudiante a int
			studentId = Integer.parseInt(theStudentId);
			
			// Obtiene la conexion a bd
			myConn = dataSource.getConnection();
			
			// crea un sql para obtener el estudiante 
			//mediante su id
			String sql = "select * from estudiante where id=?";
			
			// crear un statement
			myStmt = myConn.prepareStatement(sql);
			
			// establecer parametros
			myStmt.setInt(1, studentId);
			
			// ejecuta statement
			myRs = myStmt.executeQuery();
			
			// recuperar los datos de la fila
			if (myRs.next()) {
				String nombre = myRs.getString("nombre");
				String apellido = myRs.getString("apellido");
				String email = myRs.getString("email");
				String escuela = myRs.getString("escuela");
				
				// usar el id de estudiante durante su construccion
				theStudent = new Student(studentId, nombre, apellido, email, escuela);
			}
			else {
				throw new Exception("Could not find student id: " + studentId);
			}				
			
			return theStudent;
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}	
	}

	public void updateStudent(Student theStudent) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// obetener la conexion de db
			myConn = dataSource.getConnection();
			
			// crea un sql para actualizacion de datos
			String sql = "update estudiante "
						+ "set nombre=?, apellido=?, email=?, escuela=? "
						+ "where id=?";
			
			// prepara statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setString(1, theStudent.getNombre());
			myStmt.setString(2, theStudent.getApellido());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.setString(4, theStudent.getEscuela());
			myStmt.setInt(5, theStudent.getId());
			
			// ejecuta sql statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}	
		
	}

	public void deleteStudent(String theStudentId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// Convierte el id de estudiante en int
			int studentId = Integer.parseInt(theStudentId);
			
			// get connection to database
			// Obten la conexion a bd
			myConn = dataSource.getConnection();
			

			//Crea un sql para eliminar estudiante
			String sql = "delete from estudiante where id=?";
			
			// prepara statement
			myStmt = myConn.prepareStatement(sql);
			
			// establece parametros
			myStmt.setInt(1, studentId);
			
			// ejecuta sql statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC code
			close(myConn, myStmt, null);
		}
	
	}
}















