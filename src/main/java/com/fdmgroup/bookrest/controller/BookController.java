package com.fdmgroup.bookrest.controller;

import java.util.List;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.bookrest.model.Book;
import com.fdmgroup.bookrest.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = "/books") //easier than creating each method starting with this path
public class BookController {
	
	private BookService bookService;
	
	@Autowired
	public BookController(BookService bookService) {
		super();
		this.bookService = bookService;
	}
	
	//Request handler for GET read all books
	@GetMapping()
	public List<Book> getAll() {
		return bookService.findAllBooks();
	}
	
	//Request handler for GET read one book
	@GetMapping(path="/{id}")
	public Book getOne(@PathVariable long id) {
		return bookService.findById(id);
	}
	
	//Request handler for POST new book
	@Operation(summary = "Create a new book wit hthe book object passed in the request body.", responses = { 
			@ApiResponse(responseCode = "201", description = "Book sucessfully added.", content = {
					@Content(mediaType = "application/json") }) }) //Swagger documentation
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) //changes 200 Ok response to 201 created
	public Book create(@Valid @RequestBody Book book) {
		return bookService.create(book);
	}
	
	/* Microservice version of create book
	 * 
	 * @PostMapping
	 * @ResponseStatus(HttpStatus.CREATED)
	 * public ResponseEntity<Book> create(@RequestBody @Valid Book book) {
	 * 	book.setId(0);
	 * 	bookService.create(book);
	 * 	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand.(book.getId()).toUri();
	 * 	return ResponseEntity.created(location).build(); // may not need @ResponseStatus if this return type is used
	 * 
	 * instead of build(); you can use body(); which allows us to return a book object
	 * HttpHeaders responseHeaders = new HttpHeaders();
	 * responseHeaders.setLocation(location);
	 * responseHeaders.set("CustomHeader", "value of custom header");
	 * return new ResponseEntity<Book>(book, responseHeaders, HttpStatus.CREATED);
	 * 
	 * return ResponseEntity.created(location).header("other header", "other value").body(book); //chaining way to create a response entity object 
	 * }
	 * 
	 */
	
	//Request handler for PUT update book
	@PutMapping(path ="/{id}")
	public Book update(@PathVariable long id, @Valid @RequestBody Book book) {
		if(id == 0) {
			throw new IllegalArgumentException("Cannot update book with id=0, to create book use POST request");
		}
		book.setId(id); //find this book
		return bookService.updateBook(book);
	}
	
	//Request handler for DELETE delete a book
	@DeleteMapping(path ="/{id}")
	public void delete(@PathVariable long id) {
		bookService.delete(id);
	}
	
	//Request handler find books by author
	@GetMapping(path="/findByAuthorLike")
	public List<Book> findByAuthorLike(@RequestParam String author){
		
		return bookService.findByAuthorLike(author);
	}
	
	//Exception handler method, if book is not found
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public String bookNotFound(NoSuchElementException e) {
		return e.getMessage();
	}
	
}
