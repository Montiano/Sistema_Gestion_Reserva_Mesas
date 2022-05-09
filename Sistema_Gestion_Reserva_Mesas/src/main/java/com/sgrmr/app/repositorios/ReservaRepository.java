package com.sgrmr.app.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sgrmr.app.modelos.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long>{
	
	@Query("Select r From Reserva r Where r.cliente Like %?1% Or r.fecha Like %?1% Order by r.hora")
	public List<Reserva> findAll(String fechaClave);
	
//	@Query("Select r From Reserva r Where r.cliente Like %?1%"
//			+ " Or r.comedor Like %?1%"
//			+ " Or r.fecha Like %?1%"
//			+ " Or r.telefono Like %?1% Order by r.hora")
//	public List<Reserva> findAll(String palabraClave);
}
