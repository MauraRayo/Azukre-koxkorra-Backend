 package com.ipartek.modelo;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	private String descripcion;
	private String img;
	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal precio;
	private int stock;
	
	@ManyToOne
	private Localidad localidad;
	
	@ManyToOne
	private Categoria categoria;

	public Producto(int id, String nombre, String descripcion, String img, BigDecimal precio, int stock,
			Localidad localidad, Categoria categoria) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.img = img;
		this.precio = precio;
		this.stock = stock;
		this.localidad = localidad;
		this.categoria = categoria;
	}
	
	public Producto() {
		super();
		this.id = 0;
		this.nombre = "";
		this.descripcion = "";
		this.img = "";
		this.precio = new BigDecimal(0);
		this.stock = 0;
		this.localidad = new Localidad();
		this.categoria = new Categoria();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", img=" + img
				+ ", precio=" + precio + ", stock=" + stock + ", localidad=" + localidad + ", categoria=" + categoria
				+ "]";
	}
	
}
