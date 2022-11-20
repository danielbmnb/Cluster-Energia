package com.cluster.energia.logicanegocio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cluster.energia.modelo.TipoDocumento;
import com.cluster.energia.repositorio.TipoDocumentosRepository;

@Service
public class TiposDocumentosService {

	@Autowired
	private TipoDocumentosRepository tipoDocumentosRepository;

	@Transactional(readOnly = true)
	public List<TipoDocumento> tiposDocumentos() {
		return tipoDocumentosRepository.findAll();
	}

	@Transactional(readOnly = true)
	public TipoDocumento detalleTipoDocumento(Long id) {
		return tipoDocumentosRepository.findById(id).orElse(null);
	}

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> guardarTipoDocumento(TipoDocumento datosGuardar) {

		var retornoServicio = new HashMap<String, Object>();

		try {

			if (datosGuardar.getNombre() == null || datosGuardar.getNombre().equalsIgnoreCase("") ||
				datosGuardar.getNombre().length() <= 0) {
				retornoServicio.put("tipoDocumentoRequerido", "El tipo de documento es obligatorio");
				return retornoServicio;
			}
			
			var validarTipoDocumento = tipoDocumentosRepository.tipoDocumentoExistente(datosGuardar.getNombre());
			
			if(validarTipoDocumento >= 1) {
				retornoServicio.put("tipoDocumentoExistente", "El tipo de documento " + datosGuardar.getNombre() + " ya existe");
				return retornoServicio;
			}
			
			Long consecutivo = tipoDocumentosRepository.consecutivo();
			datosGuardar.setId(consecutivo);

			tipoDocumentosRepository.save(datosGuardar);

		} catch (Exception e) {
			retornoServicio.put("resultadoTransaccion", -1);
			retornoServicio.put("error", "Ha ocurrido un error al guardar, vuelva a intentarlo");
		}

		retornoServicio.put("resultadoTransaccion", 1);
		retornoServicio.put("mensajeExito", "El tipo de documento se ha guardado exitosamente");
		retornoServicio.put("resultado", datosGuardar);
		return retornoServicio;

	}

	@Transactional(rollbackFor = Exception.class)
	public TipoDocumento actualizarDocumento(Long id, TipoDocumento actualizarDatos) throws Exception {

		var actualizarDocumento = tipoDocumentosRepository.findById(id).orElse(null);

		if (actualizarDocumento != null) {
			actualizarDatos.setId(actualizarDocumento.getId());
			tipoDocumentosRepository.save(actualizarDatos);
		} else {
			throw new Exception("El tipo de documento no existe, por favor verifique");
		}

		return actualizarDocumento;
	}

	@Transactional(rollbackFor = Exception.class)
	public HashMap<String, Object> eliminarDocumento(Long id) {

		var retornoServicio = new HashMap<String, Object>();

		var documentoEliminado = tipoDocumentosRepository.findById(id).orElse(null);

		if (documentoEliminado != null) {
			tipoDocumentosRepository.deleteById(id);
		} else {
			retornoServicio.put("documentoNoEncontrado",
					"El tipo de documento no se ha encontrado, por favor verifique");
			return retornoServicio;
		}

		retornoServicio.put("documentoEliminado", "El tipo de documento ha sido eliminado");
		return retornoServicio;
	}

}
