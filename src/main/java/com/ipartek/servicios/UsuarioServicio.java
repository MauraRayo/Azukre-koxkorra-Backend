package com.ipartek.servicios;

import java.util.List;

import com.ipartek.modelo.Usuario;

public interface UsuarioServicio {
	
	boolean insertarUsuario(Usuario usu);
	boolean borrarUsuario(Integer id);
	boolean modificarUsuario(Usuario usu);
	
	Usuario obtenerUsuarioPorId(Integer id);
	List<Usuario> obtenerTodosUsuario();
	boolean validarUsuario(String usu, String pas);
	Usuario obtenerUsuarioPorNombre(String name);
	
	
}
