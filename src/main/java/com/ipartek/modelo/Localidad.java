package com.ipartek.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "localidades")
public class Localidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	
	public Localidad(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public Localidad() {
		super();
		this.id = 0;
		this.nombre = "";
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

	@Override
	public String toString() {
		return "Localidad [id=" + id + ", nombre=" + nombre + "]";
	}
	
	

}
