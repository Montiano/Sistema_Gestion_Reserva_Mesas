package com.sgrmr.app.util.seguridad;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

	public static void main(String[] args) {
		
		BCryptPasswordEncoder encoderAdmin = new BCryptPasswordEncoder();
		String rawPasswordAdmin = "admin";
		String encodedPasswordAdmin = encoderAdmin.encode(rawPasswordAdmin);
		
		System.out.println(encodedPasswordAdmin);
		
		BCryptPasswordEncoder encoderEmpleado = new BCryptPasswordEncoder();
		String rawPasswordEmpleado = "empleado";
		String encodedPasswordEmpleado = encoderEmpleado.encode(rawPasswordEmpleado);
		
		System.out.println(encodedPasswordEmpleado);
	}
}
