package com.luv2code.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	

	//Se define el datasource de conexiones para la inyeccion de recursos
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Configuracion de impresion
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		

		//Obtiene la conexion de db
		try(Connection myConn= dataSource.getConnection();
				Statement myStmt= myConn.createStatement();
				ResultSet myRs= myStmt.executeQuery("select * from estudiante") ){
			
			//Procesa el resultado
			while (myRs.next()) {
				String nombre = myRs.getString("nombre");
				String apellido = myRs.getString("apellido");
				String email = myRs.getString("email");
				String escuela = myRs.getString("escuela");
				out.println(nombre+" "+apellido+" "+email+" "+escuela);
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}







