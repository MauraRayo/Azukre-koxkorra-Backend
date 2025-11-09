package com.ipartek.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Comentario;
import com.ipartek.repositorio.ComentarioRepositorio;

@Service
public class ComentarioServicioImpl implements ComentarioServicio {
	
	@Autowired
	private ComentarioRepositorio comentarioRepo;

	@Override
	public boolean insertarComentario(Comentario comen) {
		if (comen.getId() == 0) {

			Comentario comentarioTemp = comentarioRepo.save(comen);

			if (comentarioTemp.getTexto().equals(comen.getTexto())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean borrarComentario(Integer id) {
		Comentario comen = new Comentario();

		Optional<Comentario> comentarioTemp = comentarioRepo.findById(id);
		if (comentarioTemp.isPresent()) {
			comen = comentarioTemp.orElse(new Comentario());
		} else {
			comen = new Comentario();
		}

		if (comen.getId() != 0) {

			comentarioRepo.deleteById(id);

			comentarioTemp = comentarioRepo.findById(id);
			
			if (comentarioTemp.isPresent()) {
				if (comentarioTemp.get().getId() == comen.getId()) {
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
	public boolean modificarComentario(Comentario comen) {
		Optional<Comentario> comentarioTemp = comentarioRepo.findById(comen.getId());

		if (comentarioTemp.isPresent()) {

			comentarioRepo.save(comen);

			Optional<Comentario> comentarioTemp2 = comentarioRepo.findById(comen.getId());
			Comentario comen2 = new Comentario();

			if (comentarioTemp2.isPresent()) {
				comen2 = comentarioTemp2.orElse(new Comentario());

			}
			if (!comen.equals(comen2)) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	@Override
	public Comentario obtenerComentarioPorId(Integer id) {
		int idTemp = 0;
		if (id != null) {
			idTemp = id;
		}
		 Optional<Comentario> comen = comentarioRepo.findById(idTemp);
		
		 if (comen.isPresent()) {
				return comen.orElse(new Comentario());
			} else {
				return new Comentario();
			}
	}

	@Override
	public List<Comentario> obtenerTodosComentarios() {
		
		return comentarioRepo.findAll();
	}

}
