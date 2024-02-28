package com.uce.edu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uce.edu.repository.modelo.Persona;
import com.uce.edu.service.IPersonaService;

//http://localhost:8085/personas/buscarTodos
//http://localhost:8085/personas/actualizar
@Controller
@RequestMapping("/personas")
public class PersonaController {

	@Autowired
	private IPersonaService iPersonaService;

	// Diferentes tipos de request
	// Verbos o metodos HTTP

	// Path
	// GET
	@GetMapping("/buscarTodos")
	public String buscarTodos(Model modelo) {

		List<Persona> lista = this.iPersonaService.buscarTodos();
		modelo.addAttribute("personas", lista);
		return "VistaListaPersonas";
	}

	@GetMapping("/buscarPorCedula/{cedulaPersona}")
	public String buscarPorCedula(@PathVariable("cedulaPersona") String cedula, Model modelo) {
		Persona persona = this.iPersonaService.buscarPorCedula(cedula);
		modelo.addAttribute("persona", persona);
		return "vistaPersona";
	}

	@PutMapping("/actualizar/{cedulaPersona}")
	public String actualizar(@PathVariable("cedulaPersona") String cedula, Persona persona) { // persona es el modelo
		persona.setCedula(cedula);
		Persona perAux = this.iPersonaService.buscarPorCedula(cedula);
		perAux.setNombre(persona.getNombre());
		perAux.setApellido(persona.getApellido());
		perAux.setCedula(persona.getCedula());
		perAux.setGenero(persona.getGenero());

		this.iPersonaService.actualizar(perAux);
		return "redirect:/personas/buscarTodos";
	}

	@DeleteMapping("/borrar/{cedula}")
	public String borrar(@PathVariable("cedula") String cedula ) {

		this.iPersonaService.borrarPorCedula(cedula);
		return "redirect:/personas/buscarTodos";
	}

	@PostMapping("/insertar")
	public String insertar(Persona persona) {
		
		this.iPersonaService.guardar(persona);

		return "redirect:/personas/buscarTodos";
	}
	
	@GetMapping("/nuevaPersona")
	public String mostrarNuevaPersona(Model modelo) {
		modelo.addAttribute("persona", new Persona());
		return "vistaNuevaPersona";
	}

}
