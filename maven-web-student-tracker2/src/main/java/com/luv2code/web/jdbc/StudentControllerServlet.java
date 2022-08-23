package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentDbUtil;
	
	//@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// create our student db util ... and pass in the conn pool / datasource
		try {
			Context ctx = new InitialContext(); //USO DE JNDI
			dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/javatechie"); //USO DE JNDI
			System.out.println("Demo con JNDI, Datasource: "+dataSource);
			System.out.println(dataSource);
			studentDbUtil = new StudentDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//creacion de un try and catch para evitar excepciones
		try {
			// Se lee el parametro command que regresara un null
			String theCommand = request.getParameter("command");
			
		//si command se pierde, por default nos regresara lista de estudiantes
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			// Creacion del switch
			switch (theCommand) {
			
			case "LIST":
				listStudents(request, response);
				break;
				
			case "ADD":
				addStudent(request, response);
				break;
				
			case "LOAD":
				loadStudent(request, response);
				break;
				
			case "UPDATE":
				updateStudent(request, response);
				break;
			
			case "DELETE":
				deleteStudent(request, response);
				break;
				
			default:
				listStudents(request, response);
			}
				
		}

		catch (Exception exc) {
			throw new ServletException(exc);
		}
		
	}

	private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		//Se lee el id del estudiante que viene del form data
		String theStudentId = request.getParameter("studentId");
		
		// Se elimina estudiante de la base de datos
		studentDbUtil.deleteStudent(theStudentId);
		
		//Se envia de regreso a la lista
		listStudents(request, response);
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		//Se lee el id del estudiante que viene del form data
		int id = Integer.parseInt(request.getParameter("studentId"));
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String email = request.getParameter("email");
		String escuela = request.getParameter("escuela");
		
		// creacion de un nuevo objeto estudiante
		Student theStudent = new Student(id, nombre, apellido, email, escuela);
		
		// perform update on database
		studentDbUtil.updateStudent(theStudent);
		
		// send them back to the "list students" page
		listStudents(request, response);
		
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		// Lee estudiante id de form data
		String theStudentId = request.getParameter("studentId");
		
		// Obtiene estudiante de la base de datos
		Student theStudent = studentDbUtil.getStudent(theStudentId);
		

		// se asigna studiante en el atributo request
		request.setAttribute("THE_STUDENT", theStudent);
		
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);		
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//Se lee la informacion del estudiante de form data
		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String email = request.getParameter("email");		
		String escuela = request.getParameter("escuela");
		
		// Creacion de un nuevo objeto estudiante
		Student theStudent = new Student(nombre, apellido, email, escuela);
		
		// Agregar estudiante a la bd
		studentDbUtil.addStudent(theStudent);
				
		// send back to main page (the student list)
		listStudents(request, response);
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) 
		throws Exception {

		//Obtiene estudiante de db util
		List<Student> students = studentDbUtil.getStudents();
		

		//Agrega estudiante de request
		request.setAttribute("STUDENT_LIST", students);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);
	}

}













