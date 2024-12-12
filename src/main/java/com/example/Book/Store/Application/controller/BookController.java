package com.example.Book.Store.Application.controller;

import com.example.Book.Store.Application.requestdto.BookRequest;
import com.example.Book.Store.Application.responsedto.BookResponse;
import com.example.Book.Store.Application.serviceimpl.BookServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class BookController {

    @Autowired
    BookServiceImpl bookService;

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@ModelAttribute @Valid BookRequest bookRequest) {
        BookResponse bookResponse = bookService.addBook(bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Book Added Successfully");
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable int bookId) {
        BookResponse bookResponse = bookService.getBookById(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<BookResponse> bookResponses = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(bookResponses);
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<String> deleteBookById(@PathVariable int bookId) {
        BookResponse bookResponse = bookService.deleteBookById(bookId);
        return ResponseEntity.status(HttpStatus.OK).body("Book Deleted Successfully");
    }

    @PatchMapping("/books/{bookId}/price")
    public ResponseEntity<BookResponse> changeBookPrice(@PathVariable int bookId, @RequestParam double price) {
        BookResponse bookResponse = bookService.changeBookPrice(bookId,price);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookResponse);
    }

    @PatchMapping("/books/{bookId}/quantity")
    public ResponseEntity<BookResponse> changeBookQuantity(@PathVariable int bookId, @RequestParam long quantity) {
        BookResponse bookResponse = bookService.changeBookQuantity(bookId,quantity);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bookResponse);
    }
}
