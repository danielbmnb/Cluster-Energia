package com.cluster.energia.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(schema = "cluste", name = "USUARIO")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", unique = true)
	private Long id;
	
	@OneToOne
    @JoinColumn(name="ID_TIPO_DOCUMENTO", nullable = false)
	private TipoDocumento tipoDocumento;
	
	@OneToOne
    @JoinColumn(name="ID_TIPO_USUARIO", nullable = false)
	private TipoUsuario tipoUsuario;
	
	@Column(name = "NOMBRE", nullable = false, length = 40)
	private String nombre;
	
	@Column(name = "APELLIDO", nullable = false, length = 40)
	private String apellido;
	
	@Column(name = "TELEFONO", nullable = true, length = 20)
	private String telefono;
	
	@Column(name = "CORREO", nullable = false, length = 100, unique = true)
	private String correo;
	
	@Column(name = "EMPRESA", nullable = true, length = 200)
	private String empresa;
	
	@Column(name = "NIT", nullable = false, length = 40, unique = true)
	private String nit;	

	@Column(name = "PASSWORD", nullable = false, length = 50)
	private String password;
	
	@Column(name = "CIUDAD", nullable = false, length = 40)
	private String ciudad;
	
	@Column(name = "PAIS", nullable = false, length = 40)
	private String pais;
	
	@Column(name = "CARGO", nullable = true, length = 40)
	private String cargo;
	
	@OneToOne
    @JoinColumn(name="ID_CATEGORIA", nullable = false)
	private Categoria categoria;
	
	@Column(name = "FECHA_EXPEDICION")
	private Date fechaExpedicion;
}
