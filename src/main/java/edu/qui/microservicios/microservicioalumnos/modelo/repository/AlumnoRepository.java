package edu.qui.microservicios.microservicioalumnos.modelo.repository;
import edu.qui.microservicios.microserviciocomun.model.entity.Alumno;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Integer> {
	
	// Seleccionar alumnos por nombre -> Keywords
	public List<Alumno> findByNombre(String nombre);
	
	// Seleccionar alumnos por rango de edad -> Keywords
	public List<Alumno> findByEdadBetween(int min, int max);
	
	// @QUERY Seleccionar alumnos por nombre o apellido
	@Query("select a from Alumno a where a.nombre like %?1% or a.apellido like %?1%")
	public List<Alumno> buscarPorNombreYApellido(String term);

}
