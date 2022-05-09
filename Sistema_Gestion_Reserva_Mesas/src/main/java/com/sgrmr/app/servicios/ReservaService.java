package com.sgrmr.app.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sgrmr.app.modelos.Reserva;

public interface ReservaService {

	public List<Reserva> listAll(String fechaClave);
	
	public List<Reserva> findAll();
	
	public Page<Reserva> finalAll(Pageable pageable);
	
	public void save(Reserva reserva);
	
	public Reserva findOne(Long id);
	
	public void delete(Long id);


	
	//public List<Reserva> findAll(String fechaClave);
}
