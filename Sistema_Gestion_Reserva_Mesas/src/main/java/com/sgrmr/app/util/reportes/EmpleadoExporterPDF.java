package com.sgrmr.app.util.reportes;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sgrmr.app.modelos.Empleado;


public class EmpleadoExporterPDF {

	private List<Empleado> listaEmpleados;

	public EmpleadoExporterPDF(List<Empleado> listaEmpleados) {
		super();
		this.listaEmpleados = listaEmpleados;
	}
	
	private void escribirCabeceraTabla(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();
		
		celda.setBackgroundColor(Color.BLUE);
		celda.setPadding(5);
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);
		
		celda.setPhrase(new Phrase("ID", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Nombre", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Apellido", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Email", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Fecha", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Teléfono", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Sexo", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Sueldo", fuente));		
		tabla.addCell(celda);
		
	}
	
	private void escribirDatosTabla(PdfPTable tabla) {
		
		for (Empleado empleado : listaEmpleados) {
			tabla.addCell(String.valueOf(empleado.getId()));
			tabla.addCell(empleado.getNombre());
			tabla.addCell(empleado.getApellido());
			tabla.addCell(empleado.getEmail());
			tabla.addCell(empleado.getFecha().toString());
			tabla.addCell(String.valueOf(empleado.getTelefono()));
			tabla.addCell(empleado.getSexo());
			tabla.addCell(String.valueOf(empleado.getSueldo()));		
		}	
	}
	
	public void exportar(HttpServletResponse response) throws DocumentException, IOException {
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());
		documento.open();
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.BLUE);
		fuente.setSize(18);
		
		Paragraph titulo = new Paragraph("Lista de Empleados:", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);
		
		PdfPTable tabla = new PdfPTable(8);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float[] {1f,2.4f,2.6f,6.5f,2.9f,2.8f,1.5f,2.2f});
		tabla.setWidthPercentage(110);
		
		escribirCabeceraTabla(tabla);
		escribirDatosTabla(tabla);
		
		documento.add(tabla);
		documento.close();
		
	}
}
