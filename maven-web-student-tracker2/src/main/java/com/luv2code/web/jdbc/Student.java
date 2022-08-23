package com.luv2code.web.jdbc;

public class Student {

	private int id;
	private String nombre;
	private String apellido;
	private String email;
	private String escuela;

	public Student(String nombre, String apellido, String email, String escuela) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.escuela = escuela;
	}

	public Student(int id, String nombre, String apellido, String email, String escuela) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.escuela = escuela;
	}
	
	//Generacion de getter y setter 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEscuela() {
		return escuela;
	}

	public void setEscuela(String escuela) {
		this.escuela = escuela;
	}

	@Override
	public String toString() {
		return "Estudiante [id=" + id + ", Nombre=" + nombre +
				", Apellido=" + apellido + ", email=" + email +
				", escuela=" + escuela + "]";
	}	
}
