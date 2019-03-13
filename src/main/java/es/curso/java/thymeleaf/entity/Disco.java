package es.curso.java.thymeleaf.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Disco implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
    private String titulo;
	private int lanzamiento;
	private int ventas;
	
	/**
	 * @param id
	 * @param titulo
	 * @param lanzamiento
	 * @param ventas
	 */
	public Disco(String id, String titulo, int lanzamiento, int ventas) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.lanzamiento = lanzamiento;
		this.ventas = ventas;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getLanzamiento() {
		return lanzamiento;
	}
	public void setLanzamiento(int lanzamiento) {
		this.lanzamiento = lanzamiento;
	}
	public int getVentas() {
		return ventas;
	}
	public void setVentas(int ventas) {
		this.ventas = ventas;
	}
	
	
}
