package com.ipartek.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.modelo.Producto;
import com.ipartek.repositorio.ProductoRepositorio;

@Service
public class ProductoServicioImpl implements ProductoServicio {

	@Autowired
	private ProductoRepositorio productoRepo;
	
	@Override
	public boolean insertarProducto(Producto produ) {
	    if (produ.getId() == 0) {
	        Producto productoTemp = productoRepo.save(produ);
	        return productoTemp.getId() != 0;
	    } else {
	        return false;
	    }
	}


	@Override
	public boolean borrarProducto(Integer id) {
		Producto produ = new Producto();

		Optional<Producto> productoTemp = productoRepo.findById(id);
		if (productoTemp.isPresent()) {
			produ = productoTemp.orElse(new Producto());
		} else {
			produ = new Producto();
		}

		if (produ.getId() != 0) {

			productoRepo.deleteById(id);

			productoTemp = productoRepo.findById(id);
			if (productoTemp.isPresent()) {
				if (productoTemp.get().getId() == produ.getId()) {
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
	public boolean modificarProducto(Producto produ) {
		Optional<Producto> productoTemp = productoRepo.findById(produ.getId());

		if (productoTemp.isPresent()) {

			productoRepo.save(produ);

			Optional<Producto> productoTemp2 = productoRepo.findById(produ.getId());
			Producto produ2 = new Producto();

			if (productoTemp2.isPresent()) {
				produ2 = productoTemp2.orElse(new Producto());

			}
			if (!produ.equals(produ2)) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	@Override
	public Producto obtenerProductoPorId(Integer id) {
		int idTemp = 0;
		if (id != null) {
			idTemp = id;
		}
		Optional<Producto> produ = productoRepo.findById(idTemp);

		if (produ.isPresent()) {
			return produ.orElse(new Producto());
		} else {
			return new Producto();
		}
	}

	@Override
	public List<Producto> obtenerTodosProductos() {
		return productoRepo.findAll();
	}

}
