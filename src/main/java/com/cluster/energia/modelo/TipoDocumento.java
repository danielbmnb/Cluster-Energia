package com.cluster.energia.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "TIPO_DOCUMENTO")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoDocumento implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "NOMBRE", nullable = false, length = 40)
	private String nombre;
	
}
