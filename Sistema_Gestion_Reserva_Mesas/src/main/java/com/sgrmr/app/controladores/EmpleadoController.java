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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;
import com.sgrmr.app.modelos.Empleado;
import com.sgrmr.app.servicios.EmpleadoService;
import com.sgrmr.app.util.paginacion.PageRender;
import com.sgrmr.app.util.reportes.EmpleadoExporterExcel;
import com.sgrmr.app.util.reportes.EmpleadoExporterPDF;

@Controller
public class EmpleadoController {

	@Autowired
	private EmpleadoService empleadoService;

//	@GetMapping({"/",""})
//	public String index() {
//		return "login";
//	}
	
	@GetMapping("/ver_detalle_empleado/{id}")
	public String verDetallesEmpleado(@PathVariable(value="id") Long id, Map<String,Object> modelo, RedirectAttributes flash) {
		Empleado empleado = empleadoService.findOne(id);
		if(empleado == null) {
			flash.addFlashAttribute("error", "Empleado no existe en la base de datos");
			return "redirect:/lista_empleados";
		}

		modelo.put("empleado", empleado);
		modelo.put("titulo", "Detalles del empleado ".concat(empleado.getNombre()));
		return "ver_detalle_empleado";
	}

	@GetMapping({"/lista_empleados"})
	public String listarEmpleados(@RequestParam(name = "page", defaultValue = "0") int pagina, Model modelo) {
		Pageable pageRequest = PageRequest.of(pagina, 5);
		Page<Empleado> empleados = empleadoService.finalAll(pageRequest);
		PageRender<Empleado> pageRender = new PageRender<>("/lista_empleados", empleados);

		modelo.addAttribute("titulo", "Listado de empleados");
		modelo.addAttribute("empleados", empleados);
		modelo.addAttribute("page", pageRender);

		return "lista_empleados";
	}


	@GetMapping("/formulario_empleado")
	public String mostrarFormularioRegistroEmpleado(Map<String, Object> modelo) {
		Empleado empleado = new Empleado();
		modelo.put("empleado", empleado);
		modelo.put("titulo", "Registro de empleados");
		return "formulario_empleado";
	}

	@PostMapping("/formulario_empleado")
	public String guardarEmpleado(@Valid Empleado empleado, BindingResult result, Model modelo, RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			modelo.addAttribute("titulo", "Registro de empleado");
			return "formulario_empleado";
		}

		String mensaje = (empleado.getId() != null) ? "El empleado ha sido modificado correctamente" : "Empleado registrado correctamente";
		empleadoService.save(empleado);
		status.setComplete();
		flash.addFlashAttribute("success", mensaje);
		return "redirect:/lista_empleados";
	}

	@GetMapping("/formulario_empleado/{id}")
	public String editarEmpleado(@PathVariable(value="id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
		Empleado empleado = null;
		if(id > 0) {
			empleado = empleadoService.findOne(id);
			if(empleado == null) {
				flash.addFlashAttribute("error", "El ID del empleado no existe en la base de datos");
				return "redirect:/lista_empleados";
			}
		}
		else {
			flash.addFlashAttribute("error", "El ID del empleado no puede ser cero");
			return "redirect:/lista_empleados";
		}

		modelo.put("empleado", empleado);
		modelo.put("titulo", "Edición de empleado");
		return "formulario_empleado";		
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarEmpleado(@PathVariable(value="id") Long id, Map<String, Object> modelo, RedirectAttributes flash) {
		if(id > 0) {
			empleadoService.delete(id);
			flash.addFlashAttribute("warning", "Empleado eliminado correctamente");
		}
		return "redirect:/lista_empleados";

	}
	
	@GetMapping("/exportarPDF")
	public void exportarListadoEmpleadosPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_".concat(fechaActual).concat(".pdf");
		
		response.setHeader(cabecera, valor);
		
		List<Empleado> empleados = empleadoService.findAll();
		
		EmpleadoExporterPDF exporter = new EmpleadoExporterPDF(empleados);
		exporter.exportar(response);
		
	}
	
	@GetMapping("/exportarExcel")
	public void exportarListadoEmpleadosExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_".concat(fechaActual).concat(".xlsx");
		
		response.setHeader(cabecera, valor);
		
		List<Empleado> empleados = empleadoService.findAll();
		
		EmpleadoExporterExcel exporter = new EmpleadoExporterExcel(empleados);
		exporter.exportar(response);
		
	}
}
