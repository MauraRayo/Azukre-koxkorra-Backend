package com.ipartek.servicios;

import java.util.List;

import com.ipartek.modelo.Categoria;


public interface CategoriaServicio {

	boolean insertarCategoria(Categoria catego);
	boolean borrarCategoria(Integer id);
	boolean modificarCategoria(Categoria catego);

	Categoria obtenerCategoriaPorId(Integer id);

	List<Categoria> obtenerTodasCategorias();
}
