package com.sgrmr.app.modelos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;



@Entity
@Table(name="reservas")
public class Reserva implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String cliente;

	@NotEmpty
	@Pattern(regexp="[0-9]{9}")
	private String telefono;

	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fecha;
	
	@NotNull
	//@DateTimeFormat(pattern = "hh:mm")
	private LocalTime hora;
	
	@NotBlank
	private String comedor;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="mesa")
	private Mesa mesa;
	
	@NotNull
	private int comensales;

	private String peticionEspecial;

	
	public Reserva() {
		super();
	}


	public Reserva(Long id, @NotEmpty String cliente, @NotEmpty String telefono, @NotNull LocalDate fecha,
			@NotNull LocalTime hora, @NotEmpty String comedor, @NotNull Mesa mesa, @NotNull int comensales,
			String peticionEspecial) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.telefono = telefono;
		this.fecha = fecha;
		this.hora = hora;
		this.comedor = comedor;
		this.mesa = mesa;
		this.comensales = comensales;
		this.peticionEspecial = peticionEspecial;
	}





	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCliente() {
		return cliente;
	}


	public void setCliente(String cliente) {
		this.cliente = cliente;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public LocalDate getFecha() {
		return fecha;
	}


	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}


	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public String getComedor() {
		return comedor;
	}


	public void setComedor(String comedor) {
		this.comedor = comedor;
	}


	public Mesa getMesa() {
		return mesa;
	}


	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}


	public int getComensales() {
		return comensales;
	}


	public void setComensales(int comensales) {
		this.comensales = comensales;
	}


	public String getPeticionEspecial() {
		return peticionEspecial;
	}


	public void setPeticionEspecial(String peticionEspecial) {
		this.peticionEspecial = peticionEspecial;
	}


	
}
