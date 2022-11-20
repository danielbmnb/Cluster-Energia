package com.cluster.energia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cluster.energia.modelo.TipoDocumento;

@Repository
public interface TipoDocumentosRepository extends JpaRepository<TipoDocumento, Long>{

	@Query(value = "SELECT (COALESCE(MAX(id), 0) + 1) as CONSECUTIVO FROM cluste.tipo_documento", nativeQuery = true)
	public Long consecutivo();

	@Query(value = "SELECT COUNT(*) FROM cluste.tipo_documento WHERE nombre = :nombre", nativeQuery = true)
	public Integer tipoDocumentoExistente(@Param("nombre")String nombre);
	
	@Query(value = "SELECT COUNT(*) FROM cluste.tipo_documento", nativeQuery = true)
	public Integer cantidadDocumento();
}
