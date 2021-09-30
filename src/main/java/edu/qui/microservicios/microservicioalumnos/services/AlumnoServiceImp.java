package edu.qui.microservicios.microservicioalumnos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.qui.microservicios.microservicioalumnos.modelo.entity.Alumno;
import edu.qui.microservicios.microservicioalumnos.modelo.repository.AlumnoRepository;

@Service
public class AlumnoServiceImp implements AlumnoService {
	
	@Autowired
	AlumnoRepository alumnoRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findAll() {
		
		Iterable<Alumno> coleccion = alumnoRepository.findAll();
		
		return coleccion;
		
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Alumno> findById(int id) {
		Optional<Alumno> optionalAlumno = null;
		
		optionalAlumno = alumnoRepository.findById(id);
		
		return optionalAlumno;
	}

	@Override
	@Transactional
	public Alumno save(Alumno alumno) {
		Alumno alumnoNuevo = null;
		
		alumnoNuevo = alumnoRepository.save(alumno);
		
		return alumnoNuevo;
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		
		alumnoRepository.deleteById(id);
		
	}

	@Override
	@Transactional
	public Alumno update(int id, Alumno a) {
		Alumno alumnoEditado = null;
		Optional<Alumno> alumnoLeido = null;
		
		alumnoLeido = alumnoRepository.findById(id);
		
		if(alumnoLeido.isPresent()) {
			Alumno alumnoSelec = alumnoLeido.get();
			alumnoSelec.setNombre(a.getNombre());
			alumnoSelec.setApellido(a.getApellido());
			alumnoSelec.setEmail(a.getEmail());
			alumnoSelec.setEdad(a.getEdad());
			
			alumnoEditado = alumnoRepository.save(alumnoSelec);
		}
		
		return alumnoEditado;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Alumno> findByNombre(String nombre) {
		List<Alumno> alumnos = null;
		
		alumnos = alumnoRepository.findByNombre(nombre);
		
		return alumnos;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Alumno> findByEdadBetween(int min, int max) {
		List<Alumno> alumnos = null;
		
		alumnoRepository.findByEdadBetween(min, max);
		
		return alumnos;
	}

	@Override
	@Transactional(readOnly = true)  
	public List<Alumno> buscarPorNombreYApellido(String term) {
		List<Alumno> alumnos = null;
		
		alumnos = alumnoRepository.buscarPorNombreYApellido(term);
		
		return alumnos;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Alumno> findAll(Pageable pageable) {
		return alumnoRepository.findAll(pageable);
		
	}

}
