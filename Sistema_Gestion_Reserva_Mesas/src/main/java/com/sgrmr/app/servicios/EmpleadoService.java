package com.sgrmr.app.servicios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sgrmr.app.modelos.Empleado;

public interface EmpleadoService {

	public List<Empleado> findAll();
	
	public Page<Empleado> finalAll(Pageable pageable);
	
	public void save(Empleado empleado);
	
	public Empleado findOne(Long id);
	
	public void delete(Long id);
}
