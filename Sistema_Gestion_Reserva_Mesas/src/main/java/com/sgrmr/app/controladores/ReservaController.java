package com.sgrmr.app.controladores;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;
import com.sgrmr.app.modelos.Mesa;
import com.sgrmr.app.modelos.Reserva;
import com.sgrmr.app.servicios.MesaService;
import com.sgrmr.app.servicios.ReservaServiceImpl;
import com.sgrmr.app.util.reportes.ReservaExporterExcel;
import com.sgrmr.app.util.reportes.ReservaExporterPDF;

@Controller
public class ReservaController {

	@Autowired
	private ReservaServiceImpl reservaService;

	@Autowired
	private MesaService mesaService;
	
	private String fechaClave;

	
	@GetMapping({"","/","/lista_reservas"})
	public String listarReservas (Model modelo, @Param("fechaClave")String fechaClave) {
		//String fechaClave = "2022-05-28";
		List<Reserva> listaReservas = reservaService.listAll(fechaClave);
		
		modelo.addAttribute("titulo", "Listado de reservas");
		modelo.addAttribute("listaReservas", listaReservas);
		modelo.addAttribute("fechaClave", fechaClave);
		this.fechaClave = fechaClave;
		
		return "lista_reservas";
	}
	

	@GetMapping("/ver_detalle_reserva/{id}")
	public String verDetallesReserva(@PathVariable(value="id") Long id, Map<String,Object> modelo, RedirectAttributes flash) {
		Reserva reserva = reservaService.findOne(id);
		if(reserva == null) {
			flash.addFlashAttribute("error", "La reserva no existe en la base de datos");
			return "redirect:/lista_reservas";
		}

		modelo.put("reserva", reserva);
		modelo.put("titulo", "Detalles de la reserva a nombre de ".concat(reserva.getCliente()));
		return "ver_detalle_reserva";
	}

	@GetMapping("/formulario_reserva")
	public String mostrarFormularioRegistroReserva(Map<String, Object> modelo) {
		Reserva reserva = new Reserva();
		List<Mesa> listaMesas = mesaService.listaMesas();
		modelo.put("titulo", "Registro de reservas");
		modelo.put("reserva", reserva);
		modelo.put("listaMesas", listaMesas);
		return "formulario_reserva";
	}

	@PostMapping("/formulario_reserva")
	public String guardarReserva(@Valid Reserva reserva, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {
		List<Mesa> listaMesas = mesaService.listaMesas();
		if(result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de reserva");
			modelo.addAttribute("reserva", reserva);
			modelo.addAttribute("listaMesas", listaMesas);
			return "formulario_reserva";
		}
		String mensaje = (reserva.getId() != null) ? "La reserva ha sido modificada correctamente" : "Reserva registrada correctamente";
		reservaService.save(reserva);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/lista_reservas";
	}

	@GetMapping("/formulario_reserva/{id}")
	public String editarReserva(@PathVariable(value="id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
		Reserva reserva = null;
		if(id > 0) {
			reserva = reservaService.findOne(id);
			if(reserva == null) {
				flash.addAttribute("error", "El ID de la reserva no existe en la base de datos");
				return "redirect:/lista_reservas";
			}
		}
		else {
			flash.addFlashAttribute("error", "El ID de la reserva no puede ser cero");
			return "redirect:/lista_reservas";
		}		
		List<Mesa> listaMesas = mesaService.listaMesas();
		modelo.put("titulo", "Editar reserva");
		modelo.put("reserva", reserva);
		modelo.put("listaMesas",listaMesas);
		return "formulario_reserva";		
	}

	@GetMapping("/eliminar_reserva/{id}")
	public String eliminarReserva(@PathVariable(value="id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
		if(id > 0) {
			reservaService.delete(id);
			flash.addFlashAttribute("warning", "Reserva eliminada correctamente");
		}
		return "redirect:/lista_reservas";

	}
	
	@GetMapping("/exportarReservasPDF")
	public void exportarListadoReservasPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_".concat(fechaActual).concat(".pdf");
		
		response.setHeader(cabecera, valor);
		//fechaClave = "2022-05-28";
		List<Reserva> reservas = reservaService.listAll(fechaClave);
		System.err.println(fechaClave);
		ReservaExporterPDF exporter = new ReservaExporterPDF(reservas);
		exporter.exportar(response);
		
	}
	
	@GetMapping("/exportarReservasExcel")
	public void exportarListadoReservasExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_".concat(fechaActual).concat(".xlsx");
		
		response.setHeader(cabecera, valor);
		System.err.println(fechaClave);
		List<Reserva> reservas = reservaService.listAll(fechaClave);
		
		ReservaExporterExcel exporter = new ReservaExporterExcel(reservas);
		exporter.exportar(response);
		
	}
}
