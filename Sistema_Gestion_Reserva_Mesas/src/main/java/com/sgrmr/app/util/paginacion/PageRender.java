package com.sgrmr.app.util.paginacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	private String url;
	private Page<T> pagina;
	private int totalPaginas;
	private int numElementosPorPagina;
	private int paginaActual;
	private List<PageItem> paginas;
	
	
	public PageRender(String url, Page<T> pagina) {
		this.url = url;
		this.pagina = pagina;
		this.paginas = new ArrayList<PageItem>();
		// Muestra el número de elementos de páginas donde poder clickar
		numElementosPorPagina = 3;
		totalPaginas = pagina.getTotalPages();
		paginaActual = pagina.getNumber() + 1;
		
		int desde, hasta;
		if(totalPaginas <= numElementosPorPagina) {
			desde = 1;
			hasta = totalPaginas;
		}else {
			if(paginaActual <= numElementosPorPagina/2) {
				desde = 1;
				hasta = numElementosPorPagina;
			}
			else if(paginaActual >= totalPaginas - numElementosPorPagina/2) {
				desde = totalPaginas - numElementosPorPagina + 1;
				hasta = numElementosPorPagina;
			}
			else {
				desde = paginaActual - numElementosPorPagina/2;
				hasta = numElementosPorPagina;
			}
		}
		
		for (int i = 0; i <hasta; i++) {
			// Actualizamos la página donde nos encontremos para que la quite del menú
			paginas.add(new PageItem(desde+i, paginaActual == desde +i));
		}
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public Page<T> getPagina() {
		return pagina;
	}


	public void setPagina(Page<T> pagina) {
		this.pagina = pagina;
	}


	public int getTotalPaginas() {
		return totalPaginas;
	}


	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}


	public int getNumElementosPorPagina() {
		return numElementosPorPagina;
	}


	public void setNumElementosPorPagina(int numElementosPorPagina) {
		this.numElementosPorPagina = numElementosPorPagina;
	}


	public int getPaginaActual() {
		return paginaActual;
	}


	public void setPaginaActual(int paginaActual) {
		this.paginaActual = paginaActual;
	}


	public List<PageItem> getPaginas() {
		return paginas;
	}


	public void setPaginas(List<PageItem> paginas) {
		this.paginas = paginas;
	}


	public boolean isFirst() {
		return pagina.isFirst();
	}
	
	public boolean isLast() {
		return pagina.isLast();
	}
	
	public boolean isHasNext() {
		return pagina.hasNext();
	}
	
	public boolean isHasPrevius() {
		return pagina.hasPrevious();
	}
	
	
	
	
}
