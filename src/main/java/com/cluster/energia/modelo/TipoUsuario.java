package com.cluster.energia.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "TIPO_USUARIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class TipoUsuario implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private Long id;
	
	@Column(name = "NOMBRE", nullable = false, length = 40)
	private String nombre;
	
}
