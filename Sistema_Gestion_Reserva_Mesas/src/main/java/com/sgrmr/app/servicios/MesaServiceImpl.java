package com.sgrmr.app.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sgrmr.app.modelos.Mesa;
import com.sgrmr.app.repositorios.MesaRepository;

@Service
public class MesaServiceImpl implements MesaService{

	@Autowired
	private MesaRepository mesaRepository;
	
	@Override
	public List<Mesa> listaMesas() {
		return (List<Mesa>) mesaRepository.findAll();
	}

}
