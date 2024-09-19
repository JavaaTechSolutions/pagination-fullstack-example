package com.jts.jpa.paging.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jts.jpa.paging.model.Books;
import com.jts.jpa.paging.repository.BookRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@GetMapping("/books")
	public ResponseEntity<Map<String, Object>> getAllBooks(@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
		Pageable paging = PageRequest.of(page, size);

		Page<Books> pageBooks;

		if (title == null) {
			pageBooks = bookRepository.findAll(paging);
		} else {
			pageBooks = bookRepository.findByTitleContaining(title, paging);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("books", pageBooks.getContent());
		response.put("currentPage", pageBooks.getNumber());
		response.put("totalItems", pageBooks.getTotalElements());
		response.put("totalPages", pageBooks.getTotalPages());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
