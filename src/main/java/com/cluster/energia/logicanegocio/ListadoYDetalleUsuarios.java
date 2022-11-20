package com.cluster.energia.logicanegocio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cluster.energia.constantes.Constantes;
import com.cluster.energia.modelo.TipoUsuario;
import com.cluster.energia.modelo.Usuario;
import com.cluster.energia.repositorio.CategoriaRepository;
import com.cluster.energia.repositorio.TipoDocumentosRepository;
import com.cluster.energia.repositorio.TipoUsuarioRepository;
import com.cluster.energia.repositorio.UsuarioRepository;

@Service
public class ListadoYDetalleUsuarios {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TipoUsuarioRepository tipoUsuarioRepository;
	
	@Autowired
	private TipoDocumentosRepository tipoDocumentoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Transactional(readOnly = true)
	public List<Usuario> listaUsuarios(){
		return usuarioRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<TipoUsuario> listarTiposUsuario(){
		return tipoUsuarioRepository.findAll();
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
	
	@Transactional(readOnly = true)
	public Map<String, Object> cantidadUsuariosYAdministradores(){
	
		var retornoServicio = new HashMap<String, Object>();
		
		var cantidadUsuarios = usuarioRepository.cantidadUsuariosYAdministradores(Constantes.USUARIO);
		var cantidadAdministradores = usuarioRepository.cantidadUsuariosYAdministradores(Constantes.ADMINISTRADOR);

		retornoServicio.put("cantidadUsuarios", cantidadUsuarios);
		retornoServicio.put("cantidadAdministradores", cantidadAdministradores);

		return retornoServicio;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> cantidadTipoDocumentos(){
	
		var retornoServicio = new HashMap<String, Object>();
		
		var cantidadDocumentos = tipoDocumentoRepository.cantidadDocumento();

		retornoServicio.put("cantidadDocumentos", cantidadDocumentos);

		return retornoServicio;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> cantidadCategorias(){
	
		var retornoServicio = new HashMap<String, Object>();
		
		var cantidadCategorias = categoriaRepository.cantidadCategoria();

		retornoServicio.put("cantidadCategorias", cantidadCategorias);

		return retornoServicio;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public HashMap<String, Object> eliminarUsuario(Long id){
		
		var retornoServicio = new HashMap<String, Object>();
		
		var usuarioEliminar = usuarioRepository.findById(id).orElse(null);
		
		if(usuarioEliminar != null) {
			usuarioRepository.deleteById(id);
		}else {
			retornoServicio.put("usuarioEliminarNoEncontrado", "El usuario no se ha encontrado, por favor verifique");
			return retornoServicio;
		}
		
		retornoServicio.put("usuarioEliminado", "El usuario ha sido eliminado");
		return retornoServicio;
	}
	
}
