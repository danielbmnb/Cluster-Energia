package com.cluster.energia.logicanegocio;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cluster.energia.modelo.Usuario;
import com.cluster.energia.repositorio.UsuarioRepository;

@Service
public class RegistroUsuariosService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Map<String, Object> registrarUsuarios(Usuario usuario) throws Exception {

		var retornoServicio = new HashMap<String, Object>();

		var usuarioRegistrado = this.validarUsuarioRegistrado(usuario);
				
		if (usuarioRegistrado == true) {
			return null;
		} else {
			
			var validacionDatos = this.validarDatos(usuario);
			
			if(validacionDatos.get("datosNoValidos").equals(true)) {
				
				retornoServicio.put("validacionesEncontradas", validacionDatos);
				retornoServicio.put("resultadoTransaccion", -1);
				
				return retornoServicio;
			}
			
			var id = usuarioRepository.consecutivo();
			usuario.setId(id);
			usuarioRepository.save(usuario);
		}
		
		retornoServicio.put("resultadoTransaccion", 1);
		retornoServicio.put("usuarioRegistrado", "El usuario se ha creado éxitosamente");
		retornoServicio.put("datosGuardados", usuario);
		return retornoServicio;
	}

	private boolean validarUsuarioRegistrado(Usuario usuario) throws Exception {
		var existe = false;

		var registrado = usuarioRepository.validarUsuarioRegistrado(usuario.getCorreo());

		if (registrado >= 1) {
			throw new Exception("El correo " + usuario.getCorreo() + " ya se encuentra registrado");
		}

		return existe;
	}
	
	private Map<String, Object> validarDatos(Usuario usuario){
		
		var datosValidados = new HashMap<String, Object>();
		var datosNoValidos = false;
		
		if(usuario.getNombre() == null || usuario.getNombre().length() <= 0 ||
				usuario.getNombre().equalsIgnoreCase("")) {
			datosValidados.put("nombreRequerido", "El nombre es obligatorio");
			datosNoValidos = true;
		}
		
		if(usuario.getApellido() == null || usuario.getApellido().length() <= 0 ||
				usuario.getApellido().equalsIgnoreCase("")) {
			datosValidados.put("apellidoRequerido", "El apellido es obligatorio");
			datosNoValidos = true;
		}
		
		if(usuario.getTipoDocumento() == null || usuario.getTipoDocumento().getId() == null) {
			datosValidados.put("tipoDocumentoRequerido", "El tipo de documento es obligatorio");
			datosNoValidos = true;
		}
		
		if(usuario.getNit() == null || usuario.getNit().length() <= 0 ||
				usuario.getNit().equalsIgnoreCase("")) {
			datosValidados.put("nitRequerido", "El nit es obligatorio");
			datosNoValidos = true;
		}
		
		if(usuario.getFechaExpedicion() == null) {
			datosValidados.put("fechaExpedicionRequerido", "La fecha de expedición del documento es obligatoria");
			datosNoValidos = true;
		}
		
		if(usuario.getPassword() == null || usuario.getPassword().length() <= 0 ||
				usuario.getPassword().equalsIgnoreCase("")) {
			datosValidados.put("passwordRequerido", "La contraseña es obligatoria");
			datosNoValidos = true;
		}
		
		if(usuario.getCorreo() == null || usuario.getCorreo().length() <= 0 ||
				usuario.getCorreo().equalsIgnoreCase("")) {
			datosValidados.put("correoRequerido", "El correo es obligatorio");
			datosNoValidos = true;
		}
		
		if(usuario.getEmpresa() == null || usuario.getEmpresa().length() <= 0 ||
				usuario.getEmpresa().equalsIgnoreCase("")) {
			datosValidados.put("empresaRequerido", "La empresa es obligatoria");
			datosNoValidos = true;
		}
		
		if(usuario.getCiudad() == null || usuario.getCiudad().length() <= 0 ||
				usuario.getCiudad().equalsIgnoreCase("")) {
			datosValidados.put("ciudadRequerido", "La ciudad es obligatoria");
			datosNoValidos = true;
		}
		
		if(usuario.getPais() == null || usuario.getPais().length() <= 0 ||
				usuario.getPais().equalsIgnoreCase("")) {
			datosValidados.put("paisRequerido", "El país es obligatorio");
			datosNoValidos = true;
		}
		
		if(usuario.getCargo() == null || usuario.getCargo().length() <= 0 ||
				usuario.getCargo().equalsIgnoreCase("")) {
			datosValidados.put("cargoRequerido", "El cargo es obligatorio");
			datosNoValidos = true;
		}
		
		if(usuario.getCategoria() == null || usuario.getCategoria().getId() == null) {
			datosValidados.put("categoriaRequerido", "La categoria es obligatoria");
			datosNoValidos = true;
		}
		
		if(usuario.getTelefono() == null) {
			datosValidados.put("telefonoRequerido", "El telefono es obligatorio");
			datosNoValidos = true;
		}

		
		datosValidados.put("datosNoValidos", datosNoValidos);
		return datosValidados;
	}
	
}
