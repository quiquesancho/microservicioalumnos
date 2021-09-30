package edu.qui.microservicios.microservicioalumnos.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
public class JSONPController {

	@Autowired
	ObjectMapper om;

	@GetMapping(value = "/jsonp/alumno")
	public void testJSONP(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "callback", required = true) String callback) throws IOException {
		
		String dataString, cuerpo;
		ObjectNode on = om.createObjectNode();
		
		on.put("id", 60);
		on.put("nombre", "Enrique");
		on.put("apellido", "Sancho");
		on.put("email", "quisan@gmail.com");
		on.put("edad", 26);
		on.put("creatd_at", "1995-01-31");
		
		dataString = on.toString();
		cuerpo = callback + "(" + dataString + ")";
		
		response.setContentType("text/javascript; charset=UTF-8");
		response.getWriter().print(cuerpo);
	}

}
