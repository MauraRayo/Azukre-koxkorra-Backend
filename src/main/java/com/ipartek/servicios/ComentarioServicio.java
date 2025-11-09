package com.ipartek.servicios;

import java.util.List;

import com.ipartek.modelo.Comentario;

public interface ComentarioServicio {
	
	boolean insertarComentario(Comentario comen);
	boolean borrarComentario(Integer id);
	boolean modificarComentario(Comentario comen);
	
	Comentario obtenerComentarioPorId(Integer id);
	List<Comentario> obtenerTodosComentarios();

}
