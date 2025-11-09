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

import com.ipartek.modelo.Localidad;
import com.ipartek.pojos.ErrorMsg;
import com.ipartek.servicios.LocalidadServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("api/localidades")
@Tag(name = "localidades", description = "Opreaciones relacionadas con las localidades")
public class LocalidadControlador {

	@Autowired
	private LocalidadServicio localidadServ;

	// -------------------- INSERTAR LOCALIDAD ----------------------------
	// ----------------------------------------------------------------

	@PostMapping()
	@Operation(summary = "Insertar una localidad")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "La localidad ha sido guardada correctamente"),
			@ApiResponse(responseCode = "500", description = "La localidad no se ha guardada") })
	public ResponseEntity<ErrorMsg> insertarLocalidad(@RequestBody Localidad locali) {
		boolean insertado = localidadServ.insertarLocalidad(locali);

		if (insertado) {
			return ResponseEntity.ok().body(new ErrorMsg(0, "Localidad insertada correctamente"));
		} else {
			return ResponseEntity.internalServerError()
					.body(new ErrorMsg(1, "Error, no se ha podido insertar la localidad"));
		}
	}

	// -------------------- BORRAR LOCALIDAD -------------------------
	// ---------------------------------------------------------------

	@DeleteMapping("/{id}")
	@Operation(summary = "Borrar una localidad, por su id en la barra de direcciones")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "localidad obtenida"),
			@ApiResponse(responseCode = "404", description = "localidad NO obtenida"),
			@ApiResponse(responseCode = "500", description = "Error") })
	public ResponseEntity<ErrorMsg> borrarLocalidad(@PathVariable String id) {
		try {
			int idTemp = Integer.parseInt(id.toString());

			boolean resultado = localidadServ.borrarLocalidad(idTemp);

			if (resultado) {// 200
				return ResponseEntity.ok().body(new ErrorMsg(0, "La localidad ha sido borrada correctamente"));
			} else {// 500
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ErrorMsg(1, "No se ha podido borrar la localidad"));
			}
		} catch (Exception e) {// 400
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg(1, "ID con formato no valido"));
		}
	}

	// ---------------------------------- MODIFICAR LOCALIDAD
	// ---------------------------
	// -------------------------------------------------------------------------------

	@PutMapping()
	@Operation(summary = "Modificar una localidad")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "La localidad ha sido modificada ", content = @Content(schema = @Schema(implementation = ErrorMsg.class))),
			@ApiResponse(responseCode = "500", description = "No se ha podido modificar la localidad", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })
	public ResponseEntity<ErrorMsg> modificarLocalidad(@RequestBody Localidad locali) {
		boolean modificado = localidadServ.modificarLocalidad(locali);

		if (modificado) {
			return ResponseEntity.ok().body(new ErrorMsg(0, "La localidad ha sido modificada correctamente"));
		} else {
			return ResponseEntity.internalServerError().body(new ErrorMsg(1, "No se pudo modificar la localidad"));
		}

	}

	// ------------------------ OBTENER LOCALIDAD POR SU ID --------------------
	// ----------------------------------------------------------------------

	@GetMapping("/{id}")
	@Operation(summary = "Obtener una localidad, por su id en la barra de direcciones")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Localidad obtenida", content = @Content(schema = @Schema(implementation = Localidad.class))),

			@ApiResponse(responseCode = "404", description = "Localidad no obtenida", content = @Content(schema = @Schema(implementation = ErrorMsg.class))),

			@ApiResponse(responseCode = "400", description = "Parametro mal puesto", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })

	public ResponseEntity<Object> obtenerUnaLocalidadId(@PathVariable String id) {

		try {
			int idTemp = Integer.parseInt(id.toString());

			Localidad locali = localidadServ.obtenerLocalidadPorId(idTemp);

			if (locali.getId() != 0) {// 200

				return ResponseEntity.ok().body(locali);

			} else {// 404
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ErrorMsg(1, "No se pudo encontar la localidad"));
			}
		} catch (Exception e) {// 400
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg(1, "ID con formato no valido"));

		}

	}
	
	// ------------------------ OBTENER TODAS LAS LOCALIDADES --------------------
			// -----------------------------------------------------------------------

			@GetMapping("")
			@Operation(summary = "Obtener todas las localidades")
			@ApiResponses({
					@ApiResponse(responseCode = "200", description = "Localidades obtenidas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Localidad.class)))),
					@ApiResponse(responseCode = "404", description = "Localidades esta vacio", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })
			public ResponseEntity<Object> obtenerTodasLocalidadades() {

				List<Localidad> locali = localidadServ.obtenerTodasLocalidades();

				if (locali.size() != 0) {// 200
					return ResponseEntity.ok().body(locali);
				} else {// 404
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMsg(1, "No se pudo encontar las Localidadades"));
				}
			}

}
