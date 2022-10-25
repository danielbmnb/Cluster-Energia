package com.cluster.energia.logicanegocio;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cluster.energia.modelo.Usuario;
import com.cluster.energia.repositorio.UsuarioRepository;

@Service
public class InicioSesionService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public HashMap<String, Object> login(Usuario usuario) throws Exception {

		var datosLogin = new HashMap<String, Object>();
		var existeUsuario = false;
		var logeado = true;
		var informacionUsuario = new Usuario();
		// primero validamos si el usuario ya existe

		var usuarioExistente = usuarioRepository.existeUsuario(usuario.getNit());

		if (usuarioExistente == 0) {
			throw new Exception(
					"El usuario con identificación " + usuario.getNit() + " no existe, por favor registre el usuario");
		} else {

			logeado = true;
			informacionUsuario = usuarioRepository.informacionUsuario(usuario.getNit());

			if (informacionUsuario != null) {
				existeUsuario = true;
				datosLogin.put("usuario", informacionUsuario);
			}

		}

		datosLogin.put("logeado", logeado);
		datosLogin.put("existeUsuario", existeUsuario);

		return datosLogin;

	}

}
