package com.ipartek.servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ipartek.modelo.Favorito;
import com.ipartek.modelo.Producto;
import com.ipartek.modelo.Usuario;
import com.ipartek.repositorio.FavoritoRepositorio;
import com.ipartek.repositorio.ProductoRepositorio;
import com.ipartek.repositorio.UsuarioRepositorio;

@Service
public class FavoritoServicioImpl implements FavoritoServicio {

	@Autowired
	private FavoritoRepositorio favoritoRepo;
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	@Autowired
	private ProductoRepositorio productoRepo;

	@Override
	 public Favorito agregarFavorito(int usuarioId, int productoId) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Producto producto = productoRepo.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setProducto(producto);
        favorito.setFecha(LocalDate.now());

        return favoritoRepo.save(favorito);
    }

	@Override
	public boolean borrarFavorito(Integer id) {
		Favorito favori= new Favorito();

		Optional<Favorito> favoritoTemp = favoritoRepo.findById(id);
		if (favoritoTemp.isPresent()) {
			favori = favoritoTemp.orElse(new Favorito());
		} else {
			favori = new Favorito();
		}

		if (favori.getId() != 0) {

			favoritoRepo.deleteById(id);

			favoritoTemp = favoritoRepo.findById(id);
			if (favoritoTemp.isPresent()) {
				if (favoritoTemp.get().getId() == favori.getId()) {
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
	public boolean modificarFavorito(Favorito favori) {
		Optional<Favorito> favoritoTemp = favoritoRepo.findById(favori.getId());

		if (favoritoTemp.isPresent()) {

			favoritoRepo.save(favori);

			
			Optional<Favorito> favoritoTemp2 = favoritoRepo.findById(favori.getId());
			Favorito favori2 = new Favorito();

			if (favoritoTemp2.isPresent()) {
				favori2 = favoritoTemp2.orElse(new Favorito());

			}
			if (!favori.equals(favori2)) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	@Override
	public Favorito obtenerFavoritoPorId(Integer id) {
		int idTemp = 0;
		if (id != null) {
			idTemp = id;
		}
		Optional<Favorito> favori = favoritoRepo.findById(idTemp);

		if (favori.isPresent()) {
			return favori.orElse(new Favorito());
		} else {
			return new Favorito();
		}
	}

	@Override
	public List<Favorito> obtenerTodosFavoritos() {
		// TODO Auto-generated method stub
		return favoritoRepo.findAll();
	}
	
	
	@Override
	public List<Favorito> obtenerFavoritosPorUsuario(Integer usuarioId) {
	    if (usuarioId == null) {
	        return List.of(); // usuario no logueado
	    }
	    return favoritoRepo.findByUsuarioId(usuarioId);
	}

}
