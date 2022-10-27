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

		var usuarioExistente = usuarioRepository.existeUsuario(usuario.getNit());

		if (usuarioExistente == 0) {

			datosLogin.put("usuarioNoEncontrado",
					"El usuario con identificación " + usuario.getNit() + " no existe, por favor registre el usuario");
			logeado = false;
			return datosLogin;

		} else {

			var passwordActual = usuarioRepository.password(usuario.getNit());

			if (passwordActual != null && passwordActual.equalsIgnoreCase(usuario.getPassword())) {

				logeado = true;
				informacionUsuario = usuarioRepository.informacionUsuario(usuario.getNit());

				if (informacionUsuario != null) {
					existeUsuario = true;
					datosLogin.put("usuario", informacionUsuario);
				}
			} else {

				datosLogin.put("passwordIncorrecta", "La contraseña es incorrecta, por favor verifique");
				return datosLogin;
			}

		}

		datosLogin.put("logeado", logeado);
		datosLogin.put("existeUsuario", existeUsuario);

		return datosLogin;

	}

}
