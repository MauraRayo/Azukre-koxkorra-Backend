package com.ipartek.servicios;

import java.util.List;

import com.ipartek.modelo.Favorito;

public interface FavoritoServicio {
	
	Favorito agregarFavorito(int usuarioId, int productoId);
	
	boolean borrarFavorito(Integer id);
	boolean modificarFavorito(Favorito favori);
	
	Favorito obtenerFavoritoPorId(Integer id);
	List<Favorito> obtenerTodosFavoritos();
	
	List<Favorito> obtenerFavoritosPorUsuario(Integer usuarioId);

	


}
