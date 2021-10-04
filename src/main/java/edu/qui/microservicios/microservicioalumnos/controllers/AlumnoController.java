package edu.qui.microservicios.microservicioalumnos.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.qui.microservicios.microservicioalumnos.modelo.entity.Alumno;
import edu.qui.microservicios.microservicioalumnos.services.AlumnoService;

@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
public class AlumnoController {

	@Autowired
	AlumnoService alumnoService;

	Logger log = LoggerFactory.getLogger(AlumnoController.class);

	@GetMapping
	public ResponseEntity<Iterable<Alumno>> getAlumnos() {
		ResponseEntity<Iterable<Alumno>> responseEntity = null;
		Iterable<Alumno> alumnos = null;

		log.debug("Ha entrado en lista de alumnos");

		alumnos = alumnoService.findAll();

		responseEntity = ResponseEntity.ok(alumnos);

		log.debug("Salida en lista de alumnos");

		return responseEntity;
	}

//	@GetMapping
//	public ResponseEntity<Iterable<Alumno>> getAlumnos(@RequestHeader("miciudad") String miciudad) {
//		ResponseEntity<Iterable<Alumno>> responseEntity = null;
//		Iterable<Alumno> alumnos = null;
//
//		log.debug("Ha entrado en lista de alumnos");
//		
//		log.info(miciudad);
//
//		alumnos = alumnoService.findAll();
//
//		responseEntity = ResponseEntity.ok(alumnos);
//
//		log.debug("Salida en lista de alumnos");
//		
//		
//
//		return responseEntity;
//	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Alumno> getAlumnoByid(@PathVariable("id") int id) {
		Optional<Alumno> alumno = null;
		ResponseEntity<Alumno> responseEntity = null;

		log.debug("Ha entrado en lista de alumnos por id");
		alumno = alumnoService.findById(id);
		if (alumno.isPresent()) {
			responseEntity = ResponseEntity.ok(alumno.get());
			log.debug("Alumno encontrado con id: " + alumno.get());
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			log.debug("Alumno no encontrado por ID");
		}

