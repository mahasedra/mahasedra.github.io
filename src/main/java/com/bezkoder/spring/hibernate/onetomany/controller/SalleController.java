package com.bezkoder.spring.hibernate.onetomany.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bezkoder.spring.hibernate.onetomany.model.Salle;
import com.bezkoder.spring.hibernate.onetomany.repository.SalleRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")

public class SalleController {
	@Autowired
	SalleRepository salleRepository;

	@GetMapping("/salles")
	public ResponseEntity<List<Salle>> getAllSalles(@RequestParam(required = false) String designation) {
		try {
			List<Salle> salles = new ArrayList<Salle>();
			if (designation == null)
				salleRepository.findAll().forEach(salles::add);
			else
				salleRepository.findByDesignationContaining(designation).forEach(salles::add);
			if (salles.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(salles, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/salles/{id}")
	public ResponseEntity<Salle> getSalleById(@PathVariable("id") long id) {
		Optional<Salle> salleData = salleRepository.findById(id);
		if (salleData.isPresent()) {
			return new ResponseEntity<>(salleData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/salles")
	public ResponseEntity<Salle> createSalle(@RequestBody Salle salle) {
		try {
			Salle _salle = salleRepository.save(new Salle(salle.getDesignation()));
			return new ResponseEntity<>(_salle, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/salles/{id}")
	public ResponseEntity<Salle> updateSalle(@PathVariable("id") long id, @RequestBody Salle salle) {
		Optional<Salle> salleData = salleRepository.findById(id);
		if (salleData.isPresent()) {
			Salle _salle = salleData.get();
			_salle.setDesignation(salle.getDesignation());
			return new ResponseEntity<>(salleRepository.save(_salle), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/salles/{id}")
	public ResponseEntity<HttpStatus> deleteSalle(@PathVariable("id") long id) {
		try {
			salleRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/salles")
	public ResponseEntity<HttpStatus> deleteAllSalles() {
		try {
			salleRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
