package com.cluster.energia.controlador;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cluster.energia.logicanegocio.ActualizarDatos;
import com.cluster.energia.logicanegocio.CategoriaService;
import com.cluster.energia.logicanegocio.ListadoYDetalleUsuarios;
import com.cluster.energia.logicanegocio.InicioSesionService;
import com.cluster.energia.logicanegocio.RegistroUsuariosService;
import com.cluster.energia.logicanegocio.TiposDocumentosService;
import com.cluster.energia.modelo.Categoria;
import com.cluster.energia.modelo.TipoDocumento;
import com.cluster.energia.modelo.TipoUsuario;
import com.cluster.energia.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(value = "/usuario")
@CrossOrigin
public class ClusterEnergiaController {

	@Autowired
	private ListadoYDetalleUsuarios clusterEnergiaService;

	@Autowired
	private ActualizarDatos actualizarDatosService;

	@Autowired
	private InicioSesionService inicioSesionService;

	@Autowired
	private RegistroUsuariosService registroUsuariosService;

	@Autowired
	private TiposDocumentosService tiposDocumentosService;

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping(value = "/listarUsuariosAdministrador")
	public ResponseEntity<List<Usuario>> listarUsuarios(@RequestHeader String Authorization) {

		if (Authorization != null) {

			var usuarios = clusterEnergiaService.listaUsuarios();

			if (!usuarios.isEmpty()) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarios);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(usuarios);
			}
		}
		return null;
	}

	@GetMapping(value = "/listarTiposUsuario")
	public ResponseEntity<List<TipoUsuario>> listarTiposUsuario() {

		var tiposUsuarios = clusterEnergiaService.listarTiposUsuario();

		if (!tiposUsuarios.isEmpty()) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(tiposUsuarios);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(tiposUsuarios);
		}
	}

	@PostMapping("/registrar")
	public ResponseEntity<Map<String, Object>> registrarUsuario(@RequestBody Usuario usuario) throws Exception {

		var registroUsuario = registroUsuariosService.registrarUsuarios(usuario);

		if (registroUsuario != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(registroUsuario);
		} else {
			throw new Exception("No se pudo registrar el usuario");
		}
	}

	@GetMapping("/cantidadUsuarios")
	public ResponseEntity<Map<String, Object>> cantidadUsuariosYAdministradores() {

		var cantidadUsuarios = clusterEnergiaService.cantidadUsuariosYAdministradores();

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(cantidadUsuarios);
	}

	@GetMapping("/cantidadTipoDocumentos")
	public ResponseEntity<Map<String, Object>> cantidadTipoDocumentos() {

		var cantidadTipoDocumentos = clusterEnergiaService.cantidadTipoDocumentos();

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(cantidadTipoDocumentos);
	}
	
	@GetMapping("/cantidadCategorias")
	public ResponseEntity<Map<String, Object>> cantidadCategorias() {

		var cantidadCategorias = clusterEnergiaService.cantidadCategorias();

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(cantidadCategorias);
	}
	
	@GetMapping("/tipoDocumento")
	public ResponseEntity<List<TipoDocumento>> tiposDocumentos() {

		var listaTiposDocuemntos = tiposDocumentosService.tiposDocumentos();

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(listaTiposDocuemntos);
	}

	@GetMapping("/detalleDocumento/{id}")
	public ResponseEntity<TipoDocumento> detalleTipoDocumento(@PathVariable Long id,
			@RequestHeader String Authorization) {

		if (Authorization != null) {

			var detalleDocumento = tiposDocumentosService.detalleTipoDocumento(id);

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(detalleDocumento);
		}
		return null;
	}

	@PostMapping("/guardarDocumento")
	public ResponseEntity<Map<String, Object>> crearDocumento(@RequestBody TipoDocumento tipoDocumento,
			@RequestHeader String Authorization) throws Exception {

		if (Authorization != null) {
			var documento = tiposDocumentosService.guardarTipoDocumento(tipoDocumento);

			if (documento != null) {

				return ResponseEntity.status(HttpStatus.ACCEPTED).body(documento);
			} else {
				throw new Exception("No se encontró el tipo de documento, por favor verifique");
			}
		}
		return null;
	}

	@PutMapping("/actualizarDocumento/{id}")
	public ResponseEntity<TipoDocumento> actualizarDocumento(@PathVariable Long id,
			@RequestBody TipoDocumento tipoDocumento, @RequestHeader String Authorization) throws Exception {

		if (Authorization != null) {
			var actualizarDocumento = tiposDocumentosService.actualizarDocumento(id, tipoDocumento);

			if (actualizarDocumento != null) {

				return ResponseEntity.status(HttpStatus.ACCEPTED).body(actualizarDocumento);
			} else {
				throw new Exception("No se encontró el tipo de documento, por favor verifique");
			}
		}
		return null;
	}

	@DeleteMapping("/eliminarDocumento/{id}")
	public ResponseEntity<HashMap<String, Object>> eliminarDocumento(@PathVariable Long id,
			@RequestHeader String Authorization) throws Exception {

		if (Authorization != null) {
			var eliminarDocumento = tiposDocumentosService.eliminarDocumento(id);

			if (eliminarDocumento != null) {

				return ResponseEntity.status(HttpStatus.ACCEPTED).body(eliminarDocumento);
			} else {
				throw new Exception("No se encontró el tipo de documento, por favor verifique");
			}
		}
		return null;
	}

	@GetMapping("/listarCategorias")
	public ResponseEntity<List<Categoria>> listarCategorias() {

		var listaCategoria = categoriaService.listarCategorias();

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(listaCategoria);
	}

	@GetMapping("/detalleCategoria/{id}")
	public ResponseEntity<Categoria> detalleCategoria(@PathVariable Long id, @RequestHeader String Authorization) {

		if (Authorization != null) {

			var detalleCategoria = categoriaService.detalleCategoria(id);

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(detalleCategoria);
		}
		return null;
	}

	@PostMapping("/guardarCategoria")
	public ResponseEntity<Map<String, Object>> crearCategoria(@RequestBody Categoria categoria,
			@RequestHeader String Authorization) throws Exception {

		if (Authorization != null) {
			var categorias = categoriaService.guardarCategoria(categoria);

			if (categoria != null) {

				return ResponseEntity.status(HttpStatus.ACCEPTED).body(categorias);
			} else {
				throw new Exception("No se encontró el tipo de documento, por favor verifique");
			}
		}
		return null;
	}

	@PutMapping("/actualizarCategoria/{id}")
	public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoria,
			@RequestHeader String Authorization) throws Exception {

		if (Authorization != null) {
			var actualizarCategoria = categoriaService.actualizarCategoria(id, categoria);

			if (actualizarCategoria != null) {

				return ResponseEntity.status(HttpStatus.ACCEPTED).body(actualizarCategoria);
			} else {
				throw new Exception("No se encontró el tipo de documento, por favor verifique");
			}
		}
		return null;
	}

	@DeleteMapping("/eliminarCategoria/{id}")
	public ResponseEntity<HashMap<String, Object>> eliminarCategoria(@PathVariable Long id,
			@RequestHeader String Authorization) throws Exception {

		if (Authorization != null) {
			var eliminarCategoria = categoriaService.eliminarCategoria(id);

			if (eliminarCategoria != null) {

				return ResponseEntity.status(HttpStatus.ACCEPTED).body(eliminarCategoria);
			} else {
				throw new Exception("No se encontró el tipo de documento, por favor verifique");
			}
		}
		return null;
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario,
			@RequestHeader String Authorization) throws Exception {

		if (Authorization != null) {
			var actualizarUsuario = actualizarDatosService.actualizarUsuario(id, usuario);

			if (actualizarUsuario != null) {

				return ResponseEntity.status(HttpStatus.ACCEPTED).body(actualizarUsuario);
			} else {
				throw new Exception("No se encontró el usuario, por favor verifique");
			}
		}
		return null;
	}

	@GetMapping("/detalleUsuario/{id}")
	public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestHeader String Authorization)
			throws Exception {

		if (Authorization != null) {

			var detalleUsuario = clusterEnergiaService.detalleUsuario(id);

			if (detalleUsuario != null) {

				return ResponseEntity.status(HttpStatus.ACCEPTED).body(detalleUsuario);
			} else {
				throw new Exception("No se pudo actualizar el usuario");
			}
		}
		return null;
	}

	@PostMapping("/login")
	public ResponseEntity<HashMap<String, Object>> login(@RequestBody Usuario usuario) throws Exception {

		var datosLogin = inicioSesionService.login(usuario);

		if (datosLogin != null) {

			var token = this.getJWTToken(usuario.getNit());

			if (token != null) {
				datosLogin.put("token", token);
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(datosLogin);
			}

		} else {
			throw new Exception("El usuario debe estar registraddo");
		}
		return null;
	}

	@DeleteMapping("/eliminarUsuario/{id}")
	public ResponseEntity<Map<String, Object>> eliminarUsuario(@PathVariable Long id,
			@RequestHeader String Authorization) {

		if (Authorization != null) {

			var usuarioEliminar = clusterEnergiaService.eliminarUsuario(id);

			return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioEliminar);
		}
		return null;
	}

	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("softtekJWT").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 900000000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