		return responseEntity;
	}

	@GetMapping(value = "/getFoto/{id}")
	public ResponseEntity<?> getFotoAlumnoById(@PathVariable("id") int id) {
		ResponseEntity<?> responseEntity = null;
		Optional<Alumno> oa = null;
		Resource imagen = null;

		log.debug("Entrado en obtener foto de alumno por id");

		oa = alumnoService.findById(id);

		if (oa.isPresent() && oa.get().getFoto() != null) {
			imagen = new ByteArrayResource(oa.get().getFoto());
			responseEntity = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
		} else {
			responseEntity = ResponseEntity.notFound().build();
		}
		
		log.debug("Saliendo de obtener foto de alumno por id");

		return responseEntity;
	}

	@PostMapping
	public ResponseEntity<?> addAlumno(@Valid @RequestBody Alumno alumno, BindingResult br) {
		ResponseEntity<?> responseEntity = null;
		Alumno alumnoNuevo = null;

		if (br.hasErrors()) {
			log.debug("Hay errores en los datos de entrada");
			responseEntity = getErrors(br);
		} else {
			log.debug("Ha entrado en insertar alumno. Alumno validado");
			alumnoNuevo = alumnoService.save(alumno);

			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumnoNuevo);

			log.debug("Ha salido de insertar un alumno");
		}

		return responseEntity;
	}

	@PostMapping("/addFoto")
	public ResponseEntity<?> addAlumnoFoto(@Valid Alumno alumno, BindingResult br, MultipartFile archivo)
			throws IOException {
		ResponseEntity<?> responseEntity = null;
		Alumno alumnoNuevo = null;

		if (br.hasErrors()) {
			log.debug("Hay errores en los datos de entrada");
			responseEntity = getErrors(br);
		} else {

			if (!archivo.isEmpty()) {
				alumno.setFoto(archivo.getBytes());
			}

			log.debug("Ha entrado en insertar alumno. Alumno validado");
			alumnoNuevo = alumnoService.save(alumno);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumnoNuevo);

			log.debug("Ha salido de insertar un alumno");
		}

		return responseEntity;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAlumno(@PathVariable("id") int id) {
		ResponseEntity<Void> responseEntity = null;
		Optional<Alumno> alumnoBuscado = null;

		log.debug("Ha entrado en eliminar alumno");

		alumnoBuscado = alumnoService.findById(id);

		alumnoService.deleteById(id);
		responseEntity = ResponseEntity.status(HttpStatus.OK).build();

		log.debug("Ha salido en eliminar alumno");

		return responseEntity;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Alumno> updateAlumno(@PathVariable("id") int id, @RequestBody Alumno alumno) {
		ResponseEntity<Alumno> responseEntity = null;
		Alumno alumnoBuscado = null;

		log.debug("Ha entrado en actualizar alumno");

		alumnoBuscado = alumnoService.update(id, alumno);

		if (alumnoBuscado != null) {
			responseEntity = ResponseEntity.ok(alumnoBuscado);
			log.debug("Ha actualizado correctamente el alumno");
		} else {
			responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			log.debug("No ha actualizado correctamente el alumno");
		}

		return responseEntity;
	}

	@PutMapping("/updateAlumno/{id}")
	public ResponseEntity<?> updateAlumnoFoto(@PathVariable("id") int id, @Valid Alumno alumno, BindingResult br,
			MultipartFile archivo) throws IOException {
		ResponseEntity<?> responseEntity = null;
		Alumno alumnoBuscado = null;

		log.debug("Ha entrado en actualizar alumno con foto");

		if (br.hasErrors()) {
			log.debug("Hay errores en los datos de entrada");
			responseEntity = getErrors(br);
		} else {
			if (!archivo.isEmpty()) {
				alumno.setFoto(archivo.getBytes());
			}

			alumnoBuscado = alumnoService.update(id, alumno);

			if (alumnoBuscado != null) {
				responseEntity = ResponseEntity.ok(alumnoBuscado);
				log.debug("Ha actualizado correctamente el alumno");
			} else {
				responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				log.debug("No ha actualizado correctamente el alumno");
			}
		}

		return responseEntity;
	}

	@GetMapping("/busqueda/nombre/{nombre}")
	public ResponseEntity<List<Alumno>> findByNombre(@PathVariable("nombre") String nombre) {

		return ResponseEntity.ok(alumnoService.findByNombre(nombre));

	}

	@GetMapping("/busqueda/edad/{edad1}/{edad2}")
	public ResponseEntity<List<Alumno>> findByEdadEntre(@PathVariable int edad1, @PathVariable int edad2) {

		return ResponseEntity.ok(alumnoService.findByEdadBetween(edad1, edad2));

	}

	@GetMapping("/busqueda/nombreApellido/{term}")
	public ResponseEntity<List<Alumno>> findByNombreYApellido(@PathVariable("term") String term) {

		return ResponseEntity.ok(alumnoService.buscarPorNombreYApellido(term));

	}

	@GetMapping(value = "/pagina")
	public ResponseEntity<Iterable<Alumno>> findAllByPagination(Pageable pageable) {
		ResponseEntity<Iterable<Alumno>> responseEntity = null;
		Iterable<Alumno> listaAlumnos = null;

		log.debug("Entrando en la paginación");

		listaAlumnos = alumnoService.findAll(pageable);

		responseEntity = ResponseEntity.ok(listaAlumnos);

		log.debug("Saliendo de paginación");

		return responseEntity;
	}

	private ResponseEntity<?> getErrors(BindingResult br) {
		ResponseEntity<?> responseEntity = null;
		List<ObjectError> listaErrores;

		listaErrores = br.getAllErrors();
		listaErrores.forEach(oe -> {
			log.error(oe.toString());
		});

		responseEntity = ResponseEntity.badRequest().body(listaErrores);

		return responseEntity;
	}
}
