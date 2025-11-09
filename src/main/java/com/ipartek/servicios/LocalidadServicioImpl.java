package com.ipartek.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Localidad;
import com.ipartek.repositorio.LocalidadRepositorio;

@Service
public class LocalidadServicioImpl implements LocalidadServicio {
	@Autowired
	private LocalidadRepositorio localidadRepo;

	@Override
	public boolean insertarLocalidad(Localidad locali) {
		if (locali.getId() == 0) {

			Localidad localidadTemp = localidadRepo.save(locali);

			if (localidadTemp.getNombre().equals(locali.getNombre())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean borrarLocalidad(Integer id) {
		Localidad locali = new Localidad();

		Optional<Localidad> localidadTemp = localidadRepo.findById(id);
		if (localidadTemp.isPresent()) {
			locali = localidadTemp.orElse(new Localidad());
		} else {
			locali = new Localidad();
		}

		if (locali.getId() != 0) {

			localidadRepo.deleteById(id);

			localidadTemp = localidadRepo.findById(id);
			if (localidadTemp.isPresent()) {
				if (localidadTemp.get().getId() == locali.getId()) {
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
	public boolean modificarLocalidad(Localidad locali) {
		Optional<Localidad> localidadTemp = localidadRepo.findById(locali.getId());

		if (localidadTemp.isPresent()) {

			localidadRepo.save(locali);

			Optional<Localidad> localidadTemp2 = localidadRepo.findById(locali.getId());
			Localidad locali2 = new Localidad();

			if (localidadTemp2.isPresent()) {
				locali2 = localidadTemp2.orElse(new Localidad());

			}
			if (!locali.equals(locali2)) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	@Override
	public Localidad obtenerLocalidadPorId(Integer id) {
		int idTemp = 0;
		if (id != null) {
			idTemp = id;
		}
		Optional<Localidad> locali = localidadRepo.findById(idTemp);

		if (locali.isPresent()) {
			return locali.orElse(new Localidad());
		} else {
			return new Localidad();
		}
	}

	@Override
	public List<Localidad> obtenerTodasLocalidades() {
		// TODO Auto-generated method stub
		return localidadRepo.findAll();
	}

}
