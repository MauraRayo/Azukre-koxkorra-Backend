package com.ipartek.controlador;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.modelo.Favorito;
import com.ipartek.modelo.FavoritoDTO;
import com.ipartek.modelo.Usuario;
import com.ipartek.pojos.ErrorMsg;
import com.ipartek.repositorio.UsuarioRepositorio;
import com.ipartek.servicios.FavoritoServicio;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/favoritos")
@Tag(name = "favoritos", description = "Opreaciones relacionadas con los productos guardados")
public class FavoritoControlador {

	@Autowired
	private FavoritoServicio favoritoServ;


	@Autowired
	private UsuarioRepositorio usuarioRepo;

	@PostMapping
	@Operation(summary = "Insertar un favorito")
	public ResponseEntity<?> agregarFavorito(@RequestBody FavoritoDTO dto, Principal principal) {
		try {
			// Solo usuarios logueados
			if (principal == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body("Debes iniciar sesión para agregar favoritos");
			}

			// Obtener el usuario logueado
			String username = principal.getName();

			Usuario usuario = usuarioRepo.findByName(username);
			if (usuario == null) {
				throw new RuntimeException("Usuario no encontrado");
			}

			Favorito favorito = favoritoServ.agregarFavorito(usuario.getId(), dto.getProductoId());
			return ResponseEntity.ok(favorito);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al agregar favorito: " + e.getMessage());
		}
	}

	// -------------------- INSERTAR FAVORITO ----------------------------
	// ----------------------------------------------------------------

//	@PostMapping()
//	@Operation(summary = "Insertar un favorito")
//	@ApiResponses({ @ApiResponse(responseCode = "200", description = "El favorito ha sido guardada correctamente"),
//			@ApiResponse(responseCode = "500", description = "La categoria no se ha guardada") })
//	public ResponseEntity<ErrorMsg> insertarFavorito(@RequestBody Favorito favori) {
//		boolean insertado = favoritoServ.insertarFavorito(favori);
//
//		if (insertado) {
//			return ResponseEntity.ok().body(new ErrorMsg(0, "Favorito insertado correctamente"));
//		} else {
//			return ResponseEntity.internalServerError()
//					.body(new ErrorMsg(1, "Error, no se ha podido insertar el Favorito"));
//		}
//	}

	// -------------------- BORRAR FAVORITO ----------------------------
	// ---------------------------------------------------------------

	@DeleteMapping("/{id}")
	@Operation(summary = "Borrar un favorito, por su id en la barra de direcciones")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "favorito obtenido"),
			@ApiResponse(responseCode = "404", description = "favorito NO obtenidaço"),
			@ApiResponse(responseCode = "500", description = "Error") })
	public ResponseEntity<ErrorMsg> borrarFavorito(@PathVariable String id) {
		try {
			int idTemp = Integer.parseInt(id.toString());

			boolean resultado = favoritoServ.borrarFavorito(idTemp);

			if (resultado) {// 200
				return ResponseEntity.ok().body(new ErrorMsg(0, "El favorito ha sido borrado correctamente"));
			} else {// 500
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ErrorMsg(1, "No se ha podido borrar el favorito"));
			}
		} catch (Exception e) {// 400
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg(1, "ID con formato no valido"));
		}
	}

	// ------------------------------- MODIFICAR FAVORITO---------------------------
	// -------------------------------------------------------------------------------

	@PutMapping()
	@Operation(summary = "Modificar un Favorito")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "El Favorito ha sido modificado", content = @Content(schema = @Schema(implementation = ErrorMsg.class))),
			@ApiResponse(responseCode = "500", description = "No se ha podido modificar el Favorito", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })
	public ResponseEntity<ErrorMsg> modificarFavorito(@RequestBody Favorito favori) {
		boolean modificado = favoritoServ.modificarFavorito(favori);

		if (modificado) {
			return ResponseEntity.ok().body(new ErrorMsg(0, "El favorito ha sido modificado correctamente"));
		} else {
			return ResponseEntity.internalServerError().body(new ErrorMsg(1, "No se pudo modificar el favorito"));
		}

	}

	// ------------------------ OBTENER FAVORITO POR SU ID --------------------
	// ----------------------------------------------------------------------

	@GetMapping("/{id}")
	@Operation(summary = "Obtener un favorito, por su id en la barra de direcciones")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "favorito obtenido", content = @Content(schema = @Schema(implementation = Favorito.class))),

			@ApiResponse(responseCode = "404", description = "favorito no obtenido", content = @Content(schema = @Schema(implementation = ErrorMsg.class))),

			@ApiResponse(responseCode = "400", description = "Parametro mal puesto", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })

	public ResponseEntity<Object> obtenerFavoritoPorId(@PathVariable String id) {

		try {
			int idTemp = Integer.parseInt(id.toString());

			Favorito favori = favoritoServ.obtenerFavoritoPorId(idTemp);

			if (favori.getId() != 0) {// 200

				return ResponseEntity.ok().body(favori);

			} else {// 404
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ErrorMsg(1, "No se pudo encontar el producto guardado"));
			}
		} catch (Exception e) {// 400
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg(1, "ID con formato no valido"));

		}

	}

	// ------------------------ OBTENER TODAS LOS FAVORITOS --------------------
	// -----------------------------------------------------------------------

	@GetMapping("")
	@Operation(summary = "Obtener todos Los favoritos")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Favoritoss obtenidas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Favorito.class)))),
			@ApiResponse(responseCode = "404", description = "Favoritoss esta vacio", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })
	public ResponseEntity<Object> obtenerTodascategorias() {

		List<Favorito> favori = favoritoServ.obtenerTodosFavoritos();

		if (favori.size() != 0) {// 200
			return ResponseEntity.ok().body(favori);
		} else {// 404
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorMsg(1, "No se pudo encontar los favoritos"));
		}
	}

	@GetMapping("/usuario/{id}")
	public List<Favorito> obtenerFavoritosPorUsuario(@PathVariable Integer id) {
		return favoritoServ.obtenerTodosFavoritos().stream()
				.filter(f -> f.getUsuario() != null && id.equals(f.getUsuario().getId())).collect(Collectors.toList());
	}

}
