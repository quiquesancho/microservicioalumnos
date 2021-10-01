package edu.qui.microservicios.microservicioalumnos.modelo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "alumnos")
public class Alumno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Size(min = 3)
	private String nombre;
	
	@NotEmpty
	private String apellido;
	
	@NotEmpty
	@Email
	private String email;
	
	@Min(1)
	private int edad;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_at;
	
	@Lob
	@JsonIgnore
	private byte[] foto;
	
	
	public Alumno(int id, String nombre, String apellido, String email, int edad, Date created_at) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.edad = edad;
		this.created_at = created_at;
	}
	
	public Alumno() {
		
	}

	@PrePersist
	private void generarFecha() {
		this.created_at = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] bs) {
		this.foto = bs;
	}
	
	public Integer getFotoHashCode() {
		Integer idev = null;
		
		if (this.foto != null) {
			idev = this.foto.hashCode();
		}
		
		return idev;
	}

	@Override
	public String toString() {
		return "Alumno [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", email=" + email + ", edad="
				+ edad + ", created_at=" + created_at + "]";
	}
}
