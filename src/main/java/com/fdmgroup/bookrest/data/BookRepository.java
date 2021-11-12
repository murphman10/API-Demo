package com.fdmgroup.bookrest.data;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.bookrest.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByAuthorContainingIgnoreCase(String author); //Built by JPA
	
}
