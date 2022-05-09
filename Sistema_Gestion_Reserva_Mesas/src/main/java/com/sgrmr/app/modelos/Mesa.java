package com.sgrmr.app.modelos;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mesas")
public class Mesa implements Serializable{

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Atributos
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	
	
	/**
	 * Constructores
	 */
	public Mesa() {
		super();
	}
	public Mesa(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	/**
	 * Getters & Setters
	 * @return
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Método toString
	 */
	@Override
	public String toString() {
		return id.toString();
	}
	
	
	
}
