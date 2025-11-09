package com.ipartek.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String pass;
	private String rol;
	
	public Usuario(int id, String name, String pass, String rol) {
		super();
		
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.rol = rol;
	}
	
	public Usuario() {
		super();
		this.id = 0;
		this.name = "";
		this.pass = "";
		this.rol = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", name=" + name + ", pass=" + pass + ", rol=" + rol + "]";
	}

}
