package com.ipartek.servicios;

import java.util.List;

import com.ipartek.modelo.Producto;

public interface ProductoServicio {

	

	boolean insertarProducto(Producto produ);
	boolean borrarProducto(Integer id);
	boolean modificarProducto(Producto produ);
	
	Producto obtenerProductoPorId(Integer id);
	List<Producto> obtenerTodosProductos();
}
