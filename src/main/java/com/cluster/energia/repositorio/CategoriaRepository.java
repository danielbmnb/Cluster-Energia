package com.cluster.energia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cluster.energia.modelo.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	@Query(value = "SELECT (COALESCE(MAX(id), 0) + 1) as CONSECUTIVO FROM cluste.categoria", nativeQuery = true)
	public Long consecutivo();

	@Query(value = "SELECT COUNT(*) FROM cluste.categoria WHERE descripcion = :descripcion", nativeQuery = true)
	public Integer categoriaExistente(@Param("descripcion")String descripcion);
	
	@Query(value = "SELECT COUNT(*) FROM cluste.categoria", nativeQuery = true)
	public Integer cantidadCategoria();
}
