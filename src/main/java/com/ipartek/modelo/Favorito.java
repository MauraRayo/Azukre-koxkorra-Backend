package com.ipartek.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "favoritos")
public class Favorito {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private LocalDate fecha;
	
	@ManyToOne
	private Usuario usuario;
	
	@ManyToOne
	private Producto producto;

	
	public Favorito(int id, LocalDate fecha, Usuario usuario, Producto producto) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.usuario = usuario;
		this.producto = producto;
	}

	public Favorito() {
		super();
		this.id = 0;
		this.fecha = LocalDate.of(0001, 1, 1) ;
		this.usuario = new Usuario();
		this.producto = new Producto();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return "Favorito [id=" + id + ", fecha=" + fecha + ", usuario=" + usuario + ", producto=" + producto + "]";
	}

}
