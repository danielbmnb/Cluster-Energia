package com.cluster.energia.logicanegocio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cluster.energia.modelo.Usuario;
import com.cluster.energia.repositorio.UsuarioRepository;

@Service
public class ListadoYDetalleUsuarios {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional(readOnly = true)
	public List<Usuario> listaUsuarios(){
		return usuarioRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Usuario detalleUsuario(Long id)throws Exception {
		
		var usuarioUnico = usuarioRepository.findById(id).orElse(null);
		
		if(usuarioUnico != null) {
			return usuarioUnico;
		}else {
			throw new Exception("No se encontró el usuario, por favor verifique");
		}
		
	}
}
