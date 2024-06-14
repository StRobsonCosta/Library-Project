package br.com.gestao.livro.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.gestao.livro.model.Book;
import br.com.gestao.livro.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService service;
	
	@PostMapping
	public ResponseEntity<Book> addBook(@RequestParam String title, @RequestParam String author,
			@RequestParam MultipartFile coverImage) throws IOException {
		Book book = service.addBook(title, author, coverImage);
		
		return ResponseEntity.ok(book);
	}
	
	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> books = service.getAllBooks();
		
		return ResponseEntity.ok(books);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteBook(@PathVariable UUID id){
		service.deleteBook(id);
		
		return ResponseEntity.noContent().build();
	}

}
