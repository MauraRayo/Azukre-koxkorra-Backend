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

import com.ipartek.modelo.Categoria;
import com.ipartek.pojos.ErrorMsg;
import com.ipartek.servicios.CategoriaServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorias", description = "Opreaciones relacionadas con las categorias")
public class CategoriaControlador {
	
	@Autowired
	private CategoriaServicio categoriaServ;
	
	
	// -------------------- INSERTAR CATEGORIA ----------------------------
	//  ----------------------------------------------------------------
		
		@PostMapping()
		@Operation(summary = "Insertar una categoria")
		@ApiResponses({ @ApiResponse(responseCode = "200", description = "La categoria ha sido guardada correctamente"),
				@ApiResponse(responseCode = "500", description = "La categoria no se ha guardada") })
		public ResponseEntity<ErrorMsg> insertarCategoria(@RequestBody Categoria catego) {
			boolean insertado = categoriaServ.insertarCategoria(catego);

			if (insertado) {
				return ResponseEntity.ok().body(new ErrorMsg(0, "Categoria insertada correctamente"));
			} else {
				return ResponseEntity.internalServerError()
						.body(new ErrorMsg(1, "Error, no se ha podido insertar la categoria"));
			}
		}
		
		
		
		// -------------------- BORRAR CATEGORIA ----------------------------
		// ---------------------------------------------------------------
		
		@DeleteMapping("/{id}")
		@Operation(summary = "Borrar una categoria, por su id en la barra de direcciones")
		@ApiResponses({ @ApiResponse(responseCode = "200", description = "categoria obtenida"),
				@ApiResponse(responseCode = "404", description = "categoria NO obtenida"),
				@ApiResponse(responseCode = "500", description = "Error") })
		public ResponseEntity<ErrorMsg> borrarCategoria(@PathVariable String id) {
			try {
				int idTemp = Integer.parseInt(id.toString());

				boolean resultado = categoriaServ.borrarCategoria(idTemp);

				if (resultado) {// 200
					return ResponseEntity.ok().body(new ErrorMsg(0, "La categoria ha sido borrada correctamente"));
				} else {// 500
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body(new ErrorMsg(1, "No se ha podido borrar la categoria"));
				}
			} catch (Exception e) {// 400
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg(1, "ID con formato no valido"));
			}
		}	
		
		// ---------------------------------- MODIFICAR CATEGORIA ---------------------------
		// -------------------------------------------------------------------------------

		@PutMapping()
		@Operation(summary = "Modificar una categoria")
		@ApiResponses({
				@ApiResponse(responseCode = "200", description = "La categoria ha sido modificada ", content = @Content(schema = @Schema(implementation = ErrorMsg.class))),
				@ApiResponse(responseCode = "500", description = "No se ha podido modificar la categoria", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })
		public ResponseEntity<ErrorMsg> modificarCategoria(@RequestBody Categoria catego) {
			boolean modificado = categoriaServ.modificarCategoria(catego);

			if (modificado) {
				return ResponseEntity.ok().body(new ErrorMsg(0, "La categoria ha sido modificada correctamente"));
			} else {
				return ResponseEntity.internalServerError().body(new ErrorMsg(1, "No se pudo modificar la categoria"));
			}

		}
		
		// ------------------------ OBTENER CATEGORIA POR SU ID --------------------
		// ----------------------------------------------------------------------

		@GetMapping("/{id}")
		@Operation(summary = "Obtener una categoria, por su id en la barra de direcciones")
		@ApiResponses({
				@ApiResponse(responseCode = "200", description = "Categoria obtenida", content = @Content(schema = @Schema(implementation = Categoria.class))),

				@ApiResponse(responseCode = "404", description = "Categoria no obtenida", content = @Content(schema = @Schema(implementation = ErrorMsg.class))),

				@ApiResponse(responseCode = "400", description = "Parametro mal puesto", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })

		public ResponseEntity<Object> obtenerUnaCategoriaId(@PathVariable String id) {

			try {
				int idTemp = Integer.parseInt(id.toString());

				Categoria catego = categoriaServ.obtenerCategoriaPorId(idTemp);

				if (catego.getId() != 0) {// 200

					return ResponseEntity.ok().body(catego);

				} else {// 404
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body(new ErrorMsg(1, "No se pudo encontar la categoria"));
				}
			} catch (Exception e) {// 400
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg(1, "ID con formato no valido"));

			}

		}

		// ------------------------ OBTENER TODAS LAS CATEGORIAS --------------------
		// -----------------------------------------------------------------------

		@GetMapping("")
		@Operation(summary = "Obtener todas las categorias")
		@ApiResponses({
				@ApiResponse(responseCode = "200", description = "categorias obtenidas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Categoria.class)))),
				@ApiResponse(responseCode = "404", description = "categorias esta vacio", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })
		public ResponseEntity<Object> obtenerTodascategorias() {

			List<Categoria> catego = categoriaServ.obtenerTodasCategorias();

			if (catego.size() != 0) {// 200
				return ResponseEntity.ok().body(catego);
			} else {// 404
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMsg(1, "No se pudo encontar las categorias"));
			}
		}
		
		
}
