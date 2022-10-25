package com.cluster.energia.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(schema = "cluste", name = "CATEGORIA")
@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Categoria implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "DESCRIPCION", nullable = false, length = 100)
	private String descripcion;

}
