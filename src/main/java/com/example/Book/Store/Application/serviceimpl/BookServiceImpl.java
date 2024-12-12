package com.example.Book.Store.Application.serviceimpl;

import com.example.Book.Store.Application.Handler.BookNotFoundByIdException;
import com.example.Book.Store.Application.entity.Book;
import com.example.Book.Store.Application.mapper.BookMapper;
import com.example.Book.Store.Application.repository.BookRepository;
import com.example.Book.Store.Application.requestdto.BookRequest;
import com.example.Book.Store.Application.responsedto.BookResponse;
import com.example.Book.Store.Application.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookRepository bookRepository;
    @Override
    public BookResponse addBook(BookRequest bookRequest) {
        Book book = bookMapper.mapToBook(bookRequest, new Book());
        book = bookRepository.save(book);
        return bookMapper.mapToBookResponse(book);
    }

    @Override
    public BookResponse getBookById(int bookId) {
        return bookRepository.findById(bookId)
                .map(book -> {
                    return bookMapper.mapToBookResponse(book);
                }).orElseThrow(()->new BookNotFoundByIdException("Not able to get book"));
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(book-> {
                    return bookMapper.mapToBookResponse(book);
                }).toList();
    }

    @Override
    public BookResponse deleteBookById(int bookId) {
        return bookRepository.findById(bookId)
                .map(book->{
                    bookRepository.delete(book);
                    return bookMapper.mapToBookResponse(book);
                }).orElseThrow(()->new BookNotFoundByIdException("Not able to delete book"));
    }

    @Override
    public BookResponse changeBookPrice(int bookId, double price) {
        return bookRepository.findById(bookId)
                .map(book->{
                    book.setPrice(price);
                    book = bookRepository.save(book);
                    return bookMapper.mapToBookResponse(book);
                }).orElseThrow(() -> new BookNotFoundByIdException("Unable to change price"));
    }

    @Override
    public BookResponse changeBookQuantity(int bookId, long quantity) {
        return bookRepository.findById(bookId)
                .map(book->{
                    book.setQuantity(quantity);
                    book = bookRepository.save(book);
                    return bookMapper.mapToBookResponse(book);
                }).orElseThrow(() -> new BookNotFoundByIdException("Unable to change quantity"));
    }

    public void updateBook(Book book) {
        bookRepository.save(book);
    }
}
