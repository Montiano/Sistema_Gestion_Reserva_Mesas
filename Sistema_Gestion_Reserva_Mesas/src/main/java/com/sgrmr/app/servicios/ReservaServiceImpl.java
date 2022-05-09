package com.sgrmr.app.servicios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgrmr.app.modelos.Reserva;
import com.sgrmr.app.repositorios.ReservaRepository;

@Service
public class ReservaServiceImpl implements ReservaService{

	@Autowired
	private ReservaRepository reservaRepository;
	
	
	public List<Reserva> listAll(String fechaClave){
		if(fechaClave != null) {
			return reservaRepository.findAll(fechaClave);
		}
		return reservaRepository.findAll();
	}
	
//	public void save(Reserva reserva) {
//		reservaRepository.save(reserva);
//	}
	
	public Reserva get(Long id) {
		return reservaRepository.findById(id).get();
	}
	
//	public void delete(Long id) {
//		reservaRepository.deleteById(id);
//	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Reserva> findAll() {
		return (List<Reserva>) reservaRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Reserva> finalAll(Pageable pageable) {
		return reservaRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Reserva reserva) {
		reservaRepository.save(reserva);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Reserva findOne(Long id) {
		return reservaRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		reservaRepository.deleteById(id);
		
	}



}
