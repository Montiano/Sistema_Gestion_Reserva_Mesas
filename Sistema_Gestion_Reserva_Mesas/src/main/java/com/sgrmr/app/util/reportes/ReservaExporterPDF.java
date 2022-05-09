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
import com.sgrmr.app.modelos.Reserva;


public class ReservaExporterPDF {

	private List<Reserva> listaReservas;
	
	public ReservaExporterPDF(List<Reserva> listaReservas) {
		super();
		this.listaReservas = listaReservas;
	}
	
	private void escribirCabeceraTabla(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();
		
		celda.setBackgroundColor(Color.BLUE);
		celda.setPadding(5);
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);
		
		celda.setPhrase(new Phrase("ID", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Cliente", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Teléfono", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Fecha", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Hora", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Comedor", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Mesa", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Comensales", fuente));		
		tabla.addCell(celda);
		celda.setPhrase(new Phrase("Petición especial", fuente));		
		tabla.addCell(celda);
		
	}
	
	private void escribirDatosTabla(PdfPTable tabla) {
		
		for (Reserva reserva : listaReservas) {
			tabla.addCell(String.valueOf(reserva.getId()));
			tabla.addCell(reserva.getCliente());
			tabla.addCell(reserva.getTelefono());
			tabla.addCell(reserva.getFecha().toString());
			tabla.addCell(reserva.getHora().toString());
			tabla.addCell(reserva.getComedor());
			tabla.addCell(String.valueOf(reserva.getMesa()));
			tabla.addCell(String.valueOf(reserva.getComensales()));
			tabla.addCell(reserva.getPeticionEspecial());		
		}	
	}
	
	public void exportar(HttpServletResponse response) throws DocumentException, IOException {
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());
		documento.open();
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.BLUE);
		fuente.setSize(18);
		
		Paragraph titulo = new Paragraph("Lista de Reservas:", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);
		
		PdfPTable tabla = new PdfPTable(9);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float[] {0.9f,2.5f,2.5f,2.5f,1.7f,2.3f,1.7f,3f,4.8f});
		tabla.setWidthPercentage(110);
		
		escribirCabeceraTabla(tabla);
		escribirDatosTabla(tabla);
		
		documento.add(tabla);
		documento.close();
		
	}
}
