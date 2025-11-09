package com.ipartek.controlador;

import java.util.List;

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


import com.ipartek.modelo.Comentario;
import com.ipartek.pojos.ErrorMsg;
import com.ipartek.servicios.ComentarioServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/comentarios")
@Tag(name = "Comentarios", description = "Opreaciones relacionadas con los comentarios")
public class ComentarioControlador {

	@Autowired
	private ComentarioServicio comentarioServ;

	// -------------------- INSERTAR COMENTARIO ----------------------------
	// ----------------------------------------------------------------

	@PostMapping()
	@Operation(summary = "Insertar un comentario")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "El comentario ha sido guardada correctamente"),
			@ApiResponse(responseCode = "500", description = "La categoria no se ha guardada") })
	public ResponseEntity<ErrorMsg> insertarComentario(@RequestBody Comentario comen) {
		boolean insertado = comentarioServ.insertarComentario(comen);

		if (insertado) {
			return ResponseEntity.ok().body(new ErrorMsg(0, "Comentario insertado correctamente"));
		} else {
			return ResponseEntity.internalServerError()
					.body(new ErrorMsg(1, "Error, no se ha podido insertar el comentario"));
		}
	}

	// -------------------- BORRAR COMENTARIO ----------------------------
	// ---------------------------------------------------------------

	@DeleteMapping("/{id}")
	@Operation(summary = "Borrar un comentario, por su id en la barra de direcciones")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "comentario obtenido"),
			@ApiResponse(responseCode = "404", description = "comentario NO obtenidaço"),
			@ApiResponse(responseCode = "500", description = "Error") })
	public ResponseEntity<ErrorMsg> borrarComentario(@PathVariable String id) {
		try {
			int idTemp = Integer.parseInt(id.toString());

			boolean resultado = comentarioServ.borrarComentario(idTemp);

			if (resultado) {// 200
				return ResponseEntity.ok().body(new ErrorMsg(0, "El comentario ha sido borrado correctamente"));
			} else {// 500
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ErrorMsg(1, "No se ha podido borrar el comentario"));
			}
		} catch (Exception e) {// 400
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg(1, "ID con formato no valido"));
		}
	}

	// ------------------------------- MODIFICAR
	// COMENTARIO---------------------------
	// -------------------------------------------------------------------------------

	@PutMapping()
	@Operation(summary = "Modificar un comentario")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "El comentario ha sido modificado", content = @Content(schema = @Schema(implementation = ErrorMsg.class))),
			@ApiResponse(responseCode = "500", description = "No se ha podido modificar el comentario", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })
	public ResponseEntity<ErrorMsg> modificarComentario(@RequestBody Comentario comen) {
		boolean modificado = comentarioServ.modificarComentario(comen);

		if (modificado) {
			return ResponseEntity.ok().body(new ErrorMsg(0, "El comentario ha sido modificado correctamente"));
		} else {
			return ResponseEntity.internalServerError().body(new ErrorMsg(1, "No se pudo modificar el comentario"));
		}

	}

	// ------------------------ OBTENER COMENTARIO POR SU ID --------------------
	// ----------------------------------------------------------------------

	@GetMapping("/{id}")
	@Operation(summary = "Obtener un comentario, por su id en la barra de direcciones")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "comentario obtenido", content = @Content(schema = @Schema(implementation = Comentario.class))),

			@ApiResponse(responseCode = "404", description = "comentario no obtenido", content = @Content(schema = @Schema(implementation = ErrorMsg.class))),

			@ApiResponse(responseCode = "400", description = "Parametro mal puesto", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })

	public ResponseEntity<Object> obtenerComentarioPorId(@PathVariable String id) {

		try {
			int idTemp = Integer.parseInt(id.toString());

			Comentario comen = comentarioServ.obtenerComentarioPorId(idTemp);

			if (comen.getId() != 0) {// 200

				return ResponseEntity.ok().body(comen);

			} else {// 404
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ErrorMsg(1, "No se pudo encontar el comentario"));
			}
		} catch (Exception e) {// 400
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg(1, "ID con formato no valido"));

		}

	}

	// ------------------------ OBTENER TODAS LOS COMENTARIOS --------------------
	// -----------------------------------------------------------------------

	@GetMapping("")
	@Operation(summary = "Obtener todOS LOS COMENTARIOS")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "comentarios obtenidas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Comentario.class)))),
			@ApiResponse(responseCode = "404", description = "comentarios esta vacio", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })
	public ResponseEntity<Object> obtenerTodascategorias() {

		List<Comentario> comen = comentarioServ.obtenerTodosComentarios();

		if (comen.size() != 0) {// 200
			return ResponseEntity.ok().body(comen);
		} else {// 404
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorMsg(1, "No se pudo encontar los comentarios"));
		}
	}	

}
