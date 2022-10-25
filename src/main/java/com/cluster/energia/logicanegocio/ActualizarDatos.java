package com.cluster.energia.logicanegocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cluster.energia.modelo.Usuario;
import com.cluster.energia.repositorio.UsuarioRepository;

@Service
public class ActualizarDatos {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario actualizarUsuario(Long id, Usuario actualizarDatos) throws Exception {

		var actualizarUsuario = usuarioRepository.findById(id).orElse(null);

		if (actualizarUsuario != null) {
			actualizarDatos.setId(actualizarUsuario.getId());
			usuarioRepository.save(actualizarDatos);
		} else {
			throw new Exception("El usuario no existe, por favor verifique");
		}

		return actualizarUsuario;
	}

}
