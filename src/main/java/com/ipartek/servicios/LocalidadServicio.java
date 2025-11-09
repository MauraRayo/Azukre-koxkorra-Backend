package com.ipartek.servicios;

import java.util.List;

import com.ipartek.modelo.Localidad;

public interface LocalidadServicio {
	
	boolean insertarLocalidad(Localidad locali);
	boolean borrarLocalidad(Integer id);
	boolean modificarLocalidad(Localidad locali);
	
	Localidad obtenerLocalidadPorId(Integer id);
	List<Localidad> obtenerTodasLocalidades();
	

}
