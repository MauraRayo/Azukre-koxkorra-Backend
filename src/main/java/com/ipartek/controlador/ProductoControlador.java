package com.ipartek.controlador;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ipartek.modelo.Producto;
import com.ipartek.pojos.ErrorMsg;
import com.ipartek.servicios.ProductoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con los productos")
public class ProductoControlador {

	@Autowired
	private ProductoServicio productoServ;

	// -------------------- INSERTAR PRODUCTO ----------------------------
	// ------------------------------------------------------------------

	@PostMapping()
	@Operation(summary = "Insertar un producto")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "El producto ha sido guardada correctamente"),
			@ApiResponse(responseCode = "500", description = "El producto no se ha guardada") })
	public ResponseEntity<ErrorMsg> insertarProducto(@RequestBody Producto produ) {

		boolean insertado = productoServ.insertarProducto(produ);

		if (insertado) {
			return ResponseEntity.ok().body(new ErrorMsg(0, "Producto insertado correctamente"));
		} else {
			return ResponseEntity.internalServerError()
					.body(new ErrorMsg(1, "Error, no se ha podido insertar el producto"));
		}
	}

	// -------------------- BORRAR PRODUCTO ----------------------------
	// ---------------------------------------------------------------

	@DeleteMapping("/{id}")
	@Operation(summary = "Borrar un producto, por su id en la barra de direcciones")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "producto obtenida"),
			@ApiResponse(responseCode = "404", description = "producto NO obtenida"),
			@ApiResponse(responseCode = "500", description = "Error") })
	public ResponseEntity<ErrorMsg> borrarProducto(@PathVariable String id) {
		try {
			int idTemp = Integer.parseInt(id.toString());

			boolean resultado = productoServ.borrarProducto(idTemp);

			if (resultado) {// 200
				return ResponseEntity.ok().body(new ErrorMsg(0, "EL producto ha sido borrada correctamente"));
			} else {// 500
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(new ErrorMsg(1, "No se ha podido borrar el producto"));
			}
		} catch (Exception e) {// 400
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg(1, "ID con formato no valido"));
		}
	}

	// -------------------------------- MODIFICAR PRODUCTO
	// --------------------------
	// -------------------------------------------------------------------------------

	@PutMapping()
	@Operation(summary = "Modificar un producto")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "La Producto ha sido modificada ", content = @Content(schema = @Schema(implementation = ErrorMsg.class))),
			@ApiResponse(responseCode = "500", description = "No se ha podido modificar el producto", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })
	public ResponseEntity<ErrorMsg> modificarProducto(@RequestBody Producto produ) {
		boolean modificado = productoServ.modificarProducto(produ);

		if (modificado) {
			return ResponseEntity.ok().body(new ErrorMsg(0, "El producto ha sido modificado correctamente"));
		} else {
			return ResponseEntity.internalServerError().body(new ErrorMsg(1, "No se pudo modificar el producto"));
		}
	}

	// ------------------------ OBTENER PRODUCTO POR SU ID --------------------
	// ----------------------------------------------------------------------

	@GetMapping("/{id}")
	@Operation(summary = "Obtener un producto, por su id en la barra de direcciones")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "producto obtenida", content = @Content(schema = @Schema(implementation = Producto.class))),

			@ApiResponse(responseCode = "404", description = "Producto no obtenida", content = @Content(schema = @Schema(implementation = ErrorMsg.class))),

			@ApiResponse(responseCode = "400", description = "Parametro mal puesto", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })

	public ResponseEntity<Object> obtenerUnaProductoId(@PathVariable String id) {

		try {
			int idTemp = Integer.parseInt(id.toString());

			Producto produ = productoServ.obtenerProductoPorId(idTemp);

			if (produ.getId() != 0) {// 200

				return ResponseEntity.ok().body(produ);

			} else {// 404
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ErrorMsg(1, "No se pudo encontar la Producto"));
			}
		} catch (Exception e) {// 400
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMsg(1, "ID con formato no valido"));

		}

	}

	// ---------------------- OBTENER TODOS LOS PRODUCTOS --------------------
	// -----------------------------------------------------------------------

	@GetMapping("")
	@Operation(summary = "Obtener todos los productos")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Productos obtenidas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Producto.class)))),
			@ApiResponse(responseCode = "404", description = "Productos esta vacio", content = @Content(schema = @Schema(implementation = ErrorMsg.class))) })
	public ResponseEntity<Object> obtenerTodasProductos() {

		List<Producto> produ = productoServ.obtenerTodosProductos();

		if (produ.size() != 0) {// 200
			return ResponseEntity.ok().body(produ);
		} else {// 404
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorMsg(1, "No se pudo encontar las Productos"));
		}
	}

}
