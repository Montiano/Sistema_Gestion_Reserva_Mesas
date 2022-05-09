package com.sgrmr.app.repositorios;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sgrmr.app.modelos.Empleado;

public interface EmpleadoRepository extends PagingAndSortingRepository<Empleado, Long>{

}
