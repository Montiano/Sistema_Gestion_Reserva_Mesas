package com.sgrmr.app.util.reportes;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sgrmr.app.modelos.Reserva;

public class ReservaExporterExcel {

	private XSSFWorkbook libro;
	private XSSFSheet hoja;
	
	private List<Reserva> listaReservas;
	
	public ReservaExporterExcel(List<Reserva> listaReservas) {
		this.listaReservas = listaReservas;
		libro = new XSSFWorkbook();
		hoja = libro.createSheet("Reservas");
	}
	
	private void escribirCabeceraTabla() {
		Row fila = hoja.createRow(0);
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setBold(true);
		fuente.setFontHeight(16);
		estilo.setFont(fuente);
		
		Cell celda = fila.createCell(0);
		celda.setCellValue("ID");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(1);
		celda.setCellValue("Cliente");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(2);
		celda.setCellValue("Teléfono");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(3);
		celda.setCellValue("Fecha");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(4);
		celda.setCellValue("Hora");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(5);
		celda.setCellValue("Comedor");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(6);
		celda.setCellValue("Mesa");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(7);
		celda.setCellValue("Comensales");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(8);
		celda.setCellValue("Petición especial");
		celda.setCellStyle(estilo);
		
	}
	
	private void escribirDatosTabla() {
		
		int numeroFilas = 1;
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		
		for (Reserva reserva : listaReservas) {
			Row fila = hoja.createRow(numeroFilas++);
			
			Cell celda = fila.createCell(0);
			celda.setCellValue(reserva.getId());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(1);
			celda.setCellValue(reserva.getCliente());
			hoja.autoSizeColumn(1);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(2);
			celda.setCellValue(reserva.getTelefono());
			hoja.autoSizeColumn(2);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(3);
			celda.setCellValue(reserva.getFecha().toString());
			hoja.autoSizeColumn(3);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(4);
			celda.setCellValue(reserva.getHora().toString());
			hoja.autoSizeColumn(4);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(5);
			celda.setCellValue(reserva.getComedor());
			hoja.autoSizeColumn(5);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(6);
			celda.setCellValue(reserva.getMesa().toString());
			hoja.autoSizeColumn(6);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(7);
			celda.setCellValue(reserva.getComensales());
			hoja.autoSizeColumn(7);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(8);
			celda.setCellValue(reserva.getPeticionEspecial());
			hoja.autoSizeColumn(8);
			celda.setCellStyle(estilo);
			
		}
	}
	
	
	public void exportar(HttpServletResponse response) throws IOException {
		escribirCabeceraTabla();
		escribirDatosTabla();
		
		ServletOutputStream outputStream = response.getOutputStream();
		libro.write(outputStream);
		
		libro.close();
		outputStream.close();
		
	}
	
	
}
