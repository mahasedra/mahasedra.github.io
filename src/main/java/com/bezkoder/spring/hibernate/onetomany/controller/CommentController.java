package com.bezkoder.spring.hibernate.onetomany.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.hibernate.onetomany.exception.ResourceNotFoundException;
import com.bezkoder.spring.hibernate.onetomany.model.Comment;
import com.bezkoder.spring.hibernate.onetomany.model.Salle;
import com.bezkoder.spring.hibernate.onetomany.model.Tutorial;
import com.bezkoder.spring.hibernate.onetomany.repository.CommentRepository;
import com.bezkoder.spring.hibernate.onetomany.repository.TutorialRepository;
import com.bezkoder.spring.hibernate.onetomany.repository.SalleRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private TutorialRepository tutorialRepository;

	@Autowired
	private SalleRepository salleRepository;

	@Autowired
	private CommentRepository commentRepository;

	@GetMapping("/tutorials/{tutorialId}/comments")
	public ResponseEntity<List<Comment>> getAllCommentsByTutorialId(
			@PathVariable(value = "tutorialId") Long tutorialId) {
		if (!tutorialRepository.existsById(tutorialId)) {
			throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
		}

		List<Comment> comments = commentRepository.findByTutorialId(tutorialId);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

	@GetMapping("/salles/{salleId}/comments")
	public ResponseEntity<List<Comment>> getAllCommentsBySalleId(@PathVariable(value = "salleId") Long salleId) {
		if (!salleRepository.existsById(salleId)) {
			throw new ResourceNotFoundException("Not found Salle with id = " + salleId);
		}

		List<Comment> comments = commentRepository.findBySalleId(salleId);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

	@GetMapping("/comments/{id}")
	public ResponseEntity<Comment> getCommentsByTutorialId(@PathVariable(value = "id") Long id) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Comment with id = " + id));

		return new ResponseEntity<>(comment, HttpStatus.OK);
	}

	@PostMapping("/tutorials/{tutorialId}/salles/{salleId}/comments")
	public ResponseEntity<Comment> createComment(@PathVariable(value = "tutorialId") Long tutorialId,
			@PathVariable(value = "salleId") Long salleId, @RequestBody Comment commentRequest) {
		Tutorial tutorialComment = tutorialRepository.findById(tutorialId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));
		commentRequest.setTutorial(tutorialComment);
		Salle salleComment = salleRepository.findById(salleId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Salle with id = " + salleId));
		commentRequest.setSalle(salleComment);
		Comment comment = commentRepository.save(commentRequest);

		return new ResponseEntity<>(comment, HttpStatus.CREATED);
	}

	@PutMapping("/comments/{id}")
	public ResponseEntity<Comment> updateComment(@PathVariable("id") long id, @RequestBody Comment commentRequest) {
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CommentId " + id + "not found"));

		comment.setContent(commentRequest.getContent());

		return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.OK);
	}

	@DeleteMapping("/comments/{id}")
	public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") long id) {
		commentRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/tutorials/{tutorialId}/comments")
	public ResponseEntity<List<Comment>> deleteAllCommentsOfTutorial(
			@PathVariable(value = "tutorialId") Long tutorialId) {
		if (!tutorialRepository.existsById(tutorialId)) {
			throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
		}

		commentRepository.deleteByTutorialId(tutorialId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
