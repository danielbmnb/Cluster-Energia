package com.cluster.energia.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cluster.energia.modelo.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	@Query(value = "SELECT COUNT(*) FROM cluste.usuario WHERE CORREO = :correo", nativeQuery = true)
	public Integer validarUsuarioRegistrado(@Param("correo")String correo);
	
	@Query(value = "SELECT (COALESCE(MAX(id), 0) + 1) as CONSECUTIVO FROM cluste.usuario", nativeQuery = true)
	public Long consecutivo();
	
	@Query(value = "SELECT COUNT(*) FROM cluste.usuario WHERE NIT = :nit", nativeQuery = true)
	public Integer existeUsuario(@Param("nit")String nit);
	
	@Query(value = "SELECT * FROM cluste.usuario WHERE NIT = :nit", nativeQuery =  true)
	public Usuario informacionUsuario(@Param("nit")String nit);
	
	@Query(value = "SELECT password FROM cluste.usuario WHERE NIT = :nit", nativeQuery =  true)
	public String password(@Param("nit")String nit);
	
	@Query(value = "SELECT COUNT(*) FROM USUARIO WHERE id_tipo_usuario = :idTipoUsuario", nativeQuery = true)
	public Integer cantidadUsuariosYAdministradores(@Param("idTipoUsuario")Long idTipoUsuario);
	
	@Query(value = "SELECT * FROM USUARIO WHERE id_tipo_usuario = :usuario", nativeQuery = true)
	public List<Usuario> usuarios(@Param("usuario")Long usuario);
}
