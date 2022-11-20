package com.cluster.energia.logicanegocio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cluster.energia.modelo.Categoria;
import com.cluster.energia.repositorio.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Transactional(readOnly = true)
	public List<Categoria> listarCategorias() {
		return categoriaRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Categoria detalleCategoria(Long id) {
		return categoriaRepository.findById(id).orElse(null);
	}

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> guardarCategoria(Categoria datosGuardar) {

		var retornoServicio = new HashMap<String, Object>();

		try {

			if (datosGuardar.getDescripcion() == null || datosGuardar.getDescripcion().equalsIgnoreCase("") ||
				datosGuardar.getDescripcion().length() <= 0) {
				retornoServicio.put("categoriaRequerida", "La categoria es obligatoria");
				return retornoServicio;
			}
			
			var validarCategoria = categoriaRepository.categoriaExistente(datosGuardar.getDescripcion());
			
			if(validarCategoria >= 1) {
				retornoServicio.put("categoriaExistente", "El tipo de documento " + datosGuardar.getDescripcion() + " ya existe");
				return retornoServicio;
			}
			
			Long consecutivo = categoriaRepository.consecutivo();
			datosGuardar.setId(consecutivo);

			categoriaRepository.save(datosGuardar);

		} catch (Exception e) {
			retornoServicio.put("resultadoTransaccion", -1);
			retornoServicio.put("error", "Ha ocurrido un error al guardar, vuelva a intentarlo");
		}

		retornoServicio.put("resultadoTransaccion", 1);
		retornoServicio.put("mensajeExito", "La categoria se ha guardado exitosamente");
		retornoServicio.put("resultado", datosGuardar);
		return retornoServicio;

	}

	@Transactional(rollbackFor = Exception.class)
	public Categoria actualizarCategoria(Long id, Categoria actualizarDatos) throws Exception {

		var actualizarCategoria = categoriaRepository.findById(id).orElse(null);

		if (actualizarCategoria != null) {
			actualizarDatos.setId(actualizarCategoria.getId());
			categoriaRepository.save(actualizarDatos);
		} else {
			throw new Exception("La categoria no existe, por favor verifique");
		}

		return actualizarCategoria;
	}

	@Transactional(rollbackFor = Exception.class)
	public HashMap<String, Object> eliminarCategoria(Long id) {

		var retornoServicio = new HashMap<String, Object>();

		var categoriaEliminado = categoriaRepository.findById(id).orElse(null);

		if (categoriaEliminado != null) {
			categoriaRepository.deleteById(id);
		} else {
			retornoServicio.put("categoriaNoEncontrado",
					"La categoria no se ha encontrado, por favor verifique");
			return retornoServicio;
		}

		retornoServicio.put("categoriaEliminado", "La categoria se ha sido eliminado");
		return retornoServicio;
	}

	
}
