package edu.qui.microservicios.microservicioalumnos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.qui.microservicios.microserviciocomun.model.entity.Alumno;

public interface AlumnoService {
	
	public Iterable<Alumno> findAll();
	
	public Page<Alumno> findAll(Pageable pageable);
	
	public Optional<Alumno> findById(int id);
	
	public Alumno save(Alumno a);
	
	public void deleteById(int id);
	
	public Alumno update(int id, Alumno a);
	
	public List<Alumno> findByNombre(String nombre);
	
	public List<Alumno>findByEdadBetween(int min, int max);
	
	public List<Alumno> buscarPorNombreYApellido(String term);

}
