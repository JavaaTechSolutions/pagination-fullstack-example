package com.jts.jpa.paging.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jts.jpa.paging.model.Books;

public interface BookRepository extends JpaRepository<Books, Long> {
	
	Page<Books> findByTitleContaining(String title, Pageable pageable);

}
