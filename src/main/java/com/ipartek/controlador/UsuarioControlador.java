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
import org.springframework.web.server.ResponseStatusException;

import com.ipartek.modelo.Usuario;
import com.ipartek.pojos.ErrorMsg;
import com.ipartek.servicios.UsuarioServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "usuarios", description = "Operaciones relacionadas con los usuarios")
public class UsuarioControlador {

	@Autowired
	private UsuarioServicio usuarioServ;

	@GetMapping("/validar/{usu}/{pas}")
	public boolean validarUsuario(@PathVariable String usu, @PathVariable String pas){
		return usuarioServ.validarUsuario(usu, pas);	
		
	}
	
	@GetMapping("/nombre/{name}")
	public Usuario obtenerUsuarioPorNombre(@PathVariable String name) {
	    Usuario usu = usuarioServ.obtenerUsuarioPorNombre(name);
	    if (usu != null) {
	        return usu;
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
	    }
	}

	
	// -------------------- INSERTAR USUARIO ----------------------------
	// ----------------------------------------------------------------

	@PostMapping()
	@Operation(summary = "Insertar un usuario")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "El usuario ha sido guardado correctamente"),
			@ApiResponse(responseCode = "500", description = "El usuario no se ha guardado") })
	public ResponseEntity<ErrorMsg> insertarUsuario(@RequestBody Usuario usu) {
		boolean insertado = usuarioServ.insertarUsuario(usu);

		if (insertado) {
			return ResponseEntity.ok().body(new ErrorMsg(0, "Usuario insertado correctamente"));
		} else {
			return ResponseEntity.internalServerError()
					.body(new ErrorMsg(1, "Error, no se ha podido insertar el usuario"));
		}
	}

	// -------------------- BORRAR USUARIO --------------------------
	// --------------------------------------------------------------

	@DeleteMapping("/{id}")
	@Operation(summary = "Borrar un usuario, por su id en la barra de direcciones")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "usuario obtenido"),
			@ApiResponse(responseCode = "404", description = "usuario NO obtenido"),
			@ApiResponse(responseCode = "500", description = "Error") })
	public ResponseEntity<ErrorMsg> borrarUsuario(@PathVariable String id) {
		try {
			int idTemp = Integer.parseInt(id.toString());

			boolean resultado = usuarioServ.borrarUsuario(idTemp);

			if (resultado) {// 200
				return ResponseEntity.ok().body(new ErrorMsg(0, "El usuario ha sido borrado correctamente"));
			} else {// 500
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ErrorMsg(1, "No se ha podido borrar el usuario"));
			}
		} catch (Exception e) {// 400
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg(1, "ID con formato no valido"));
		}
	}

	// --------------------------------- MODIFICAR USUARIO
	// ---------------------------
	// -------------------------------------------------------------------------------

	@PutMapping()
	@Operation(summary = "Modificar un Usuario")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "La categoria ha sido modificada ", content = @Content(schema = @Schema(implementation = ErrorMsg.class))),
			@ApiResponse(responseCode = "500", description = "No se ha podido modificar la categoria", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })
	public ResponseEntity<ErrorMsg> modificarUsuario(@RequestBody Usuario usu) {
		boolean modificado = usuarioServ.modificarUsuario(usu);

		if (modificado) {
			return ResponseEntity.ok().body(new ErrorMsg(0, "El usuario ha sido modificado correctamente"));
		} else {
			return ResponseEntity.internalServerError().body(new ErrorMsg(1, "No se pudo modificar el usuario"));
		}
	}

	// ----------------------- OBTENER USUARIO POR SU ID --------------------
	// ----------------------------------------------------------------------

	@GetMapping("/{id}")
	@Operation(summary = "Obtener un usuario, por su id en la barra de direcciones")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Usuario obtenido", content = @Content(schema = @Schema(implementation = Usuario.class))),

			@ApiResponse(responseCode = "404", description = "Usuario no obtenido", content = @Content(schema = @Schema(implementation = ErrorMsg.class))),

			@ApiResponse(responseCode = "400", description = "Parametro mal puesto", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })

	public ResponseEntity<Object> obtenerUnaUsuariosId(@PathVariable String id) {

		try {
			int idTemp = Integer.parseInt(id.toString());

			Usuario usu = usuarioServ.obtenerUsuarioPorId(idTemp);

			if (usu.getId() != 0) {// 200

				return ResponseEntity.ok().body(usu);

			} else {// 404
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ErrorMsg(1, "No se pudo encontar la Usuarios"));
			}
		} catch (Exception e) {// 400
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg(1, "ID con formato no valido"));

		}

	}

	// ----------------------- OBTENER TODOS LOS USUARIOS --------------------
	// -----------------------------------------------------------------------

	@GetMapping("")
	@Operation(summary = "Obtener todos los usuarios")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Usuarios obtenidas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Usuario.class)))),
			@ApiResponse(responseCode = "404", description = "Usuarios esta vacio", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })
	public ResponseEntity<Object> obtenerTodosUsuarios() {

		List<Usuario> usu = usuarioServ.obtenerTodosUsuario();

		if (usu.size() != 0) {// 200
			return ResponseEntity.ok().body(usu);
		} else {// 404
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorMsg(1, "No se pudo encontar las Usuarios"));
		}
	}

}
