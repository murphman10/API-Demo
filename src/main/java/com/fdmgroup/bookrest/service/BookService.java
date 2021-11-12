package com.fdmgroup.bookrest.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.bookrest.data.BookRepository;
import com.fdmgroup.bookrest.model.Book;

@Service
public class BookService {
	
	private BookRepository bookRepository;
	
	@Autowired
	public BookService(BookRepository bookRepository) {
		super();
		this.bookRepository = bookRepository;
	}
	
	//create
	public Book create(Book book) {
		book.setId(0); //if a new book is being added, this prevents you from over-writing a book already there
		return bookRepository.save(book);
		
	}
	
	//find all
	public List<Book> findAllBooks() {
		return bookRepository.findAll();
	}

	//find by id
	public Book findById(long bookId) {
		//bookRepository.findById is returning an optional: either a book or an exception
		return bookRepository.findById(bookId)
				.orElseThrow(() -> new NoSuchElementException("could not find book with id=" + bookId));
	}
	
	//update
	public Book updateBook(Book book) {
		//verify the book exists
		verifyBookExists(book.getId());
		//if it exists, update it
		return bookRepository.save(book);
	}
	
	//delete
	public void delete(long bookId) {
		verifyBookExists(bookId);
		bookRepository.deleteById(bookId);
	}
	
	private void verifyBookExists(long bookId) {
		
		bookRepository.findById(bookId)
		.orElseThrow(() -> new NoSuchElementException("could not find book with id=" + bookId));
	}

	public List<Book> findByAuthorLike(String author) {
		
		return bookRepository.findByAuthorContainingIgnoreCase(author);
	}
	
}
