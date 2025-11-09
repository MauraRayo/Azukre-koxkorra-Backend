package com.ipartek.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Categoria;
import com.ipartek.repositorio.CategoriaRepositorio;

@Service
public class CategoriaServicioImpl implements CategoriaServicio {

	@Autowired
	private CategoriaRepositorio categoriaRepo;

	@Override
	public boolean insertarCategoria(Categoria catego) {
		if (catego.getId() == 0) {

			Categoria categoriaTemp = categoriaRepo.save(catego);

			if (categoriaTemp.getNombre().equals(catego.getNombre())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean borrarCategoria(Integer id) {
		Categoria catego = new Categoria();

		Optional<Categoria> categoriaTemp = categoriaRepo.findById(id);
		if (categoriaTemp.isPresent()) {
			catego = categoriaTemp.orElse(new Categoria());
		} else {
			catego = new Categoria();
		}

		if (catego.getId() != 0) {

			categoriaRepo.deleteById(id);

			categoriaTemp = categoriaRepo.findById(id);
			if (categoriaTemp.isPresent()) {
				if (categoriaTemp.get().getId() == catego.getId()) {
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
	public boolean modificarCategoria(Categoria catego) {
		Optional<Categoria> categoriaTemp = categoriaRepo.findById(catego.getId());

		if (categoriaTemp.isPresent()) {

			categoriaRepo.save(catego);

			Optional<Categoria> categoriaTemp2 = categoriaRepo.findById(catego.getId());
			Categoria catego2 = new Categoria();

			if (categoriaTemp2.isPresent()) {
				catego2 = categoriaTemp2.orElse(new Categoria());

			}
			if (!catego.equals(catego2)) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	@Override
	public Categoria obtenerCategoriaPorId(Integer id) {
		int idTemp = 0;
		if (id != null) {
			idTemp = id;
		}
		 Optional<Categoria> catego = categoriaRepo.findById(idTemp);
		
		 if (catego.isPresent()) {
				return catego.orElse(new Categoria());
			} else {
				return new Categoria();
			}
	}

	@Override
	public List<Categoria> obtenerTodasCategorias() {
		return categoriaRepo.findAll();
	}

}
