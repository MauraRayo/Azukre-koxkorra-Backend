package com.ipartek.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.modelo.Favorito;

@Repository
public interface FavoritoRepositorio extends JpaRepository<Favorito, Integer> {

	List<Favorito> findByUsuarioId(Integer usuarioId);

}
