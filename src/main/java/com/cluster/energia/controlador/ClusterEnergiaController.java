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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cluster.energia.logicanegocio.ActualizarDatos;
import com.cluster.energia.logicanegocio.ListadoYDetalleUsuarios;
import com.cluster.energia.logicanegocio.InicioSesionService;
import com.cluster.energia.logicanegocio.RegistroUsuariosService;
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
	
	
	@GetMapping(value = "/listar")
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

	@PostMapping("/registrar")
	public ResponseEntity<Map<String, Object>> registrarUsuario(@RequestBody Usuario usuario)
			throws Exception {

			var registroUsuario = registroUsuariosService.registrarUsuarios(usuario);

			if (registroUsuario != null) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(registroUsuario);
			} else {
				throw new Exception("No se pudo registrar el usuario");
			}
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
