package com.ipartek.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Usuario;
import com.ipartek.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;

	@Override
	public boolean insertarUsuario(Usuario usu) {
		if (usu.getId() == 0) {

			Usuario usuarioTemp = usuarioRepo.save(usu);

			if (usuarioTemp.getName().equals(usu.getName())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean borrarUsuario(Integer id) {
		Usuario usu = new Usuario();

		Optional<Usuario> usuarioTemp = usuarioRepo.findById(id);
		if (usuarioTemp.isPresent()) {
			usu = usuarioTemp.orElse(new Usuario());
		} else {
			usu = new Usuario();
		}

		if (usu.getId() != 0) {

			usuarioRepo.deleteById(id);

			usuarioTemp = usuarioRepo.findById(id);
			if (usuarioTemp.isPresent()) {
				if (usuarioTemp.get().getId() == usu.getId()) {
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}

		} else {
			return false;
		}
	}

	@Override
	public boolean modificarUsuario(Usuario usu) {
		Optional<Usuario> usuarioTemp = usuarioRepo.findById(usu.getId());

		if (usuarioTemp.isPresent()) {

			usuarioRepo.save(usu);

			Optional<Usuario> usuarioTemp2 = usuarioRepo.findById(usu.getId());
			Usuario usu2 = new Usuario();

			if (usuarioTemp2.isPresent()) {
				usu2 = usuarioTemp2.orElse(new Usuario());

			}
			if (!usu.equals(usu2)) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	@Override
	public Usuario obtenerUsuarioPorId(Integer id) {
		int idTemp = 0;
		if (id != null) {
			idTemp = id;
		}
		Optional<Usuario> usu = usuarioRepo.findById(idTemp);

		if (usu.isPresent()) {
			return usu.orElse(new Usuario());
		} else {
			return new Usuario();
		}
		

	}

	@Override
	public List<Usuario> obtenerTodosUsuario() {
		return usuarioRepo.findAll();
	}
	
	@Override
	public boolean validarUsuario(String usu, String pas) {
	    System.out.println("Validando usuario desde backend...");

	    Usuario usuBD = usuarioRepo.findByName(usu);

	    if (usuBD == null) {
	        System.out.println("Usuario no encontrado: " + usu);
	        return false;
	    }

	    System.out.println("Usuario encontrado: " + usuBD.getName());
	    System.out.println("Password recibida: " + pas);
	    System.out.println("Password en BD: " + usuBD.getPass());

	    // Comparación sensible a mayúsculas (correcta para contraseñas)
	    boolean valido = pas.equals(usuBD.getPass());

	    if (valido) {
	        System.out.println("✅ Usuario y contraseña correctos");
	    } else {
	        System.out.println("❌ Contraseña incorrecta");
	    }

	    return valido;
	}

	@Override
	public Usuario obtenerUsuarioPorNombre(String name) {
	
		return usuarioRepo.findByName(name);
	}

	
	
}
