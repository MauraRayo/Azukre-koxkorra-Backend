package com.ipartek.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comentarios")
public class Comentario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String texto;
	private int valoracion;
	private LocalDate fecha;
	
	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	private Producto producto;

	public Comentario(int id, String texto, int valoracion, LocalDate fecha, Usuario usuario, Producto producto) {
		super();
		this.id = id;
		this.texto = texto;
		this.valoracion = valoracion;
		this.fecha = fecha;
		this.usuario = usuario;
		this.producto = producto;
	}
	
	public Comentario() {
		super();
		this.id = 0;
		this.texto = "";
		this.valoracion = 0;
	    this.fecha = LocalDate.of(0001, 1, 1);
		this.usuario = new Usuario();
		this.producto =new Producto();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public int getValoracion() {
		return valoracion;
	}

	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		return "Comentario [id=" + id + ", texto=" + texto + ", valoracion=" + valoracion + ", fecha=" + fecha
				+ ", usuario=" + usuario + ", producto=" + producto + "]";
	}
}
